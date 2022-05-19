package at.ac.tgm.pdamianik.sem8.sew.server;

import at.ac.tgm.pdamianik.sem8.sew.server.model.protocol.Challenge;
import at.ac.tgm.pdamianik.sem8.sew.server.model.protocol.Result;
import at.ac.tgm.pdamianik.sem8.sew.server.model.protocol.Statistic;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * It handles a client connection by asking the client questions and sending back the results
 * @author pdamianik
 */
public class ClientHandler implements Runnable {
	private final Socket SOCKET;

	private final List<Challenge> challenges;

	private final Statistic statistic;

	private String username;

	public ClientHandler(Socket socket, List<Challenge> challenges) {
		this.SOCKET = socket;
		this.challenges = challenges;
		this.statistic = new Statistic(0, 0, challenges.size());
	}

	/**
	 * If the message is an instance of the expectation, return the message cast to the expectation, otherwise print an error
	 * message and return null.
	 *
	 * The function is generic, so it can be used with any type of message
	 *
	 * @param message The message received from the server
	 * @param expectation The class of the message you're expecting.
	 * @return The message that was received.
	 */
	public <T> T expectMessage(Object message, Class<T> expectation) {
		if (expectation.isInstance(message)) {
			return expectation.cast(message);
		}
		System.err.println("Received unexpected message from " + username);
		return null;
	}

	/**
	 * The server waits for a client to connect, then waits for the client to send a username, then asks the client questions
	 * until the client disconnects
	 */
	@Override
	public void run() {
		try (ObjectOutputStream output = new ObjectOutputStream(SOCKET.getOutputStream())) {
			try (ObjectInputStream input = new ObjectInputStream(SOCKET.getInputStream())) {
				username = expectMessage(input.readObject(), String.class);
				System.out.println("Client " + username + " connected");
				if (username != null) {
					askQuestions(input, output);
				}
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * It asks the user a question, checks the answer, and sends the result back to the user
	 *
	 * @param input The input stream to read from.
	 * @param output The output stream to write to.
	 */
	private void askQuestions(ObjectInputStream input, ObjectOutputStream output) throws IOException, ClassNotFoundException {
		for (Challenge challenge : challenges) {
			output.writeObject(challenge.question());

			String answer = expectMessage(input.readObject(), String.class);
			if (answer == null) {
				break;
			}

			Result result;
			if (challenge.answer().contains(answer.toLowerCase())) {
				statistic.correct();
				result = new Result(true, statistic.clone());
			} else {
				statistic.wrong();
				result = new Result(false, statistic.clone());
			}

			output.writeObject(result);
		}
	}
}

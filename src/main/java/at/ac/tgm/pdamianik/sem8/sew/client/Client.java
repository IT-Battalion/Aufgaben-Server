package at.ac.tgm.pdamianik.sem8.sew.client;

import at.ac.tgm.pdamianik.sem8.sew.server.model.protocol.Prompt;
import at.ac.tgm.pdamianik.sem8.sew.server.model.protocol.Result;
import at.ac.tgm.pdamianik.sem8.sew.server.model.protocol.Statistic;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * A client
 * @param host a host to connect to
 * @param port the port to connect to
 * @param username the username to send to the server
 * @author pdamianik
 */
public record Client(String host, int port, String username) {

	// A method that connects to the server.
	public void connect() throws IOException, ClassNotFoundException {
		try (Socket socket = new Socket(host, port)) {
			try (ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())) {
				try (ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {
					communicate(socket, input, output);
				}
			}
		}
	}

	// The main loop of the client. It reads messages from the server and sends messages to the server.
	public void communicate(Socket socket, ObjectInputStream input, ObjectOutputStream output) throws IOException, ClassNotFoundException {
		output.writeObject(username); // Send hello with username

		try (Scanner scanner = new Scanner(System.in)) {
			while (socket.isConnected()) {
				Object msg = input.readObject();

				if (msg == null) {
					throw new IOException("Unexpected null message from server");
				} else if (msg instanceof Prompt prompt) {
					System.out.print(prompt.question() + " ");
					String userInput = scanner.nextLine();
					output.writeObject(userInput);
				} else if (msg instanceof Result result) {
					if (result.correct()) {
						System.out.println("This answer was correct!");
					} else {
						System.out.println("This answer was wrong!");
					}
					Statistic statistic = result.statistic();
					System.out.println("Statistic: " + statistic.getCorrect() + " correct, " + statistic.getWrong() + " wrong, " + statistic.getTodo() + " pending");
					if (statistic.getTodo() == 0) {
						break;
					}
				}
			}
		}
	}

	// The main method of the client. It parses the command line arguments and creates a client. Then it connects to the
	// server.
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		if (args.length != 3) {
			throw new IllegalArgumentException("Expected 3 parameters: host (string), port (int), username (string)");
		}
		int port;
		try {
			port = Integer.parseInt(args[1]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Expected 3 parameters: host (string), port (int), username (string)");
		}
		Client client = new Client(args[0], port, args[2]);
		client.connect();
	}
}

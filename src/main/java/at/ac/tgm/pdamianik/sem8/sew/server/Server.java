package at.ac.tgm.pdamianik.sem8.sew.server;

import at.ac.tgm.pdamianik.sem8.sew.server.model.generators.*;
import at.ac.tgm.pdamianik.sem8.sew.server.model.protocol.Challenge;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * It listens for incoming connections on a given port, and when a connection is accepted, it creates a new thread to
 * handle the client
 * @author pdamianik
 */
public class Server {
	public static final int BUFFER_CAPACITY = 20;

	public static final int QUESTION_COUNT = 3;

	public static final String challengesFile = "challenges.csv";

	public final int port;

	private final Generator<Challenge> CHALLENGE_GENERATOR;

	private final BlockingQueue<Challenge> challengeQueue;

	public Server(int port) {
		this.port = port;
		List<Generator<Challenge>> challengeGenerators = new ArrayList<>();
		challengeGenerators.add(new CalculationChallengeGenerator());
		challengeGenerators.add(new ListChallengeGenerator(ChallengeLoader.cacheLoad(challengesFile)));
		CHALLENGE_GENERATOR = new AnyRandomGenerator<>(challengeGenerators);
		challengeQueue = new LinkedBlockingQueue<>(BUFFER_CAPACITY);
	}

	/**
	 * Listen for incoming connections, and for each one, create a {@link ClientHandler} to handle the connection.
	 *
	 * The first thing we do is create a thread pool. We'll use this thread pool to handle incoming connections and also the generation of new {@link Challenge}s
	 */
	public void listen() throws IOException {
		ExecutorService threadPool = Executors.newCachedThreadPool();

		for (int i = 0; i < 20; i++) {
			threadPool.submit(() -> {
				challengeQueue.add(CHALLENGE_GENERATOR.generate());
			});
		}

		try (ServerSocket serverSocket = new ServerSocket(port)) {
			System.out.println("Server is listening on port " + port);
			while (!serverSocket.isClosed()) {
				Socket clientConnection = serverSocket.accept();
				System.out.println("Accepted incoming connection");
				List<Challenge> selectedChallenges = new ArrayList<>();
				challengeQueue.drainTo(selectedChallenges, QUESTION_COUNT);

				ClientHandler handler = new ClientHandler(clientConnection, selectedChallenges);
				threadPool.submit(handler);
			}
		} finally {
			threadPool.shutdownNow();
		}
	}

	/**
	 * It creates a new server object, and then tells it to listen for incoming connections
	 */
	public static void main(String[] args) throws IOException {
		if (args.length != 1) {
			throw new IllegalArgumentException("Expected one parameter: port (int)");
		}
		int port;
		try {
			port = Integer.parseInt(args[0]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Expected one parameter: port (int)");
		}
		Server server = new Server(port);
		server.listen();
	}
}

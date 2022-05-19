package at.ac.tgm.pdamianik.sem8.sew.server.model.generators;

import at.ac.tgm.pdamianik.sem8.sew.server.model.protocol.Challenge;
import at.ac.tgm.pdamianik.sem8.sew.server.model.protocol.Prompt;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * It loads a csv file containing a list of challenges, and returns a list of challenges.
 * The challenges are stored without a header in the format:
 * question,answer,answer,...
 * @author pdamainik
 */
public class ChallengeLoader {
	private static final HashMap<String, List<Challenge>> challengesCache = new HashMap<>();

	/**
	 * It reads a file, parses it, and returns a list of challenges
	 *
	 * @param challengesFile The name of the file that contains the challenges.
	 * @return A list of challenges
	 */
	public static List<Challenge> load(String challengesFile) {
		if (challengesCache.containsKey(challengesFile)) {
			return challengesCache.get(challengesFile);
		}
		List<Challenge> challenges = new ArrayList<>();
		URL resource = ClassLoader.getSystemClassLoader().getResource(challengesFile);
		try (Scanner reader = new Scanner(new BufferedReader(new FileReader(resource.getPath())))) {
			while (reader.hasNextLine()) {
				List<String> line = Arrays.asList(reader.nextLine().split(","));

				Prompt question = new Prompt(line.get(0));

				Set<String> answers = new HashSet<>();
				for (int i = 1; i < line.size(); i++) {
					answers.add(line.get(i));
				}

				Challenge challenge = new Challenge(question, answers);
				challenges.add(challenge);
			}
			System.out.println(challengesCache);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		challengesCache.put(challengesFile, challenges);
		return challenges;
	}

	/**
	 * If the challenges file is already in the cache, return the cached version, otherwise load it from the file. It uses the method {@link #load(String)} to do the loading
	 *
	 * @see #load
	 * @param challengesFile The file to load the challenges from.
	 * @return A list of challenges.
	 */
	public static List<Challenge> cacheLoad(String challengesFile) {
		if (challengesCache.containsKey(challengesFile)) {
			return challengesCache.get(challengesFile);
		}
		return load(challengesFile);
	}
}

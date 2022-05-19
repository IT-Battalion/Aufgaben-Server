package at.ac.tgm.pdamianik.sem8.sew.server.model.generators;

import at.ac.tgm.pdamianik.sem8.sew.server.model.protocol.Challenge;

import java.util.List;
import java.util.Random;

/**
 * It's a generator that generates a random challenge from a list of challenges
 * @author pdamianik
 */
public class ListChallengeGenerator implements Generator<Challenge> {
	private List<Challenge> questions;
	private final Random generator;

	/**
	 * A constructor that takes the list of challenges available for random selection later on.
	 * @param challenges the list of challenges
	 */
	public ListChallengeGenerator(List<Challenge> challenges) {
		this.questions = challenges;
		this.generator = new Random();
	}

	/**
	 * This function returns a list of challenges
	 *
	 * @return A list of challenges.
	 */
	public List<Challenge> getChallenges() {
		return questions;
	}

	/**
	 * This function sets the questions of the quiz to the questions passed in as a parameter
	 *
	 * @param questions The list of questions that will be displayed to the user.
	 */
	public void setChallenges(List<Challenge> questions) {
		this.questions = questions;
	}

	/**
	 * Return a random question from the list of questions.
	 *
	 * @return A random question from the list of questions.
	 */
	@Override
	public Challenge generate() {
		return questions.get(generator.nextInt(questions.size()));
	}

}

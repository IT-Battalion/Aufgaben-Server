package at.ac.tgm.pdamianik.sem8.sew.server.model.generators;

import at.ac.tgm.pdamianik.sem8.sew.server.model.protocol.Challenge;
import at.ac.tgm.pdamianik.sem8.sew.server.model.protocol.Prompt;

import java.util.*;

/**
 * It generates a random calculation challenge
 * @author pdamianik
 */
public class CalculationChallengeGenerator implements Generator<Challenge> {
	private final Random generator;

	public CalculationChallengeGenerator() {
		this.generator = new Random();
	}

	/**
	 * A method that generates a random calculation {@link Challenge}.
	 * @return a random calculation {@link Challenge}
	 */
	@Override
	public Challenge generate() {
		int operation = generator.nextInt(4);
		int a = generator.nextInt(100);
		int b = generator.nextInt(100);
		int result;

		String text = switch (operation) {
			case 0 -> { // Add
				result = a + b;
				yield String.format("What is %s + %s?", a, b);
			}
			case 1 -> { // Subtract
				result = a - b;
				yield String.format("What is %s - %s?", a, b);
			}
			case 2 -> { // Multiply
				result = a * b;
				yield String.format("What is %s * %s?", a, b);
			}
			case 3 -> { // Divide
				result = a / b;
				yield String.format("What is %s / %s (without decimal positions)?", a, b);
			}
			case 4 -> { // Modulo
				result = a % b;
				yield String.format("Was ist %s %% %s?", a, b);
			}
			default -> throw new RuntimeException("Impossible");
		};

		return new Challenge(new Prompt(text), new HashSet<>(Collections.singleton(Integer.toString(result))));
	}

}

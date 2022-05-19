package at.ac.tgm.pdamianik.sem8.sew.server.model.generators;

import java.util.List;
import java.util.Random;

/**
 * It's a generator that generates a random element from a list of generators
 * @author pdamianik
 */
public class AnyRandomGenerator<E> implements Generator<E> {
	private final List<Generator<E>> generators;
	private final Random random;

	/**
	 * A constructor taking a list of generators of which it will later on pick random generators.
	 * @param generators the list to select from.
	 */
	public AnyRandomGenerator(List<Generator<E>> generators) {
		this.generators = generators;
		this.random = new Random();
	}

	/**
	 * Return a random generator from the generator list passed into the constructor
	 * @return a random generator
	 */
	@Override
	public E generate() {
		return generators.get(random.nextInt(generators.size())).generate();
	}

}

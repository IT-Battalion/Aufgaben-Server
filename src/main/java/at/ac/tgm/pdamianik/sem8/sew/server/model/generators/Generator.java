package at.ac.tgm.pdamianik.sem8.sew.server.model.generators;

/** An interface representing a generator for some type.
 * @author pdamianik
 */
public interface Generator<E> {
	/**
	 * Generate() returns an object of type E.
	 *
	 * @return A random number between 0 and 1.
	 */
	E generate();
}

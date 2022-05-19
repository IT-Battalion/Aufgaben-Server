package at.ac.tgm.pdamianik.sem8.sew.server.model.protocol;

import java.io.Serial;
import java.io.Serializable;

/**
 * A packet send by the server to prompt the user for an answer to the given question
 * @param question
 */
public record Prompt(String question) implements Serializable {
	@Serial
	private static final long serialVersionUID = 829345L;
}

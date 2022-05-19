package at.ac.tgm.pdamianik.sem8.sew.server.model.protocol;

import java.io.Serial;
import java.io.Serializable;
import java.util.Set;

/**
 * A record representing a question and possible answers pair (a challenge)
 * @param question the question
 * @param answer the answer
 */
public record Challenge(Prompt question, Set<String> answer) implements Serializable {
	@Serial
	private static final long serialVersionUID = 1347813L;
}

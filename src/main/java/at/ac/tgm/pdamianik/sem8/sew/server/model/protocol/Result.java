package at.ac.tgm.pdamianik.sem8.sew.server.model.protocol;

import java.io.Serial;
import java.io.Serializable;

/**
 * The result of a question send to the user by the server that contains whether the users answer was correct and the {@link Statistic} of the current connection/quiz
 * @param correct whether the answer was correct
 * @param statistic the {@link Statistic} of the current connection/quiz
 */
public record Result(boolean correct, @Serial Statistic statistic) implements Serializable {
	@Serial
	private static final long serialVersionUID = 341263L;
}

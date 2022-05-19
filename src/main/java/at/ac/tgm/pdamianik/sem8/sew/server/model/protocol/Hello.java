package at.ac.tgm.pdamianik.sem8.sew.server.model.protocol;

import java.io.Serial;
import java.io.Serializable;

/**
 * A hello packet send by the user to transmit their username
 * @param username the username of the user
 */
public record Hello(String username) implements Serializable {
	@Serial
	private static final long serialVersionUID = 482688L;
}

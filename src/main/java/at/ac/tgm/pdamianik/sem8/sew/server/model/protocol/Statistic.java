package at.ac.tgm.pdamianik.sem8.sew.server.model.protocol;

import java.io.Serial;
import java.io.Serializable;

/**
 * It's a simple class that keeps track of the number of correct, wrong and todo answers
 */
public class Statistic implements Serializable, Cloneable {
	@Serial
	private static final long serialVersionUID = 103459L;

	private int correct;
	private int wrong;
	private int todo;

	public Statistic(int correct, int wrong, int todo) {
		this.correct = correct;
		this.wrong = wrong;
		this.todo = todo;
	}

	/**
	 * This function returns the number of correct answers
	 *
	 * @return The correct variable is being returned.
	 */
	public int getCorrect() {
		return correct;
	}

	/**
	 * This function sets the value of the variable correct to the value of the parameter correct
	 *
	 * @param correct The number of correct answers
	 */
	public void setCorrect(int correct) {
		this.correct = correct;
	}

	/**
	 * This function increments the correct variable by one and decrements the todo variable by one.
	 */
	public void correct() {
		correct++;
		todo--;
	}

	/**
	 * This function returns the number of wrong answers
	 *
	 * @return The wrong variable is being returned.
	 */
	public int getWrong() {
		return wrong;
	}

	/**
	 * This function sets the value of the variable `wrong` to the value of the parameter `wrong`
	 *
	 * @param wrong The number of wrong answers the user has given.
	 */
	public void setWrong(int wrong) {
		this.wrong = wrong;
	}

	/**
	 * If the user gets the question wrong, the wrong variable is incremented by one and the todo variable is decremented by
	 * one.
	 */
	public void wrong() {
		wrong++;
		todo--;
	}

	/**
	 * This function returns the value of the variable `todo`
	 *
	 * @return The value of the variable todo.
	 */
	public int getTodo() {
		return todo;
	}

	/**
	 * This function sets the value of the todo variable.
	 *
	 * @param todo The todo number to be deleted.
	 */
	public void setTodo(int todo) {
		this.todo = todo;
	}

	public int getCorrectPercent() {
		return (int)(correct/(correct*1d+wrong)*100);
	}

	/**
	 * This function returns a new Statistic object that is a copy of the current Statistic object.
	 *
	 * @return A new Statistic object with the same values as the original.
	 */
	public Statistic clone() {
		return new Statistic(correct, wrong, todo);
	}
}

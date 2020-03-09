package edu.westga.cs3270.model;

/**
 * The state class
 * 
 * @author Group H
 *
 */
public class State {
	private int xCoor;
	private int yCoor;
	private int reward;

	/**
	 * Constructs a state object
	 * 
	 * @preconditions xCoor > 0 && yCoor > 0
	 * @postconditions this.xCoor == this.getXCoor() && this.yCoor ==
	 *                 this.getYCoor()
	 * @param xCoor the x coordinate of the state
	 * @param yCoor the y coordinate of the state
	 * @param reward the state
	 */
	public State(int xCoor, int yCoor, int reward) {
		if (xCoor < 0) {
			throw new IllegalArgumentException("xCoor cannot be less than or equal to 0");
		}

		if (yCoor < 0) {
			throw new IllegalArgumentException("yCoor cannot be less than or equal to 0");
		}
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		this.reward = reward;
	}

	/**
	 * Sets the reward.
	 *
	 * @param reward the new reward
	 */
	public void setReward(int reward) {
		this.reward = reward;
	}

	/**
	 * Gets the xCoor
	 * 
	 * @preconditions none
	 * @postconditions none
	 * @return the xCoor
	 */
	public int getxCoor() {
		return this.xCoor;
	}

	/**
	 * Gets the xCoor
	 * 
	 * @preconditions none
	 * @postconditions none
	 * @return the yCoor
	 */
	public int getyCoor() {
		return this.yCoor;
	}

	/**
	 * Gets the reward
	 * @return the reward
	 */
	public int getReward() {
		return this.reward;
	}
}

/**
 * 
 */
package edu.westga.cs3270.model;

import java.util.ArrayList;
import java.util.List;

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
	private List<Action> actions;

	
	/**
	 * Constructs a state object
	 * @preconditions xCoor > 0 && yCoor > 0
	 * @postconditions this.xCoor == this.getXCoor() && this.yCoor == this.getYCoor()
	 * @param xCoor the x coordinate of the state
	 * @param yCoor the y coordinate of the state
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
		this.actions = new ArrayList<Action>();
	}
	
	public void addPossibleAction(Action action) {
		this.actions.add(action);
	}
	


	public void setReward(int reward) {
		this.reward = reward;
	}
	/**
	 * Gets the xCoor
	 * @preconditions none
	 * @postconditions none
	 * @return the xCoor
	 */
	public int getxCoor() {
		return this.xCoor;
	}
	
	/**
	 * Gets the Actions
	 * @preconditions none
	 * @postconditions none
	 * @return the action list
	 */
	public List<Action> getActions() {
		return this.actions;
	}


	/**
	 * Gets the xCoor
	 * @preconditions none
	 * @postconditions none
	 * @return the yCoor
	 */
	public int getyCoor() {
		return this.yCoor;
	}


	/**
	 * @return the reward
	 */
	public int getReward() {
		return reward;
	}
}

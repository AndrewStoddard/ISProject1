/**
 * 
 */
package edu.westga.cs3270.model;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Group H
 *
 */
public class StateGenerator {
	private List<State> states;
	private InputParser parser;
	
	
	/**
	 * Constructs a state generator object
	 * @preconditions parser != null
	 * @postconditions this.parser == parser
	 * 
	 * @param parser the parser object
	 */
	public StateGenerator(InputParser parser) {
		if (parser == null) {
			throw new IllegalArgumentException("Cannot have a null parser");
		}
		this.parser = parser;
		this.states = new ArrayList<State>();
		this.generateStates();
		
	
	}
	
	public Set<State> getLOIs(List<Point2D> lois) {
		Set<State> locationsOfInterest = new HashSet<State>();
		for (Point2D points : lois) {
			for (State state : this.states) {
				if (state.getxCoor() == points.getX() && state.getyCoor() == points.getY()) {
					locationsOfInterest.add(state);
				}
			}
		}
		return locationsOfInterest;
		
	}
	
	public State getStartState(Point2D startState) {
		State result = null;
		for (State state : this.states) {
			if (state.getxCoor() == startState.getX() && state.getyCoor() == startState.getY()) {
				result = state;
			}
		}
		return result;
	}
	
	/**
	 * Gets the states
	 * @preconditions none
	 * @postconditions none
	 * @return the states
	 */
	public List<State> getStates() {
		return this.states;
	}
	
	private void generateStates() {
		int[][] map = this.parser.getMap();
		for (int i = 0; i < this.parser.getMapWidth(); i++) {
			for(int j = 0; j < this.parser.getMapHeight(); j++) {
				State state = new State(i, j, map[i][j]);
				if (state.getyCoor() != 0) {
					state.addPossibleAction(Action.SOUTH);
				}
				if (state.getyCoor() != this.parser.getMapHeight() - 1) {
					state.addPossibleAction(Action.NORTH);
				}
				if (state.getxCoor() != 0) {
					state.addPossibleAction(Action.WEST);
				}
				if (state.getxCoor() != this.parser.getMapWidth() - 1) {
					state.addPossibleAction(Action.EAST);
				}
				if (state.getReward() == 0) { 
					state.setReward(state.getReward());
				}
				this.states.add(state);
				
			}
		}
	}
	
	
}

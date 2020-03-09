package edu.westga.cs3270.model;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The state generator class, generates potential states.
 * @author Group H
 *
 */
public class StateGenerator {
	
	/** The states. */
	private List<State> states;
	
	/** The parser. */
	private EnvironmentParser parser;
	
	
	/**
	 * Constructs a state generator object.
	 *
	 * @param parser the parser object
	 * @preconditions parser != null
	 * @postconditions this.parser == parser
	 */
	public StateGenerator(EnvironmentParser parser) {
		if (parser == null) {
			throw new IllegalArgumentException("Cannot have a null parser");
		}
		this.parser = parser;
		this.states = new ArrayList<State>();
		this.generateStates();
		
	
	}
	
	/**
	 * Gets the Location of interest based on point2D.
	 *
	 * @param lois the lois
	 * @return the LO is
	 */
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
	
	/**
	 * Gets the start state.
	 *
	 * @param startState the start state
	 * @return the start state
	 */
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
	 * Gets the states.
	 *
	 * @return the states
	 * @preconditions none
	 * @postconditions none
	 */
	public List<State> getStates() {
		return this.states;
	}
	
	/**
	 * Generate states.
	 */
	private void generateStates() {
		int[][] map = this.parser.getMap();
		for (int i = 0; i < this.parser.getMapWidth(); i++) {
			for (int j = 0; j < this.parser.getMapHeight(); j++) {
				State state = new State(i, j, map[i][j]);
				this.states.add(state);
				
			}
		}
	}
	
	
}

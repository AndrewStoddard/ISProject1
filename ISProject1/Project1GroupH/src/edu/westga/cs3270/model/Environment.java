/**
 * 
 */
package edu.westga.cs3270.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * @author Group H
 *
 */
public class Environment {

	private Map<State, List<Action>> stateMap;

	private int mapHeight;
	private int mapWidth;

	private State startState;
	private Set<State> locationsOfInterest;

	public Environment(EnvironmentParser eParser) {

		this.mapHeight = eParser.getMapHeight();
		this.mapWidth = eParser.getMapWidth();

		StateGenerator sg = new StateGenerator(eParser);
		this.startState = sg.getStartState(eParser.getStartPoint());
		this.locationsOfInterest = sg.getLOIs(eParser.getLocationsOfIntrest());

		this.stateMap = new HashMap<State, List<Action>>();
		for (State state : sg.getStates()) {
			this.stateMap.put(state, this.generatePossibleActions(state));
		}

	}

	/**
	 * 
	 * @return
	 */
	public Map<State, List<Action>> getStateMap() {
		return this.stateMap;
	}

	/**
	 * @return the startState
	 */
	public State getStartState() {
		return this.startState;
	}

	/**
	 * @return the locationsOfInterest
	 */
	public Set<State> getLocationsOfInterest() {
		return this.locationsOfInterest;
	}

	/**
	 * 
	 * @param currentState
	 * @param action
	 * @return
	 */
	public State getNewState(State currentState, Action action) {
		State state = null;
		int xCoor = currentState.getxCoor();
		int yCoor = currentState.getyCoor();
		switch (action) {
		case NORTH:
			yCoor++;
			break;
		case SOUTH:
			yCoor--;
			break;
		case WEST:
			xCoor--;
			break;
		case EAST:
			xCoor++;
			break;
		}

		for (Entry<State, List<Action>> entry : this.stateMap.entrySet()) {
			if (entry.getKey().getxCoor() == xCoor && entry.getKey().getyCoor() == yCoor) {
				state = entry.getKey();
			}
		}

		return state;
	}

	/**
	 * 
	 * @param state
	 * @return
	 */
	public boolean isLocationOfInterest(State state) {
		boolean isLocationOfInterest = false;
		if (this.locationsOfInterest.contains(state)) {
			isLocationOfInterest = true;
		}
		return isLocationOfInterest;
	}

	/**
	 * 
	 * @param state
	 * @return
	 */
	public List<Action> getPossibleActions(State state) {
		if (this.stateMap.containsKey(state)) {
			return this.stateMap.get(state);
		}
		return null;
	}

	private List<Action> generatePossibleActions(State state) {

		List<Action> actions = new ArrayList<Action>();
		for (int i = 0; i < this.mapWidth; i++) {
			for (int j = 0; j < this.mapHeight; j++) {
				if (state.getyCoor() != 0) {
					actions.add(Action.SOUTH);
				}
				if (state.getyCoor() != this.mapHeight - 1) {
					actions.add(Action.NORTH);
				}
				if (state.getxCoor() != 0) {
					actions.add(Action.WEST);
				}
				if (state.getxCoor() != this.mapWidth - 1) {
					actions.add(Action.EAST);
				}
			}
		}
		return actions;
	}
}

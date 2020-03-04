/**
 * 
 */
package edu.westga.cs3270.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * The Class Agent.
 *
 * @author Group H
 */
public class Agent {
	
	/** The q table. */
	private Map<State, Map<Action, Double>> qTable;
	
	/**
	 * Instantiates a new agent.
	 */
	public Agent() { 
		this.qTable = new HashMap<State, Map<Action, Double>>();
	}
	
	/**
	 * Initialize Q table.
	 *
	 * @param stateSet the state set
	 * @param actionSet the action set
	 */
	public void initializeQTable(Set<State> stateSet, Set<Action> actionSet) {
		//Add every state to hash map. For each one, make the map with each action that a state can perform and set the value to 0
		for (State state : stateSet) {
			for (Action action : actionSet) {
				Map<Action, Double> newMap = new HashMap<Action, Double>();
				newMap.put(action, 0.0);
				this.qTable.put(state, newMap);
			}
		}
	}
	
	/**
	 * Adjust value.
	 *
	 * @param state the state
	 * @param action the action
	 * @param value the value
	 */
	public void adjustValue(State state, Action action, double value) {
		//When the agent moves to a new state, the previous state (passed in) and the action taken will update that integer value.
		//i.e. If you move north, from the state (1,1) to  the state (1,2) then in the map, (1,1) - North - [new value]
		Map<Action, Double> newMap = new HashMap<Action, Double>();
		newMap.put(action, value);
		this.qTable.replace(state, newMap);
	}
	
}

/**
 * 
 */
package edu.westga.cs3270.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;
import java.util.Random;

import edu.westga.cs3270.Main;

/**
 * The Class Agent.
 *
 * @author Group H
 */
public class Agent {

	private Environment environment;
	private Map<State, Map<Action, Double>> qTable;
	private State currentState;
	private int health;

	/**
	 * 
	 * @param environment
	 */
	public Agent(Environment environment) {
		this.environment = environment;
		this.qTable = new HashMap<State, Map<Action, Double>>();
		this.health = 50;
		this.currentState = this.environment.getStartState();

		this.initializeQTable();
	}

	/**
	 * 
	 * 
	 */
	public void live() {

		this.currentState = this.environment.getStartState();
		this.health = 50;

		while (true) {
			this.move();
			if (this.environment.isLocationOfInterest(this.currentState)) {
				break;
			}
			if (this.isDead()) {
				break;
			}
		}

	}

	/**
	 * 
	 * @return
	 */
	public Map<State, Map<Action, Double>> getQTable() {
		return this.qTable;
	}
	
	public List<State> getBestPath() {
		List<State> states = new ArrayList<State>();
		
		Queue<State> q = new LinkedList<State>();
		q.add(this.environment.getStartState());
		while (!q.isEmpty()) {
			Action bestAction = null;
			double maxQ = 0.0;
			State state = q.remove();
			states.add(state);
			
			for (Entry<Action, Double> entry : this.qTable.get(state).entrySet()) {
				if (bestAction == null) {
					maxQ = entry.getValue();
					bestAction = entry.getKey();
				} else {
					if (maxQ < entry.getValue()) {
						maxQ = entry.getValue();
						bestAction = entry.getKey();
					}
				}
			}
			if (this.environment.isLocationOfInterest(state) || maxQ < 0.0) {
				break;
			}
			q.add(this.environment.getNewState(state, bestAction));
			
		}
		return states;
	}

	private void initializeQTable() {
		for (Entry<State, List<Action>> entry : this.environment.getStateMap().entrySet()) {
			this.qTable.put(entry.getKey(), new HashMap<Action, Double>());

			for (Action action : entry.getValue()) {
				this.qTable.get(entry.getKey()).put(action, 0.0);
			}
		}

	}

	private void move() {
		Action moveDirection = this.getDirectionToMove();
		State oldState = this.currentState;
		this.currentState = this.environment.getNewState(this.currentState, moveDirection);
		this.health = this.health + this.currentState.getReward();
		this.updateQValue(moveDirection, oldState);

	}

	private Double getMaxQValue(State state) {
		Double qValue = 0.0;
		boolean first = true;
		for (Entry<Action, Double> entry : this.qTable.get(state).entrySet()) {
			if (first) {
				qValue = entry.getValue();
				first = false;
			}
			if (entry.getValue() > qValue) {
				qValue = entry.getValue();
			}
		}

		return qValue;
	}

	private boolean isDead() {
		boolean result = false;
		if (this.health <= 0) {
			result = true;
		}

		return result;
	}

	private boolean isExploit() {
		return (Math.random() > Main.getEpsilon());
	}

	private Action getDirectionToMove() {
		Action result = null;

		if (this.isExploit()) {
			Double qValue = 0.0;
			for (Entry<Action, Double> entry : this.qTable.get(this.currentState).entrySet()) {
				if (entry.getValue() > qValue) {
					qValue = entry.getValue();
					result = entry.getKey();
				}
			}
		}

		if (result == null) {
			result = this.environment.getPossibleActions(this.currentState)
					.get(new Random().nextInt(this.environment.getPossibleActions(this.currentState).size()));
		}

		return result;
	}

	private void updateQValue(Action action, State oldState) {
		Double oldQValue = this.qTable.get(oldState).get(action);

		Double newQValue = ((1 - Main.getAlpha()) * oldQValue) + Main.getAlpha()
				* (this.currentState.getReward() + Main.getAlpha() * this.getMaxQValue(this.currentState));

		this.qTable.get(oldState).replace(action, newQValue);
	}

}

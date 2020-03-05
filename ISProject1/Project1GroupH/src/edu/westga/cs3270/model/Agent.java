/**
 * 
 */
package edu.westga.cs3270.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import edu.westga.cs3270.Main;

/**
 * The Class Agent.
 *
 * @author Group H
 */
public class Agent {
	
	/** The q table. */
	private Map<State, Map<Action, Double>> qTable;
	private State currentState;
	private Random rand;
	private Set<State> locationsOfInterest;
	private List<State> resultingList;
	private int health;
	private State startState;

	public Agent(State startState, Set<State> locationsOfInterest, List<State> allStates) {
		this.qTable = new HashMap<State, Map<Action, Double>>();
		this.resultingList = new ArrayList<State>();
		this.health = 50;
		this.startState = startState;
		
		this.rand = new Random();
		this.currentState = startState;
		
		this.locationsOfInterest = locationsOfInterest;
		this.initializeQTable(allStates);
	}
	
	public void run(int episodeCount) {
		int episodes = 0;
		
		for (int i = 0; i < episodeCount; i++) {
			System.out.println();

			int moves = 0;
			
			this.currentState = this.startState;
			this.health = 50;
			this.resultingList = new ArrayList<State>();
			
			while(true) {
				moves++;
				this.move();
				if (this.isLOI()) {					
					System.out.println("Goal");

					break;
				}
				if (this.isDead()) {
					System.out.println("Die");
					break;
				}
			}
			System.out.println("Episode: " + episodes);
			for(State state : this.resultingList) {
				System.out.print("State: " + state.getxCoor() + " : " + state.getyCoor() + " : " + state.getReward() + " ; Actions : ");
				for (Action action : state.getActions()) {
					System.out.print(action + ", ");
				}
				System.out.println();
			}
			System.out.println("Final State : " + this.currentState.getxCoor() + " : " + this.currentState.getyCoor() + " : " + this.currentState.getReward());
			
			System.out.println("Moves: " + moves);
			episodes++;
			
			
			
			
		}
	}
	
	public List<State> getResultingList() {
		return this.resultingList;
	}
	
	public Map<State, Map<Action, Double>> getQTable() {
		return this.qTable;
	}

	public void initializeQTable(List<State> states) {
		for (State state : states) {
			this.qTable.put(state, new HashMap<Action, Double>());
			for (Action action : state.getActions()) {
				this.qTable.get(state).put(action, 0.0);
			}
		}
		
		

	}

	public void move() {
		Action moveDirection = this.getDirectionToMove();
		State newState = this.getNewState(moveDirection);
		State oldState = this.currentState;
		this.currentState = newState;
		this.health = this.health + this.currentState.getReward();
		this.resultingList.add(oldState);
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
	
	private boolean isLOI() {
		boolean result = false;
		for (State state : this.locationsOfInterest) {
			if (state.equals(this.currentState)) {
				result = true;
			}
		}
		return result;
	}
	
	private boolean isDead() {
		boolean result = false;
		if (this.health <= 0) {
			result = true;
		}
		
		return result;
	}

	private State getNewState(Action action) {
		State newState = null;
		int xCoor = this.currentState.getxCoor();
		int yCoor = this.currentState.getyCoor();
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

		for (Entry<State, Map<Action, Double>> entry : this.qTable.entrySet()) {
			if (entry.getKey().getxCoor() == xCoor && entry.getKey().getyCoor() == yCoor) {
				newState = entry.getKey();
			}
		}
		//Testing purposes
		if (newState == null) {
			
			for(State state : this.resultingList) {
				System.out.print("State: " + state.getxCoor() + " : " + state.getyCoor() + " : " + state.getReward() + " ; Actions : ");
				for (Action actions : state.getActions()) {
					System.out.print(actions + ", ");
				}
				System.out.println();
			}
			
			
			System.out.print("Final State : " + this.currentState.getxCoor() + " : " + this.currentState.getyCoor() + " : " + this.currentState.getReward() + " : ");
			for (Action actions : this.currentState.getActions()) {
				System.out.print(actions + ", ");
			}
			System.out.println("Null State : " + xCoor + " : " + yCoor);
		}
		return newState;
	}

	/**
	 * 
	 * @return true if exploit false if explore
	 */
	private boolean exploreOrExploit() {
		return (Math.random() > Main.getEpsilon());
	}

	private Action getDirectionToMove() {
		Double qValue = 0.0;
		Action result = null;

		if (this.exploreOrExploit()) {
			for (Entry<Action, Double> entry : this.qTable.get(this.currentState).entrySet()) {
				if (entry.getValue() > qValue) {
					qValue = entry.getValue();
					result = entry.getKey();
					
				}

			}
			if (result == null) {
				result = (Action) this.qTable.get(this.currentState).keySet().toArray()[this.rand
						.nextInt(this.qTable.get(this.currentState).keySet().toArray().length)];
			}

		} else {
			result = (Action) this.qTable.get(this.currentState).keySet().toArray()[this.rand
					.nextInt(this.qTable.get(this.currentState).keySet().toArray().length)];

		}
		return result;
	}

	private void updateQValue(Action action, State oldState) {
		Double oldQValue = this.qTable.get(oldState).get(action);
		
		Double newQValue = ((1 - Main.getAlpha()) * oldQValue) + Main.getAlpha() * (this.currentState.getReward() + Main.getAlpha() * this.getMaxQValue(this.currentState));
				
		this.qTable.get(oldState).replace(action, newQValue);
	}

}

/**
 * 
 */
package edu.westga.cs3270.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Group H
 *
 */
public class StateGenerator {
	private List<State> states;
	private InputParser parser;
	
	public StateGenerator(InputParser parser) {
		this.parser = parser;
		this.states = new ArrayList<State>();
		this.generateStates();
		for(State state : this.states) {
			System.out.print("State: " + state.getxCoor() + " : " + state.getyCoor() + " : " + state.getReward() + " ; Actions : ");
			for (Action action : state.getActions()) {
				System.out.print(action + ", ");
			}
			System.out.println();
		}
	}
	
	private void generateStates() {
		int[][] map = this.parser.getMap();
		for (int i = 0; i < this.parser.getMapWidth(); i++) {
			for(int j = 0; j < this.parser.getMapHeight(); j++) {
				State state = new State(j, i, map[j][i]);
				if (state.getxCoor() != 0) {
					state.addPossibleAction(Action.SOUTH);
				}
				if (state.getxCoor() != this.parser.getMapHeight() - 1) {
					state.addPossibleAction(Action.NORTH);
				}
				if (state.getyCoor() != 0) {
					state.addPossibleAction(Action.WEST);
				}
				if (state.getyCoor() != this.parser.getMapWidth()) {
					state.addPossibleAction(Action.EAST);
				}
				this.states.add(state);
				
			}
		}
	}
	
	
}

package edu.westga.cs3270;

import java.util.List;
import java.util.Set;

import edu.westga.cs3270.model.Action;
import edu.westga.cs3270.model.Agent;
import edu.westga.cs3270.model.InputParser;
import edu.westga.cs3270.model.State;
import edu.westga.cs3270.model.StateGenerator;

/**The Main Class
 * @author Group H
 *
 */
public class Main {
	
	private static final int RUN_MODE = 1;
	public static final int EPISODE_COUNT = 1;
	public static double epsilon = 0.0;
	public static double gamma = 0.0;
	public static double alpha = 0.0;
	
	
    public static void main(String[] args) throws InterruptedException {
    	setHyperParameters();
        String input = "8x4" + System.lineSeparator()
        + "0,0,-100,0,0,0,0,0" + System.lineSeparator()
        + "0,0,0,0,0,0,-150,0" + System.lineSeparator()
        + "0,-100,0,0,0,-120,0,0" + System.lineSeparator()
        + "Start,-100,-100,0,-100,-100,-100,LOI+100";
        InputParser inputParser = new InputParser(input);
        
        StateGenerator sg = new StateGenerator(inputParser);

        Agent agent = new Agent(sg.getStartState(inputParser.getStartPoint()), sg.getLOIs(inputParser.getLocationsOfIntrest()), sg.getStates());
        
        agent.run(100);
        List<State> states = agent.getResultingList();
        
        for(State state : states) {
			System.out.print("State: " + state.getxCoor() + " : " + state.getyCoor() + " : " + state.getReward() + " ; Actions : ");
			for (Action action : state.getActions()) {
				System.out.print(action + ", ");
			}
			System.out.println();
		}
        
    }
    
    private static void setHyperParameters() {
    	switch (RUN_MODE) {
    	case 1:
    		epsilon = 0.1;
    		gamma = 0.9;
    		alpha = 0.5;
    		break;
    	case 2:
    		epsilon = 1.0;
    		gamma = 0.9;
    		alpha = 0.5;
    		break;
    	case 3:
    		epsilon = 0.0;
    		gamma = 0.9;
    		alpha = 0.5;
    		break;
    	case 4:
    		epsilon = 0.1;
    		gamma = 0.2;
    		alpha = 0.7;
    		break;
    	}
    }
}

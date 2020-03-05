package edu.westga.cs3270;

import java.util.Map;

import edu.westga.cs3270.model.Action;
import edu.westga.cs3270.model.Agent;
import edu.westga.cs3270.model.EnvironmentParser;
import edu.westga.cs3270.model.State;
import edu.westga.cs3270.model.StateGenerator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Main Class.
 *
 * @author Group H
 */
public class Main extends Application {

	/** The Constant RUN_MODE. */
	private static final int RUN_MODE = 1;
	
	/** The Constant EPISODE_COUNT. */
	public static final int EPISODE_COUNT = 1;
	
	/** The epsilon. */
	private static double epsilon = 0.0;
	
    public static void main(String[] args) throws InterruptedException {
		Main.launch(args);
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

	/** The gamma. */
	private static double gamma = 0.0;
	
	/** The alpha. */
	private static double alpha = 0.0;

	/**
	 * Gets the epsilon.
	 *
	 * @return the epsilon
	 */
	public static double getEpsilon() {
		return epsilon;
	}

	/**
	 * Gets the gamma.
	 *
	 * @return the gamma
	 */
	public static double getGamma() {
		return gamma;
	}

	/**
	 * Gets the alpha.
	 *
	 * @return the alpha
	 */
	public static double getAlpha() {
		return alpha;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		setHyperParameters();
        String input = "8x4" + System.lineSeparator()
        + "0,0,-100,0,0,0,0,0" + System.lineSeparator()
        + "0,0,0,0,0,0,-150,0" + System.lineSeparator()
        + "0,-100,0,0,0,-120,0,0" + System.lineSeparator()
        + "Start,-100,-100,0,-100,-100,-100,LOI+100";
        EnvironmentParser parser = new EnvironmentParser(input);
        
        StateGenerator sg = new StateGenerator(parser);

        Agent agent = new Agent(sg.getStartState(parser.getStartPoint()), sg.getLOIs(parser.getLocationsOfIntrest()), sg.getStates());
        
        //agent.run(50000);
        //List<State> states = agent.getResultingList();
        Map<State, Map<Action, Double>> qTable = agent.getQTable();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/main.fxml"));
		loader.load();
		
		Scene scene = new Scene(loader.getRoot());
		primaryStage.setTitle("main");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}

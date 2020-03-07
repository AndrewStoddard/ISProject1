package edu.westga.cs3270;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import edu.westga.cs3270.model.Action;
import edu.westga.cs3270.model.Agent;
import edu.westga.cs3270.model.Environment;
import edu.westga.cs3270.model.EnvironmentParser;
import edu.westga.cs3270.model.State;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
	public static final int EPISODE_COUNT = 1000000;

	/** The epsilon. */
	private static double epsilon = 0.0;

	@Override
	public void start(Stage primaryStage) throws Exception {

		setHyperParameters();
		String input = "8x4" + System.lineSeparator() + "0,0,-100,0,0,0,0,0" + System.lineSeparator()
				+ "0,0,0,0,0,0,-150,0" + System.lineSeparator() + "0,-100,0,0,0,-120,0,0" + System.lineSeparator()
				+ "Start,-100,-100,0,-100,-100,-100,LOI+100";
		EnvironmentParser parser = new EnvironmentParser(input);
		Environment env = new Environment(parser);


		Agent agent = new Agent(env);
		for (int i = 1; i < EPISODE_COUNT; i++) {

			agent.live();

		}

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setVgap(5);
		grid.setHgap(8);
		
		
		List<StackPane> stackList = new ArrayList<StackPane>();
		
		for (Entry<State, List<Action>> entry : env.getStateMap().entrySet()) {
			StackPane stack = new StackPane();
						
			Text text = new Text(agent.getQValuesAsString(entry.getKey()));
			text.setFill(Color.WHITE);
			text.setTextAlignment(TextAlignment.CENTER);
			
			stackList.add(stack);
			
			stack.getChildren().addAll(makeRectangle(Color.BLACK), text);
			GridPane.setConstraints(stack, entry.getKey().getxCoor(), entry.getKey().getyCoor());
		}

		grid.getChildren().addAll(stackList);

		Scene scene = new Scene(grid);
		primaryStage.setTitle("Test");
		primaryStage.setScene(scene);

		primaryStage.show();

		

		showBestPath(agent, stackList);


	}

	private static void showBestPath(Agent agent, List<StackPane> stackList) {
		for (State state : agent.getBestPath()) {
			for (StackPane steck : stackList) {
				if (GridPane.getColumnIndex(steck) == state.getxCoor()
						&& GridPane.getRowIndex(steck) == state.getyCoor()) {
					steck.getChildren().remove(1);
					
					steck.getChildren().addAll(makeRectangle(Color.YELLOW), new Text(agent.getQValuesAsString(state)));
				}

			}
		}
	}

	private static Rectangle makeRectangle(Color color) {
		Rectangle r = new Rectangle();

		r.setWidth(250);
		r.setHeight(150);
		r.setArcWidth(20);
		r.setArcHeight(20);
		r.setFill(color);
		return r;
	}

	public static void main(String[] args) throws InterruptedException {
		Main.launch(args);
		System.exit(0);
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

}

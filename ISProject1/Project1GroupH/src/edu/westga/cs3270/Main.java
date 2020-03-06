package edu.westga.cs3270;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import edu.westga.cs3270.model.Action;
import edu.westga.cs3270.model.Agent;
import edu.westga.cs3270.model.Environment;
import edu.westga.cs3270.model.EnvironmentParser;
import edu.westga.cs3270.model.State;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
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
		String input = "8x4" + System.lineSeparator() + "Start,0,-100,0,0,0,0,0" + System.lineSeparator()
				+ "0,0,0,0,0,0,-150,0" + System.lineSeparator() + "0,-100,0,0,0,-120,0,0" + System.lineSeparator()
				+ "0,-100,-100,0,-100,-100,-100,LOI+100";
		EnvironmentParser parser = new EnvironmentParser(input);
		Environment env = new Environment(parser);

		Agent agent = new Agent(env);

		GridPane grid = new GridPane();
		grid.setPadding(new Insets(10, 10, 10, 10));
		grid.setVgap(8);
		grid.setHgap(10);
		List<Rectangle> list = new ArrayList<Rectangle>();
		for (Entry<State, List<Action>> entry : env.getStateMap().entrySet()) {
			Rectangle r = new Rectangle();
			r.setX(50);
			r.setY(50);
			r.setWidth(200);
			r.setHeight(100);
			r.setArcWidth(20);
			r.setArcHeight(20);
			r.setFill(Color.BLACK);
			list.add(r);
			GridPane.setConstraints(r, entry.getKey().getxCoor(), entry.getKey().getyCoor());
		}

		grid.getChildren().addAll(list);

		Scene scene = new Scene(grid);
		primaryStage.setTitle("Test");
		primaryStage.setScene(scene);
		beAlive(agent, list).start();
		
		primaryStage.show();
		
	}

	/**
	 * The for loop in the thread will change the colors of the squares. I just don't know how to thread.
	 * @param agent
	 * @param list
	 * @return
	 */
	public static Thread beAlive(Agent agent, List<Rectangle> list) {
		return new Thread(() -> {
			for (int i = 0; i < EPISODE_COUNT; i++) {
				for (Rectangle rect : list) {
					rect.setFill(Color.BLACK);

				}
				agent.live();
				
				for (State state : agent.getBestPath()) {
					for (Rectangle rect : list) {
						if (GridPane.getColumnIndex(rect) == state.getxCoor()
								&& GridPane.getRowIndex(rect) == state.getyCoor()) {
							rect.setFill(Color.WHITE);
						}

					}
				}
			}
			
			;

		});
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

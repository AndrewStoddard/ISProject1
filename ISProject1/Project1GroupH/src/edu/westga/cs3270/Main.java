package edu.westga.cs3270;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

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

	/** The Constant EPISODE_COUNT. */
	private static int episodeCount = 0;

	/** The epsilon. */
	private static double epsilon = 0.0;

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scanner scan = new Scanner(System.in);
		EnvironmentParser envParser = getFileMapFromInput(scan);
		getHyperParametersFromInput(scan);
		getEpisodeCountFromInput(scan);
		scan.close();
		Environment env = new Environment(envParser);
		Agent agent = new Agent(env);
		for (int i = 0; i < episodeCount; i++) {
			agent.live();
		}
		getAnswerTo1(agent, env);
		GridPane grid = new GridPane();
		grid.setPadding(new Insets(20, 20, 20, 20));
		grid.setVgap(5);
		grid.setHgap(8);
		List<StackPane> stackList = new ArrayList<StackPane>();
		for (Entry<State, List<Action>> entry : env.getStateMap().entrySet()) {
			StackPane stack = new StackPane();
			Text text = new Text(Integer.toString(entry.getKey().getReward()));
			text.setFill(Color.WHITE);
			text.setTextAlignment(TextAlignment.CENTER);
			stackList.add(stack);
			stack.getChildren().addAll(makeRectangle(Color.BLACK), text);
			GridPane.setConstraints(stack, entry.getKey().getxCoor(), entry.getKey().getyCoor());
		}
		grid.getChildren().addAll(stackList);
		Scene scene = new Scene(grid);
		primaryStage.setTitle("Best Path After " + Main.episodeCount + "episodes");
		primaryStage.setScene(scene);
		primaryStage.show();
		showBestPath(agent, stackList);
	}
	
	private static void getAnswerTo1(Agent agent, Environment env) {
		for (Entry<Integer, State> entry : agent.getMoveList().entrySet()) {
			System.out.println(entry.getKey() + " : " + entry.getValue().getxCoor() + " : " + entry.getValue().getyCoor());
		}
		System.out.println("Q Value for North of Start: " + agent.getQTable().get(env.getStartState()).get(Action.NORTH));
	}
	
	private static EnvironmentParser getFileMapFromInput(Scanner scan) {
		boolean validFile = false;

		EnvironmentParser envParser = null;
		while (!validFile) {
			System.out.print("Enter the file path to the CSV file: ");
			String filePath = scan.next();
			try {
				envParser = new EnvironmentParser(new File(filePath));
				validFile = true;
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());

			}
		}
		return envParser;
	}

	private static void getHyperParametersFromInput(Scanner scan) {
		boolean validDouble = false;
		System.out.println();
		while (!validDouble) {
			System.out.print("Enter an alpha value: ");
			try {
				Main.alpha = Double.parseDouble(scan.next());
				validDouble = true;
			} catch (NumberFormatException e) {
				System.out.println("Invalid Value");
			}
		}
		validDouble = false;
		System.out.println();
		while (!validDouble) {
			System.out.print("Enter an gamma value: ");
			try {
				Main.gamma = Double.parseDouble(scan.next());
				validDouble = true;
			} catch (NumberFormatException e) {
				System.out.println("Invalid Value");
			}
		}
		validDouble = false;
		System.out.println();
		while (!validDouble) {
			System.out.print("Enter an epsilon value: ");
			try {
				Main.epsilon = Double.parseDouble(scan.next());
				validDouble = true;
			} catch (NumberFormatException e) {
				System.out.println("Invalid Value");
			}
		}
	}
	
	private static void getEpisodeCountFromInput(Scanner scan) {
		boolean validInteger = false;
		while (!validInteger) {
			System.out.print("Enter an episode count: ");
			try {
				Main.episodeCount = Integer.parseInt(scan.next());
				validInteger = true;

			} catch (NumberFormatException e) {
				System.out.println("Invalid Value");
			}
		}
	}

	private static void showBestPath(Agent agent, List<StackPane> stackList) {
		for (State state : agent.getBestPath()) {
			for (StackPane steck : stackList) {
				if (GridPane.getColumnIndex(steck) == state.getxCoor()
						&& GridPane.getRowIndex(steck) == state.getyCoor()) {
					steck.getChildren().remove(1);

					steck.getChildren().addAll(makeRectangle(Color.YELLOW),
							new Text(Integer.toString(state.getReward())));
				}

			}
		}
	}

	private static Rectangle makeRectangle(Color color) {
		Rectangle rectangle = new Rectangle();

		rectangle.setWidth(50);
		rectangle.setHeight(50);
		rectangle.setArcWidth(20);
		rectangle.setArcHeight(20);
		rectangle.setFill(color);
		return rectangle;
	}

	/**Main method
	 * 
	 * @param args command line args
	 * @throws InterruptedException throws for jfx
	 */
	public static void main(String[] args) throws InterruptedException {
		Main.launch(args);
		System.exit(0);
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

package edu.westga.cs3270;

import edu.westga.cs3270.model.InputParser;
import edu.westga.cs3270.model.StateGenerator;

/**
 * The Main Class.
 *
 * @author Group H
 */
public class Main {

	/** The Constant RUN_MODE. */
	private static final int RUN_MODE = 1;
	
	/** The Constant EPISODE_COUNT. */
	public static final int EPISODE_COUNT = 1;
	
	/** The epsilon. */
	private static double epsilon = 0.0;
	
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

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		setHyperParameters();
		String input = "8x4" + System.lineSeparator() + "0,0,-100,0,0,0,0,0" + System.lineSeparator()
				+ "0,0,0,0,0,0,-150,0" + System.lineSeparator() + "0,-100,0,0,0,-120,0,0" + System.lineSeparator()
				+ "Start,-100,-100,0,-100,-100,-100,LOI+100";
		InputParser inputParser = new InputParser(input);

		StateGenerator sg = new StateGenerator(inputParser);

	}

	/**
	 * Sets the hyper parameters.
	 */
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
			default:
				break;
		}
	}
}

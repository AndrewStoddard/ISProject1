package edu.westga.cs3270;

import edu.westga.cs3270.model.*;

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
	
	
    public static void main(String[] args) {
    	setHyperParameters();
        String input = "8x4" + System.lineSeparator()
        + "0,0,-100,0,0,0,0,0" + System.lineSeparator()
        + "0,0,0,0,0,0,-150,0" + System.lineSeparator()
        + "0,-100,0,0,0,-120,0,0" + System.lineSeparator()
        + "Start,-100,-100,0,-100,-100,-100,LOI+100";
        var inputParser = new InputParser(input);

        System.out.println("Dim: " + inputParser.getMapWidth() + " x " + inputParser.getMapHeight());
        System.out.println("Start: " + inputParser.getStartPoint().getX() + ", " + inputParser.getStartPoint().getY());
        System.out.println("LOIs: " + inputParser.getLocationsOfIntrest().get(0).getX() + ", " + inputParser.getLocationsOfIntrest().get(0).getY());
        System.out.println("Start Val: " + inputParser.getMap()[0][0]);
       
        int[][] table = inputParser.getMap();
        for (int i = inputParser.getMapHeight() - 1; i >= 0; i--) {
        	for (int j = 0; j < inputParser.getMapWidth(); j++) {
        		System.out.print(table[i][j] + " ");
        	}
        	System.out.println("");
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

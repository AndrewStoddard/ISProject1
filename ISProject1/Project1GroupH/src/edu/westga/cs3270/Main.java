package edu.westga.cs3270;

import edu.westga.cs3270.model.*;

/**The Main Class
 * @author Group H
 *
 */
public class Main {
    public static void main(String[] args) {
        String input = "8x4" + System.lineSeparator()
        + "0,0,-100,0,0,0,0,0" + System.lineSeparator()
        + "0,0,0,0,0,0,-150,0" + System.lineSeparator()
        + "0,-100,0,0,0,-120,0,0" + System.lineSeparator()
        + "Start,-100,-100,0,-100,-100,-100,LOI+100";
        var inputParser = new InputParser(input);

        System.out.println("Dim: " + inputParser.getWidth() + " x " + inputParser.getHeight());
        System.out.println("Start: " + inputParser.getStartPoint().getX() + ", " + inputParser.getStartPoint().getY());
        System.out.println("LOIs: " + inputParser.getLocationsOfIntrest().get(0).getX() + ", " + inputParser.getLocationsOfIntrest().get(0).getY());

        for (int[] row : inputParser.getMap()) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println("");
        }
    }
}

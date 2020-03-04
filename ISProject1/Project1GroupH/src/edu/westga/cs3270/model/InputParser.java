package edu.westga.cs3270.model;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Parses csv style input into data that the "controller" can use.
 * 
 * @author Group 1
 */
public class InputParser {

    private String input;
    private int mapWidth;
    private int mapHeight;
    private Point2D start;
    private ArrayList<Point2D> locationsOfIntrest;
    private int[][] map;

    /**
     * Constructs an InputParser and uses a file for input.
     * 
     * @param file The input file.
     */
    public InputParser(File file) {
        this.locationsOfIntrest = new ArrayList<Point2D>();
        try (Scanner scanner = new Scanner(file, "UTF-8")) {
            this.input = scanner.useDelimiter("\\A").next();
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found.");
        }
        this.parseInput();
    }

    /**
     * Constructs an InputParser and a string as input.
     * 
     * @param input The input string.
     */
    public InputParser(String input) {
        this.locationsOfIntrest = new ArrayList<Point2D>();
        this.input = input;
        this.parseInput();
    }

    private void parseInput() {
        String[] inputSplit = this.input.split(System.lineSeparator());
        if (inputSplit.length < 2) {
            throw new IllegalArgumentException("Not enough lines in input.");
        }
        try {
            String[] firstLineSplit = inputSplit[0].split("x");
            this.mapWidth = Integer.parseInt(firstLineSplit[0]);
            this.mapHeight = Integer.parseInt(firstLineSplit[1]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Parse error on line 1.");
        }
        this.map = new int[this.mapHeight][this.mapWidth];

        List<String> rows = Arrays.asList(Arrays.copyOfRange(inputSplit, 1, inputSplit.length));
        Collections.reverse(rows);
        this.parseRows(rows);
    }

    private void parseRows(List<String> rows) {
        int lineNumber = -1;
        try {
            for (int i = 0; i < rows.size(); i++) {
                lineNumber = i + 1;
                String[] rowValues = rows.get(i).split(",");
                for (int j = 0; j < rowValues.length; j++) {
                    String currentRowValue = rowValues[j];
                    if (currentRowValue.equals("Start")) {
                        this.start = new Point2D.Double(i, j);
                        this.map[i][j] = 0;
                    } else if (currentRowValue.contains("LOI")) {
                        this.locationsOfIntrest.add(new Point2D.Double(i, j));
                        this.map[i][j] = Integer.parseInt(currentRowValue.replace("LOI+", ""));
                    } else {
                        this.map[i][j] = Integer.parseInt(currentRowValue);
                    }
                }
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Parse error on line " + lineNumber + ".");
        }
        if (this.start == null) {
            throw new IllegalArgumentException("No Start was found");
        }
        if (this.locationsOfIntrest.isEmpty()) {
            throw new IllegalArgumentException("No LOIs were found");
        }
    }

    /**
     * Returns the width of the map.
     * 
     * @return the width of the map.
     */
    public int getMapWidth() {
        return this.mapWidth;
    }

    /**
     * Returns the height of the map.
     * 
     * @return the height of the map.
     */
    public int getMapHeight() {
        return this.mapHeight;
    }

    /**
     * Returns the start point.
     * 
     * @return the start point.
     */
    public Point2D getStartPoint() {
        return this.start;
    }

    /**
     * Returns a list of Locations of intrest.
     * 
     * @return a list of Locations of intrest.
     */
    public ArrayList<Point2D> getLocationsOfIntrest() {
        return this.locationsOfIntrest;
    }

    /**
     * Returns the map.
     * 
     * @return the map.
     */
    public int[][] getMap() {
        return this.map;
    }
}
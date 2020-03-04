package edu.westga.cs3270.model;

import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InputParser {

    private String input;
    private int width;
    private int height;
    private Point2D start;
    private ArrayList<Point2D> LOIs;
    private int[][] map;

    public InputParser(File file) {
        this.LOIs = new ArrayList<Point2D>();
        this.input = "";
        this.parseInput();
    }

    public InputParser(String input) {
        this.LOIs = new ArrayList<Point2D>();
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
            this.width = Integer.parseInt(firstLineSplit[0]);
            this.height = Integer.parseInt(firstLineSplit[1]);
            this.map = new int[this.height][this.width];
        } catch (Exception e) {
            throw new IllegalArgumentException("Parse error on line 1.");
        }
        int lineNumber = -1;
        List<String> rows = Arrays.asList(Arrays.copyOfRange(inputSplit, 1, inputSplit.length));
        Collections.reverse(rows);
        try {
            for (int i = 0; i < rows.size(); i++) {
                lineNumber = i + 1;
                String[] rowValues = rows.get(i).split(",");
                for (int j = 0; j < rowValues.length; j++) {
                    String currentRowValue = rowValues[j];
                    if (currentRowValue.equals("Start")) {
                        this.start = new Point2D.Double(i, j);
                        this.map[i][j] = 100;
                    } else if (currentRowValue.contains("LOI")) {
                        this.LOIs.add(new Point2D.Double(i, j));
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
        if (this.LOIs.isEmpty()) {
            throw new IllegalArgumentException("No LOIs were found");
        }
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public Point2D getStartPoint() {
        return this.start;
    }

    public ArrayList<Point2D> getLocationsOfIntrest() {
        return this.LOIs;
    }

    public int[][] getMap() {
        return this.map;
    }
}
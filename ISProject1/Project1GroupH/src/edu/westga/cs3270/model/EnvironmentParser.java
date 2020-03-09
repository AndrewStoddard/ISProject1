package edu.westga.cs3270.model;

import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Parses csv style input into data that the "controller" can use.
 * 
 * @author Group 1
 */
public class EnvironmentParser {

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
    public EnvironmentParser(File file) {
        this.locationsOfIntrest = new ArrayList<Point2D>();
        try {
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
            String nextLine = reader.readLine();
            
            while (nextLine != null) {
            	if (nextLine.startsWith("\uFEFF")) {
                	nextLine = nextLine.substring(1);
                }
            	this.input += nextLine + "\n";
            	nextLine =  reader.readLine();
            }
            if (this.input.contains("null")) {
            	this.input = this.input.substring(4);
            }
            
            
            reader.close();
            
            		
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File not found.");
        } catch (IOException e) {
			e.printStackTrace();
		}
  
        
        this.parseInput();
    }

    /**
     * Constructs an InputParser and a string as input.
     * 
     * @param input The input string.
     */
    public EnvironmentParser(String input) {
        this.locationsOfIntrest = new ArrayList<Point2D>();
        this.input = input;
        this.parseInput();
    }

    private void parseInput() {	
        String[] inputSplit = this.input.split("\n");
        
        if (inputSplit.length < 2) {
            throw new IllegalArgumentException("Not enough lines in input.");
        }
        try {
            String[] firstLineSplit = inputSplit[0].split("x");
            
            this.mapWidth = Integer.parseInt(firstLineSplit[0].strip());
            this.mapHeight = Integer.parseInt(firstLineSplit[1].strip());
        } catch (Exception e) {
            throw new IllegalArgumentException("Parse error on line 1.");
        }
        this.map = new int[this.mapWidth][this.mapHeight];

        List<String> rows = Arrays.asList(Arrays.copyOfRange(inputSplit, 1, inputSplit.length));
        this.parseRows(rows);
    }
    
    private void parseRows(List<String> rows) {
        int lineNumber = 1;
        try {
            for (int y = rows.size() - 1; y >= 0; y--) {
                String[] split = rows.get(y).split(",");
                
                for (int x = split.length - 1; x >= 0; x--) {
                    if (split[x].equals("Start")) {
                        this.start = new Point2D.Double(x, y);
                        this.map[x][y] = 0;
                    } else if (split[x].contains("LOI")) {
                        this.locationsOfIntrest.add(new Point2D.Double(x, y));
                        this.map[x][y] = Integer.parseInt(split[x].replace("LOI+", ""));
                    } else {
                        this.map[x][y] = Integer.parseInt(split[x].strip());
                    }
                    
                }
                lineNumber++;
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
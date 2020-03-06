/**
 * 
 */
package edu.westga.cs3270.view;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

/**
 * @author csuser
 *
 */
public class MainCodeBehind {
	
	@FXML
	private GridPane grid;
	
	
	
	
	
	
	
	@FXML
	void initialize() {
		this.grid = new GridPane();
		this.grid.setPadding(new Insets(10, 10, 10, 10));
		this.grid.setVgap(8);
		this.grid.setHgap(10);
		
		Rectangle r = new Rectangle();
		r.setX(50);
		r.setY(50);
		r.setWidth(200);
		r.setHeight(100);
		r.setArcWidth(20);
		r.setArcHeight(20);
		
		GridPane.setConstraints(r, 0, 0);
		
		grid.getChildren().addAll(r);
		
	}
}

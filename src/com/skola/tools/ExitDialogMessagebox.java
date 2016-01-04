package com.skola.tools;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Simple dialogbox for handling exiting the app
 * 
 * @author seb
 * 
 */
public class ExitDialogMessagebox extends Stage
{

	// the return value
	private Boolean answear = false;

	public ExitDialogMessagebox()
	{

		// init values
		setResizable(false);
		initModality(Modality.APPLICATION_MODAL);
		initStyle(StageStyle.DECORATED);

		final Label label = new Label("Do you want to exit?");
		label.setWrapText(true);
		label.setGraphicTextGap(20);

		final Button buttonYes = new Button("Yes");
		// handel yes click
		buttonYes.setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event)
			{
				answear = true;
				ExitDialogMessagebox.this.close();
			}
		});

		final Button buttonNo = new Button("No");
		// handle no click
		buttonNo.setOnAction(new EventHandler<ActionEvent>()
		{

			@Override
			public void handle(ActionEvent event)
			{
				answear = false;
				ExitDialogMessagebox.this.close();
			}
		});

		// somewhere to put all the things
		final BorderPane borderPane = new BorderPane();
		borderPane.setTop(label);

		final HBox hbox2 = new HBox();
		hbox2.setAlignment(Pos.CENTER);
		hbox2.getChildren().add(buttonYes);
		hbox2.getChildren().add(buttonNo);
		borderPane.setBottom(hbox2);

		// calculate width of string
		// final Text text = new Text("Do you want to exit?");
		// text.snapshot(null, null);
		// // + 20 because there is padding 10 left and right
		// int width = (int) text.getLayoutBounds().getWidth() + 40;

		final int width = 100;

		final int height = 70;

		// create and set the scene
		final Scene scene = new Scene(borderPane, width, height);
		scene.setFill(Color.TRANSPARENT);
		setScene(scene);
	}

	/***
	 * Displayes the exit dialogbox
	 * 
	 * @return if user want to exit (true) else false;
	 */
	public Boolean showDialog()
	{
		// start the dialogbox
		this.showAndWait();
		return this.answear;
	}
}

package com.skola.main;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BinaryConvertFXMLScene
{

	public void start(final Stage primaryStage)
	{
		primaryStage.setTitle("FXML Binary Converter");
		try
		{

			// fxml link
			final Parent root = FXMLLoader.load(getClass().getResource("/upg8.fxml"));
			// there is a controller connected to the fxmlfile aswell.
			// see the fxml file

			// add the fxml to the scene
			final Scene scene = new Scene(root);

			// add scene to stage and show stage
			primaryStage.setScene(scene);
			primaryStage.show();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}
	}
}

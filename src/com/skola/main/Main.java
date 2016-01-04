package com.skola.main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import com.skola.tools.ExitDialogMessagebox;

public class Main extends Application
{

	public static void main(String[] args)
	{
		launch(args);

	}

	@Override
	public void start(final Stage primaryStage)
	{
		// HTML5 version
		final BinaryConverterHTMLScene controllerHTML = new BinaryConverterHTMLScene();
		// FXML version
		final BinaryConvertFXMLScene controllerFXML = new BinaryConvertFXMLScene();

		primaryStage.getIcons().add(new Image(getClass().getResource("/assets/ico/binary32x32.png").toString()));

		// handle on exit click on kryss
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>()
		{

			@Override
			public void handle(WindowEvent e)
			{

				final ExitDialogMessagebox test = new ExitDialogMessagebox();
				final Boolean answear = test.showDialog();
				if (answear == false)
				{
					e.consume(); // this blocks window closing
				}

			}
		});

		// sets min size for both html and fxml
		primaryStage.setMinWidth(300);
		primaryStage.setMinHeight(400);

		// here you can test html or fxml version.
		controllerHTML.start(primaryStage);
		// controllerFXML.start(primaryStage);
	}

}

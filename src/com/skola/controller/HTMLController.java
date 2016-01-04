package com.skola.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Platform;
import javafx.scene.web.WebEngine;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import org.w3c.dom.Element;
import org.w3c.dom.html.HTMLTextAreaElement;

import com.skola.tools.BinaryConverter;
import com.skola.tools.ExitDialogMessagebox;

/***
 * Handels events fired from the html page. This is instead of using Javascript.
 * Also the passage way to connect with the app from html Wich i belive and
 * think is one of the best way to programming. GWT is a nicer, probely safer
 * and better solution.
 * 
 * Im using bootstrapp 3.0 for creating the html UI.
 * 
 * {@link "http://getbootstrap.com/"}
 * 
 * @author seb
 * 
 */
public class HTMLController
{

	private final Element outputBinary;
	private final WebEngine engine;
	private String inputText;
	private final FileChooser fileChooser;
	private final Stage primaryStage;

	// bind the output textarea to a var
	public HTMLController(WebEngine engine, Stage Stage)
	{
		primaryStage = Stage;
		fileChooser = new FileChooser();
		this.engine = engine;
		outputBinary = (Element) engine.executeScript("document.getElementById('outputBinary')");

	}

	/**
	 * Clears the the output field and removes validation errors
	 * 
	 */
	public void clear()
	{
		// clear old validation and output field
		engine.executeScript("$('#inputTextGroup').removeClass('has-error');");
		engine.executeScript("$('#inputTextGroup').find('label').addClass('hide');");

		// clear input and output fields
		outputBinary.setTextContent("");
	}

	/**
	 * Clears everything
	 * 
	 * @return
	 */
	public void newClear()
	{
		// clear the input field
		final HTMLTextAreaElement inputField = (HTMLTextAreaElement) engine
				.executeScript("document.getElementById('inputText')");

		inputField.setValue("");
		// clear everything else aswell
		clear();
	}

	/**
	 * handles menu on exit
	 */
	public void exit()
	{
		final ExitDialogMessagebox test = new ExitDialogMessagebox();
		final Boolean answear = test.showDialog();
		System.out.println("answear: " + answear);
		if (answear == true)
		{
			Platform.exit();
		}
	}

	/**
	 * Handles input from webpage. Has a direct connection to the button on the
	 * page.
	 */
	public void convert()
	{
		// clear errors and output field
		clear();

		// get the input text
		inputText = (String) engine.executeScript("document.getElementById('inputText').value");

		// alternative
		// final HTMLTextAreaElement inputField = (HTMLTextAreaElement) engine
		// .executeScript("document.getElementById('inputText')");

		// Do validation
		// display validation error
		if (inputText != null && !inputText.isEmpty())
		{
			// call converter
			final String binaryAnswear = BinaryConverter.converTextToBinary(inputText);
			// display convertion
			outputBinary.setTextContent(binaryAnswear);
		}
		else
		{

			// add error css classes
			engine.executeScript("$('#inputTextGroup').addClass('has-error');");
			engine.executeScript("$('#inputTextGroup').children().removeClass('hide');");
			outputBinary.setTextContent("");
		}

	}

	public void help()
	{
		engine.executeScript("$('#helpModal').modal('show');");
	}

	public void openFileMenu()
	{
		engine.executeScript("$('#nav-main').collapse('toggle');");
	}

	/**
	 * Opens a open file dialog.
	 */
	public void openFileDlg()
	{

		fileChooser.setTitle("Open File");
		fileChooser.getExtensionFilters().clear();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT", "*.txt"));
		final File file = fileChooser.showOpenDialog(primaryStage);

		if (file != null)
		{
			openFile(file);
		}

	}

	/**
	 * Handles opening txt file. wich files in the input field
	 * 
	 * @param file
	 *          text file containg the text for the input field.
	 */
	private void openFile(File file)
	{

		final HTMLTextAreaElement inputField = (HTMLTextAreaElement) engine
				.executeScript("document.getElementById('inputText')");
		String savedTxt = "";

		try
		{
			final FileReader thefileReader = new FileReader(file);

			final BufferedReader filin = new BufferedReader(thefileReader);

			String rad = filin.readLine(); // L�s f�rsta raden i filen

			while (rad != null)
			{ // Returnerar null n�r filen �r slut
				savedTxt += rad;
				rad = filin.readLine(); // L�ser n�sta rad i filen
			}

			filin.close(); // St�ng filen n�r vi inte ska l�sa mer

		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		inputField.setValue(savedTxt);
		inputField.setTextContent(savedTxt);
		outputBinary.setTextContent(savedTxt);
	}

	/**
	 * Opens file save dialog
	 */
	public void saveFileDlg()
	{

		fileChooser.setTitle("Save File");
		fileChooser.getExtensionFilters().clear();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("BIN", "*.bin"));
		final File file = fileChooser.showSaveDialog(primaryStage);
		if (file != null)
		{
			saveTofile(file);
		}
	}

	/**
	 * Handles file saving of the binary ouput
	 * 
	 * @param file
	 *          the file you want to save to.
	 */
	private void saveTofile(File file)
	{
		try
		{
			FileWriter fileWriter = null;

			fileWriter = new FileWriter(file);
			fileWriter.write(outputBinary.getTextContent());
			fileWriter.close();
		}
		catch (final IOException e)
		{
			e.printStackTrace();
		}

	}

	/**
	 * For debugging the webview. This will start firebug so you can easily debug
	 * your html inside javafx
	 */
	public void startFirebug()
	{
		/**
		 * @Link( http://css-tricks.com/snippets/html/use-firebug-in-any-browser/ )
		 */
		final String firebug = "var firebug=document.createElement('script');firebug.setAttribute('src','http://getfirebug.com/releases/lite/1.2/firebug-lite-compressed.js');document.body.appendChild(firebug);(function(){if(window.firebug.version){firebug.init();}else{setTimeout(arguments.callee);}})();void(firebug);";
		engine.executeScript(firebug);
		/**
		 * Used this because there was a bug with my open and save file hrefs.
		 * Listade ut att man m�ste ge href en address f�r att de ska fungera.
		 * R�ckte med att s�tta dme till href="#"
		 */

	}
}

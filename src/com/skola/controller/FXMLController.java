package com.skola.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import com.skola.tools.BinaryConverter;
import com.skola.tools.ExitDialogMessagebox;

public class FXMLController
{

	@FXML
	TextArea inputText;

	@FXML
	TextArea outputBinary;

	@FXML
	private VBox borderPane;

	private final FileChooser fileChooser;

	public FXMLController()
	{
		fileChooser = new FileChooser();

	}

	/**
	 * Clears the fields
	 */
	@FXML
	private void clearFields()
	{
		System.out.println("clearing areas");
		inputText.clear();
		outputBinary.clear();
	}

	@FXML
	public void openFileDlg()
	{

		fileChooser.setTitle("Open File");
		fileChooser.getExtensionFilters().clear();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT", "*.txt"));
		final File file = fileChooser.showOpenDialog(borderPane.getScene().getWindow());

		if (file != null)
		{
			openFile(file);
		}

	}

	private void openFile(File file)
	{

		try
		{
			final FileReader thefileReader = new FileReader(file);

			final BufferedReader filin = new BufferedReader(thefileReader);

			String rad = filin.readLine(); // Läs första raden i filen

			while (rad != null)
			{ // Returnerar null när filen är slut
				System.out.println(rad); // Skriver ut innehållet till kommandofönstret
				inputText.setText(rad);
				rad = filin.readLine(); // Läser nästa rad i filen

			}

			filin.close(); // Stäng filen när vi inte ska läsa mer

		}
		catch (final Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	public void saveFileDlg()
	{

		fileChooser.setTitle("Save File");
		fileChooser.getExtensionFilters().clear();
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("BIN", "*.bin"));
		final File file = fileChooser.showSaveDialog(borderPane.getScene().getWindow());
		if (file != null)
		{
			saveTofile(file);
		}
	}

	private void saveTofile(File file)
	{
		try
		{
			FileWriter fileWriter = null;

			fileWriter = new FileWriter(file);
			fileWriter.write(outputBinary.getText());
			fileWriter.close();
		}
		catch (final IOException ex)
		{

			System.out.println(ex.getMessage());
			// Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	/**
	 * Do the convert to binary
	 */
	@FXML
	private void convertToBinary()
	{
		System.out.println("converting");

		if (inputText != null && !inputText.getText().isEmpty())
		{
			// call converter
			final String binaryAnswear = BinaryConverter.converTextToBinary(inputText.getText());
			// display convertion
			outputBinary.setText(binaryAnswear);
		}
		else
		{
			System.out.println("validation error");
			outputBinary.setText("");
		}

	}

	/**
	 * exit from menu
	 */
	@FXML
	private void Quit()
	{

		final ExitDialogMessagebox test = new ExitDialogMessagebox();
		final Boolean answear = test.showDialog();
		System.out.println("answear: " + answear);
		if (answear == true)
		{
			Platform.exit();
		}
	}
}

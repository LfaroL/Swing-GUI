import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class Commands {

	// Check all commands in the textArea
	static Boolean checkCommands(JTextArea textArea) {
		Boolean errorFound = false; // Set to true if an error is found
		JTextPane errorTextPane = new JTextPane(); // TextPane to write error
													// messages found to

		// Split textArea string by new line
		List<String> commandsList = Arrays.asList(textArea.getText().split("\n"));

		int commandCount = 0; // Command count
		int lineCount = 1; // Line count

		// Parse first line of text file into Integer
		try {
			commandCount = Integer.parseInt(commandsList.get(0));
		} catch (NumberFormatException e) { // Create error message if fails to
											// parse into Integer
			ErrorMessages.errorMessage(errorTextPane, lineCount, "Should be a positive integer" + "\n");
			errorFound = true;
		}

		// Check if command count = commandList size
		if (commandCount != commandsList.size() - 1 && !errorFound) {
			ErrorMessages.errorMessage(errorTextPane, lineCount,
					"Should be a positive Integer specifying the number of commands" + "\n	Specified command count: "
							+ commandCount + "\n	Current command count: " + (commandsList.size() - 1) + "\n");
			errorFound = true;
		}

		// While there is still a command in the commandList
		while (lineCount <= commandsList.size() - 1) {
			// Split current command into one word strings
			List<String> currentCommand = Arrays.asList(commandsList.get(lineCount).split(" "));
			// Stores all error messages to write
			List<String> errorMessage = isValidCommand(currentCommand);

			// If error messages exists, write to the textPane
			if (!errorMessage.isEmpty()) {
				errorFound = true;
				for (int index = 0; index < errorMessage.size(); index++) {
					String s = errorMessage.get(index);
					if (index != errorMessage.size() - 1) {
						ErrorMessages.errorMessage(errorTextPane, lineCount + 1, s);
					} else {
						ErrorMessages.errorMessage(errorTextPane, lineCount + 1, s + "\n");
					}
				}
			}
			lineCount++; // ADD ONE TO lineCount
		}
		// Show list of errors in a window
		if (errorFound) {
			FrameCreation.showErrorFrame(errorTextPane);
		}

		return errorFound;
	}

	// Check if command is valid
	static List<String> isValidCommand(List<String> command) {
		// Stores error messages to return
		List<String> isValidCommand = new ArrayList<String>();
		// Contains current command name
		String commandName = command.get(0).toUpperCase();

		// Compare current command name to every valid command name

		if (commandName.equals("DAY") || commandName.equals("GOOD") || commandName.equals("SNEAK")
				|| commandName.equals("INFLATION") || commandName.equals("DEFLATION")) {
			if (command.size() != 1) {
				isValidCommand.add(commandName + " command requires no parameters");
			}
		} else if (commandName.equals("PICKUP")) {
			isValidCommand = ErrorMessages.errorMessagePickUp(command);
		} else if (commandName.equals("LETTER")) {
			isValidCommand = ErrorMessages.errorMessageLetter(command);
		} else if (commandName.equals("PACKAGE")) {
			isValidCommand = ErrorMessages.errorMessagePackage(command);
		} else if (commandName.equals("BUILD")) {
			isValidCommand = ErrorMessages.errorMessageBuild(command);
		} else if (commandName.equals("SCIENCE")) {
			isValidCommand = ErrorMessages.errorMessageScience(command);
		} else if (commandName.equals("NSADELAY")) {
			isValidCommand = ErrorMessages.errorMessageNSADelay(command);
		} else {
			isValidCommand.add("Not a valid command");
		}

		return isValidCommand;
	}

}

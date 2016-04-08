import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ErrorMessages {


	static void errorMessage(JTextPane textPane, int lineNumber, String errorMessage) {
		String errorStart = "  Error at line "; // Start of error message
		String errorLineNumber = lineNumber + ""; // Line number of error
		String errorEnd = ": " + errorMessage + "\n"; // End of error message
		append(textPane, errorStart); // Write to textPane
		appendStyle(textPane, errorLineNumber); // Write to textPane with Style
		append(textPane, errorEnd); // Write to textPane
	}

	static void append(JTextPane textPane, String s) {
		try {
			Document doc = textPane.getDocument(); // Get textPane document
			doc.insertString(doc.getLength(), s, null); // Append string to
														// textPane
		} catch (BadLocationException exc) {
			exc.printStackTrace();
		}
	}

	static void appendStyle(JTextPane textPane, String s) {
		try {
			StyledDocument doc = textPane.getStyledDocument(); // Get textPane
																// StyledDocument
			Style style = textPane.addStyle(s, null); // Add style to textPane
			StyleConstants.setForeground(style, Color.red); // Change font color
															// to RED
			doc.insertString(doc.getLength(), s, style); // Append string to
															// textPane
		} catch (BadLocationException exc) {
			exc.printStackTrace();
		}
	}

	static List<String> errorMessagePickUp(List<String> command) {
		List<String> errorMessage = new ArrayList<String>();
		String regex = "\\d+";

		if (command.size() != 3) {
			errorMessage.add("PICKUP command requires two parameters"
					+ "\n	- PICKUP stringOfficeName stringPersonAttemptingPickUp");
		} else {
			if (command.get(1).matches(regex)) {
				errorMessage.add("The 1st argument \"" + command.get(1) + "\" needs to be a String for office name");
			}
			if (command.get(2).matches(regex)) {
				errorMessage.add("The 2nd argument \"" + command.get(2)
						+ "\" needs to be a String for person attempting pick up");
			}
		}
		return errorMessage;
	}

	static List<String> errorMessageLetter(List<String> command) {
		List<String> errorMessage = new ArrayList<String>();
		String regex = "\\d+";

		if (command.size() != 5) {
			errorMessage.add("LETTER command requires four parameters"
					+ "\n	- LETTER stringSource stringRecepient stringDestination stringReturnSender");
		} else {
			if (command.get(1).matches(regex)) {
				errorMessage.add(
						"The 1st argument \"" + command.get(1) + "\" needs to be a String for letter office source");
			}
			if (command.get(2).matches(regex)) {
				errorMessage
						.add("The 2nd argument \"" + command.get(2) + "\" needs to be a String for letter recipient");
			}
			if (command.get(3).matches(regex)) {
				errorMessage.add("The 3rd argument \"" + command.get(3)
						+ "\" needs to be a String for letter office destination");
			}
			if (command.get(4).matches(regex)) {
				errorMessage.add(
						"The 4th argument \"" + command.get(4) + "\" needs to be a String for letter return sender");
			}
		}
		return errorMessage;
	}

	static List<String> errorMessagePackage(List<String> command) {
		List<String> errorMessage = new ArrayList<String>();
		String regex = "\\d+";

		if (command.size() != 6) {
			errorMessage.add("PACKAGE command requires five parameters"
					+ "\n	- PACKAGE stringSource stringRecepient stringDestination intPostageAndPersuasion intLength");
		} else {
			if (command.get(1).matches(regex)) {
				errorMessage.add(
						"The 1st argument \"" + command.get(1) + "\" needs to be a String for package office source");
			}
			if (command.get(2).matches(regex)) {
				errorMessage
						.add("The 2nd argument \"" + command.get(2) + "\" needs to be a String for package recipient");
			}
			if (command.get(3).matches(regex)) {
				errorMessage.add("The 3rd argument \"" + command.get(3)
						+ "\" needs to be a String for package office destination");
			}
			if (!(command.get(4).matches(regex)) || (Integer.parseInt(command.get(4)) <= 0)) {
				errorMessage.add("The 4th argument \"" + command.get(4)
						+ "\" needs to be a positive Integer for package postage and persuasion");
			}
			if (!(command.get(5).matches(regex)) || (Integer.parseInt(command.get(5)) <= 0)) {
				errorMessage.add("The 5th argument \"" + command.get(5)
						+ "\" needs to be a positive Integer for package length");
			}
		}
		return errorMessage;
	}

	static List<String> errorMessageBuild(List<String> command) {
		List<String> errorMessage = new ArrayList<String>();
		String regex = "\\d+";

		if (command.size() != 7) {
			errorMessage.add("BUILD command requires six parameters"
					+ "\n	- BUILD stringOfficeName intTransitTime intPostage intCapacity intPesuasion intMaxPackage");
		} else {
			if (command.get(1).matches(regex)) {
				errorMessage.add("The 1st argument \"" + command.get(1) + "\" needs to be a String for office name");
			}
			if (!(command.get(2).matches(regex)) || (Integer.parseInt(command.get(2)) <= 0)) {
				errorMessage.add("The 2nd argument \"" + command.get(2)
						+ "\" needs to be a positive Integer for office transit " + "time");
			}
			if (!(command.get(3).matches(regex)) || (Integer.parseInt(command.get(3)) <= 0)) {
				errorMessage.add("The 3rd argument \"" + command.get(3)
						+ "\" needs to be a positive Integer for office postage requirement");
			}
			if (!(command.get(4).matches(regex)) || (Integer.parseInt(command.get(4)) <= 0)) {
				errorMessage.add("The 4th argument \"" + command.get(4)
						+ "\" needs to be a positive Integer for office capacity");
			}
			if (!(command.get(5).matches(regex)) || (Integer.parseInt(command.get(5)) <= 0)) {
				errorMessage.add("The 5th argument \"" + command.get(5)
						+ "\" needs to be a positive Integer for office persuasion");
			}
			if (!(command.get(6).matches(regex)) || (Integer.parseInt(command.get(6)) <= 0)) {
				errorMessage.add("The 6th argument \"" + command.get(6)
						+ "\" needs to be a positive Integer for office max package length");
			}
		}
		return errorMessage;
	}

	static List<String> errorMessageScience(List<String> command) {
		List<String> errorMessage = new ArrayList<String>();

		if (command.size() != 2) {
			errorMessage.add("SCIENCE command requires one parameter" + "\n	- SCIENCE intDaysToTimeTravel");
		} else {
			try {
				Integer.parseInt(command.get(1));
			} catch (NumberFormatException e){
				errorMessage.add("The 1st argument \"" + command.get(1)
				+ "\" needs to be a positive Integer for days to time travel");
			}
		}
		return errorMessage;
	}

	static List<String> errorMessageNSADelay(List<String> command) {
		List<String> errorMessage = new ArrayList<String>();
		String regex = "\\d+";

		if (command.size() != 3) {
			errorMessage
					.add("NSADELAY command requires two parameter" + "\n	- NSADELAY stringPersonName intDelayDays");
		} else {
			if (command.get(1).matches(regex)) {
				errorMessage
						.add("The 1st argument \"" + command.get(1) + "\" needs to be a String for person to delay");
			}
			if (!(command.get(2).matches(regex)) || (Integer.parseInt(command.get(2)) <= 0)) {
				errorMessage.add("The 2nd argument \"" + command.get(2)
						+ "\" needs to be a positive Integer for number of delay days");
			}
		}
		return errorMessage;

	}
}

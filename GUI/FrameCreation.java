import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class FrameCreation {

	static JPanel createGridFrame(int row, int column) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(row, column));
		panel.setBackground(Color.WHITE);
		return panel;
	}

	static JLabel createJLabelFrameHeader(String labelMessage) {
		JLabel label = new JLabel(labelMessage, JLabel.CENTER);
		label.setFont(label.getFont().deriveFont(Font.BOLD, 18));
		return label;
	}

	static JLabel createJLabelBodyHeader(String labelMessage) {
		JLabel label = new JLabel(labelMessage, JLabel.LEFT);
		label.setFont(label.getFont().deriveFont(Font.BOLD, 16));
		return label;
	}

	static JLabel createJLabelBodyContent(String labelMessage) {
		JLabel label = new JLabel(labelMessage, JLabel.LEFT);
		label.setFont(label.getFont().deriveFont(Font.PLAIN, 15));
		return label;
	}

	// Creates the header label and adds it to the mainPanel
	static void setFrameHeader(JPanel mainPanel) {
		JLabel headerLabel = createJLabelFrameHeader("Welcome to the Command File Editor.");
		mainPanel.add(headerLabel);
	}

	// Creates a panel and label for the introduction section
	static void setHomePageIntroduction(JPanel mainPanel) {
		JPanel introductionPanel = createGridFrame(3, 1); // Grid layout (3
															// rows, 1 column)

		// Label contains text to display for the home page
		JLabel bodyHeader = createJLabelBodyHeader("          I. Introduction");
		JLabel bodyContent = createJLabelBodyContent("                This program creates and edits commands file"
				+ " for the A2 program using a GUI for easy interaction.");

		// Add to the main panel
		introductionPanel.add(bodyHeader);
		introductionPanel.add(bodyContent);
		mainPanel.add(introductionPanel);
	}

	// Creates a panel and label for the use section
	static void setHomePageUse(JPanel mainPanel) {
		// Two panels with 3 x 1 Grid layouts
		JPanel usePanel_1 = createGridFrame(3, 1);
		JPanel usePanel_2 = createGridFrame(3, 1);

		// Label with instructions on how to use the program
		JLabel bodyHeader = createJLabelBodyHeader("          II. Use");
		JLabel bodyContent_1 = createJLabelBodyContent("                1. Create a new file or open an existing one.");
		JLabel bodyContent_2 = createJLabelBodyContent(
				"                2. A JTextArea will open to write on the text file.");
		JLabel bodyContent_3 = createJLabelBodyContent("                3. Save the file when done writing.");
		JLabel bodyContent_4 = createJLabelBodyContent(
				"                4. Click help on the menu bar to show this home page again.");

		// Add ot the main panel
		usePanel_1.add(bodyHeader);
		usePanel_1.add(bodyContent_1);
		usePanel_1.add(bodyContent_2);
		usePanel_2.add(bodyContent_3);
		usePanel_2.add(bodyContent_4);
		mainPanel.add(usePanel_1);
		mainPanel.add(usePanel_2);
	}

	// Creates a panel and label for the commands section
	static void setHomePageCommands(JPanel mainPanel) {
		// Six panels with 3 x 1 Grid layouts
		JPanel commandsPanel_1 = createGridFrame(3, 1);
		JPanel commandsPanel_2 = createGridFrame(3, 1);
		JPanel commandsPanel_3 = createGridFrame(3, 1);
		JPanel commandsPanel_4 = createGridFrame(3, 1);
		JPanel commandsPanel_5 = createGridFrame(3, 1);
		JPanel commandsPanel_6 = createGridFrame(3, 1);

		// Labels with directions how to use the commands
		JLabel bodyHeader = createJLabelBodyHeader("          III. Commands");
		JLabel bodyContent_1 = createJLabelBodyContent("                1. DAY: Ends the current Day");
		JLabel bodyContent_2 = createJLabelBodyContent("                2. PICKUP: Pick up a package ");
		JLabel bodyContent_3 = createJLabelBodyContent(
				"                         " + "- PICKUP stringOfficeName stringPersonAttemptingPickUp");
		JLabel bodyContent_4 = createJLabelBodyContent("                3. LETTER: Send a letter");
		JLabel bodyContent_5 = createJLabelBodyContent("                         "
				+ "- LETTER stringSource stringRecepient stringDestination stringReturnSender");
		JLabel bodyContent_6 = createJLabelBodyContent("                4. PACKAGE: Send a package");
		JLabel bodyContent_7 = createJLabelBodyContent("                         "
				+ "- PACKAGE stringSource stringRecepient stringDestination intPostageAndPersuasion intLength");
		JLabel bodyContent_8 = createJLabelBodyContent("                5. BUILD: Create a new office");
		JLabel bodyContent_9 = createJLabelBodyContent("                         "
				+ "- BUILD stringOfficeName intTransitTime intPostage intCapacity intPesuasion intMaxPackage");
		JLabel bodyContent_10 = createJLabelBodyContent("                6. SCIENCE: Time travel a number of days");
		JLabel bodyContent_11 = createJLabelBodyContent("                         " + "- SCIENCE intDaysToTimeTravel");
		JLabel bodyContent_12 = createJLabelBodyContent("                7. GOOD: Skip rest of the day");
		JLabel bodyContent_13 = createJLabelBodyContent(
				"                8. NSADELAY: Delay deliverables for a recipient");
		JLabel bodyContent_14 = createJLabelBodyContent(
				"                         " + "- NSADELAY stringPersonName intDelayDays");
		JLabel bodyContent_15 = createJLabelBodyContent("                9. SNEAK: Send next item successfully");
		JLabel bodyContent_16 = createJLabelBodyContent(
				"                10. INFLATION/DEFLATION: Increase/decrease postage cost for each office");

		// Add to the main panel
		commandsPanel_1.add(bodyHeader);
		commandsPanel_1.add(bodyContent_1);
		commandsPanel_1.add(bodyContent_2);
		commandsPanel_2.add(bodyContent_3);
		commandsPanel_2.add(bodyContent_4);
		commandsPanel_2.add(bodyContent_5);
		commandsPanel_3.add(bodyContent_6);
		commandsPanel_3.add(bodyContent_7);
		commandsPanel_3.add(bodyContent_8);
		commandsPanel_4.add(bodyContent_9);
		commandsPanel_4.add(bodyContent_10);
		commandsPanel_4.add(bodyContent_11);
		commandsPanel_5.add(bodyContent_12);
		commandsPanel_5.add(bodyContent_13);
		commandsPanel_5.add(bodyContent_14);
		commandsPanel_6.add(bodyContent_15);
		commandsPanel_6.add(bodyContent_16);
		mainPanel.add(commandsPanel_1);
		mainPanel.add(commandsPanel_2);
		mainPanel.add(commandsPanel_3);
		mainPanel.add(commandsPanel_4);
		mainPanel.add(commandsPanel_5);
		mainPanel.add(commandsPanel_6);
	}
	
	static void showHelpFrame() {
		JFrame mainFrame = new JFrame("Help Window");
		mainFrame.setSize(800, 600);
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setLocationRelativeTo(null);
		mainFrame.getContentPane().setBackground(Color.WHITE);
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				mainFrame.dispose();
			}
		});

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(10, 1));
		mainPanel.setBackground(Color.WHITE);

		mainFrame.add(mainPanel, BorderLayout.CENTER);
		setFrameHeader(mainPanel);
		setHomePageIntroduction(mainPanel);
		setHomePageUse(mainPanel);
		setHomePageCommands(mainPanel);

		mainFrame.setVisible(true);
	}

	static void showCommandExamplesFrame() {
		JFrame mainFrame = new JFrame("Commands Example Window");
		mainFrame.setSize(800, 600);
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setLocationRelativeTo(null);
		mainFrame.getContentPane().setBackground(Color.WHITE);
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				mainFrame.dispose();
			}
		});

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(10, 1));
		mainPanel.setBackground(Color.WHITE);

		mainFrame.add(mainPanel, BorderLayout.CENTER);

		JTextPane textPane = new JTextPane();
		textPane.setFont(textPane.getFont().deriveFont(Font.PLAIN, 16));
		StyledDocument doc = textPane.getStyledDocument();
		
		
		List<String> commandExamples = commandExamples();
		for (int index = 0 ; index <= commandExamples.size() - 1 ; index = index + 1) {
			
			Style style = textPane.addStyle(commandExamples.get(index), null);
			StyleConstants.setBold(style, true);
			if (index % 3 == 0) {
				try {
					doc.insertString(doc.getLength(), commandExamples.get(index), style);
				} catch (BadLocationException e) {
					e.printStackTrace();
				}

			}else if (index % 3 != 0){
				try {
					doc.insertString(doc.getLength(), commandExamples.get(index), null);
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		textPane.setEditable(false);

		mainFrame.add(textPane);
		mainFrame.setVisible(true);
	}

	static List<String> commandExamples() {
		List<String> commandExamples = new ArrayList<String>();
		
		commandExamples.add("   -PickUp Command Examples: \n ");
		commandExamples.add("\tPICKUP Burnaby Jim\n");
		commandExamples.add("\tPICKUP Vancouver Apple\n");

		commandExamples.add("\n   -Package Command Examples: \n ");
		commandExamples.add("\tPACKAGE Vancouver Simon Burnaby 500 3500\n");
		commandExamples.add("\tPACKAGE Burnaby Simon Vancouver 1000 500\n");
		
		commandExamples.add("\n   -Letter Command Examples: \n ");
		commandExamples.add("\tLETTER Vancouver Bill Burnaby Apple\n");
		commandExamples.add("\tLETTER Burnaby Bill Vancouver Tom\n");
		
		commandExamples.add("\n   -Build Command Examples: \n ");
		commandExamples.add("\tBUILD Paris 1 10 1000 10000 5000\n");
		commandExamples.add("\tBUILD Mars 1 10 1000 10000 4000\n");
		
		commandExamples.add("\n   -Science Command Examples: \n ");
		commandExamples.add("\tSCIENCE 10\n");
		commandExamples.add("\tSCIENCE -5\n");
		
		commandExamples.add("\n   -NSADelay Command Examples: \n ");
		commandExamples.add("\tNSADELAY Bill 1\n");
		commandExamples.add("\tNSADELAY Simon 2\n");	
		return commandExamples;
	}
	
	static void showFindFrame() {
		JFrame mainFrame = new JFrame("Find");
		mainFrame.setSize(300, 100);
		mainFrame.setLayout(new GridLayout(2, 1));
		mainFrame.setLocationRelativeTo(null);
		mainFrame.getContentPane().setBackground(Color.WHITE);
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				mainFrame.dispose();
			}
		});

		JButton findButton = new JButton("Find");
		JTextField textField = new JTextField();

		mainFrame.add(textField);
		mainFrame.add(findButton);
		mainFrame.setVisible(true);
	}

	static void showErrorFrame(JTextPane textPane) {
		JFrame mainFrame = new JFrame("Errors found");
		mainFrame.setSize(650, 600);
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setLocationRelativeTo(null);
		mainFrame.getContentPane().setBackground(Color.WHITE);
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				mainFrame.dispose();
			}
		});

		textPane.setEditable(false);
		mainFrame.add(textPane, BorderLayout.CENTER);

		mainFrame.setVisible(true);
	}

	static int showSaveAnywayFrame() {
		int dialogResult = JOptionPane.showConfirmDialog(null, "Errors found, save file anyway?", "Warning",
				JOptionPane.YES_NO_OPTION);
		return dialogResult;
	}

	static int showConfirmExitFrame() {
		int dialogResult = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit Program",
				JOptionPane.YES_NO_OPTION);
		return dialogResult;
	}
	

}

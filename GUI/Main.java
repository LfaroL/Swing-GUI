import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class Main {
	protected JFrame mainFrame; // JFrame variable to act as main window
	protected File previouslySaved = null; // File variable to store previously
											// saved file
	protected Boolean savedFile = false; // Boolean variable to store whether a
											// previously saved file exists
	protected Boolean editorMode = false; // Boolean variable to store whether
											// user can type to the GUI

	// Default constructor calls method to create the JFrame
	public Main() {
		createJFRAME();
	}

	public static void main(String[] args) {
		Main GUI = new Main();
		GUI.showJFRAME();
	}

	// Sets JFrame to visible
	private void showJFRAME() {
		mainFrame.setVisible(true);
	}

	// Creates the JFrame that acts as main window for the GUI
	private void createJFRAME() {
		// Set JFrame size, layout, location and background color
		mainFrame = new JFrame("Command File Editor");
		mainFrame.setSize(800, 800);
		mainFrame.setLayout(new BorderLayout());
		mainFrame.setLocationRelativeTo(null);
		mainFrame.getContentPane().setBackground(Color.WHITE);
		mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// Exit program when closed button is clicked
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent windowEvent) {
				int dialogResult = FrameCreation.showConfirmExitFrame();
				if (dialogResult == JOptionPane.YES_OPTION) {
					mainFrame.dispose();
				}
			}
		});

		// Create JMenuBar to store the JMenus
		JMenuBar menuBar = new JMenuBar();
		mainFrame.add(menuBar, BorderLayout.PAGE_START);

		// Create JPanel for the center part of the mainFrame
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(10, 1)); // Grid layout (10 rows, 1
													// column)
		mainPanel.setBackground(Color.WHITE); // Background color white
		mainPanel.setPreferredSize(mainFrame.getSize());// Preferred size =
														// mainFrame size

		// Add mainPanel to mainFrame
		mainFrame.add(mainPanel, BorderLayout.CENTER);

		// Set the home page for the GUI
		FrameCreation.setFrameHeader(mainPanel);
		FrameCreation.setHomePageIntroduction(mainPanel);
		FrameCreation.setHomePageUse(mainPanel);
		FrameCreation.setHomePageCommands(mainPanel);

		// Initialize three JMenus
		JMenu edit = Menus.createEditMenu();
		JMenu file = createFileMenu(menuBar, mainPanel, edit);
		JMenu help = Menus.createHelpMenu();

		// Add to the menubar
		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(help);
	}

	// Creates the file menu
	private JMenu createFileMenu(JMenuBar menuBar, JPanel mainPanel, JMenu edit) {
		// Create file menu and set 'F' as shortcut key
		JMenu file = new JMenu("File");
		file.setFont(file.getFont().deriveFont(Font.BOLD, 14));
		file.setMnemonic(KeyEvent.VK_F);

		// Create the 'New' menu item and set 'N' as shortcut key
		JMenuItem fileMenuNew = new JMenuItem("New");
		fileMenuNew.setMnemonic(KeyEvent.VK_N);

		// Create the 'Open' menu item and set 'O' as shortcut key
		JMenuItem fileMenuOpen = new JMenuItem("Open File");
		fileMenuOpen.setMnemonic(KeyEvent.VK_O);

		// Create the 'Save' menu item and set 'S' as shortcut key and Ctrl+S as
		// keystroke shortcut
		JMenuItem fileMenuSave = new JMenuItem("Save");
		fileMenuSave.setEnabled(false);
		fileMenuSave.setMnemonic(KeyEvent.VK_S);
		fileMenuSave.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		// Create the 'Save as' menu item and set 'A' as shortcut key
		JMenuItem fileMenuSaveAs = new JMenuItem("Save As...");
		fileMenuSaveAs.setEnabled(false);
		fileMenuSaveAs.setMnemonic(KeyEvent.VK_A);

		// Create the 'Exit' menu item and set 'X' as shortcut key
		JMenuItem fileMenuExit = new JMenuItem("Exit");
		fileMenuExit.setMnemonic(KeyEvent.VK_X);

		JTabbedPaneCloseButton tabbedPane = new JTabbedPaneCloseButton();
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		JPopupMenu tabbedPanePopup = new JPopupMenu();
		JMenuItem tabbedPaneNew = new JMenuItem("New");
		tabbedPaneNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// Create JTextArea with an UndoManager for undo and redo
				// functions
				JTextArea newTextArea = Menus.textAreaWithUndoAndRedo(edit, null);
				JScrollPane newTextAreaScroll = Menus.createTextAreaWithLineNumbering(newTextArea);
				tabbedPane.addTab("Untitled", newTextAreaScroll);
			}
		});
		JMenuItem tabbedPaneClose = new JMenuItem("Close");
		tabbedPaneClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				int i = tabbedPane.getSelectedIndex();
				if (i != -1) {
					tabbedPane.remove(i);
				}
			}
		});

		tabbedPanePopup.add(tabbedPaneNew);
		tabbedPanePopup.add(tabbedPaneClose);

		tabbedPane.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				maybeShowPopup(e);
			}

			public void mouseReleased(MouseEvent e) {
				maybeShowPopup(e);
			}

			private void maybeShowPopup(MouseEvent e) {
				if (e.isPopupTrigger()) {
					tabbedPanePopup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

		// Action listener for 'New'
		fileMenuNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (!editorMode) {
					editorMode = true;
					// Enable Edit menu
					menuBar.getMenu(1).setEnabled(true);
					// Erase textArea

					JTextArea textArea = Menus.textAreaWithUndoAndRedo(edit, null);
					JScrollPane textAreaScroll = Menus.createTextAreaWithLineNumbering(textArea);

					// Clears the main panel
					mainPanel.removeAll();
					mainPanel.setLayout(new GridLayout(1, 1));
					mainPanel.setPreferredSize(textArea.getSize());

					// Enable the Save and SaveAs options
					fileMenuSave.setEnabled(true);
					fileMenuSaveAs.setEnabled(true);
					savedFile = false;

					// Update panel then add the textArea
					tabbedPane.add("Untitled", textAreaScroll);
					mainPanel.add(tabbedPane);
					mainPanel.updateUI();
				} else {
					// Create JTextArea with an UndoManager for undo and redo
					// functions
					JTextArea newTextArea = Menus.textAreaWithUndoAndRedo(edit, null);
					JScrollPane newTextAreaScroll = Menus.createTextAreaWithLineNumbering(newTextArea);
					tabbedPane.addTab("Untitled", newTextAreaScroll);
					mainPanel.updateUI();
				}
			}
		});

		// Action listener for 'Open'
		fileMenuOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if (!editorMode) {
					editorMode = true;
					// Enable Edit menu
					menuBar.getMenu(1).setEnabled(true);

					// Clears the main panel
					mainPanel.removeAll();
					mainPanel.setLayout(new GridLayout(1, 1));

					// Enable the Save and SaveAs options
					fileMenuSave.setEnabled(true);
					fileMenuSaveAs.setEnabled(true);
					savedFile = false;

					// User selects text file and grabs the text file's contents
					List<String> titleAndContent = openFile();
					String fileTitle = titleAndContent.get(0);
					String fileContent = titleAndContent.get(1);
					JTextArea textArea = Menus.textAreaWithUndoAndRedo(edit, fileContent);
					JScrollPane textAreaScroll = Menus.createTextAreaWithLineNumbering(textArea);

					// Update panel then add the textArea
					tabbedPane.add(fileTitle, textAreaScroll);
					mainPanel.setPreferredSize(textArea.getSize());
					mainPanel.add(tabbedPane);
					mainPanel.updateUI();
				} else {
					// User selects text file and grabs the text file's contents
					List<String> titleAndContent = openFile();
					if (!titleAndContent.get(0).isEmpty()) {
						String fileTitle = titleAndContent.get(0);
						String fileContent = titleAndContent.get(1);
						JTextArea textArea = Menus.textAreaWithUndoAndRedo(edit, fileContent);
						JScrollPane textAreaScroll = Menus.createTextAreaWithLineNumbering(textArea);

						// Update panel then add the textArea
						tabbedPane.add(fileTitle, textAreaScroll);
						mainPanel.updateUI();
					}
				}
			}
		});

		// Action listener for 'Save'
		fileMenuSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// Get JScrollPane from active tab, then get JTextArea from
				// JScrollPane
				JViewport viewport = ((JScrollPane) tabbedPane.getSelectedComponent()).getViewport();
				JTextArea textArea = (JTextArea) viewport.getView();

				// Check if any errors in the text area
				Boolean errorFound = Commands.checkCommands(textArea);
				// If no errors found, save file
				if (!errorFound) {
					if (savedFile) {
						String newTitle = saveFile(previouslySaved, textArea);
						tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), newTitle);
					} else {
						String newTitle = saveAsFile(textArea);
						tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), newTitle);

					}
				} else { // Else, show errors and ask user whether to still save
							// the file or not
					int dialogResult = FrameCreation.showSaveAnywayFrame();
					if (dialogResult == JOptionPane.YES_OPTION) {
						if (savedFile) {
							String newTitle = saveFile(previouslySaved, textArea);
							tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), newTitle);
						} else {
							String newTitle = saveAsFile(textArea);
							tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), newTitle);

						}
					}
				}
			}
		});

		// Action listener for 'Save As'
		fileMenuSaveAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JViewport viewport = ((JScrollPane) tabbedPane.getSelectedComponent()).getViewport();
				JTextArea textArea = (JTextArea) viewport.getView();

				// Check if any errors in the text area
				Boolean errorFound = Commands.checkCommands(textArea);

				// If no errors found, save file
				if (!errorFound) {
					String newTitle = saveAsFile(textArea);
					tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), newTitle);

				} else { // Else, show errors and ask user whether to still save
							// the file or not
					int dialogResult = FrameCreation.showSaveAnywayFrame();
					if (dialogResult == JOptionPane.YES_OPTION) {
						String newTitle = saveAsFile(textArea);
						tabbedPane.setTitleAt(tabbedPane.getSelectedIndex(), newTitle);
					}
				}
			}
		});

		// Action listener for 'Exit'
		fileMenuExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// Ask user to confirm before exiting the program
				int dialogResult = FrameCreation.showConfirmExitFrame();
				// If user chooses Yes, exit
				if (dialogResult == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});

		// Add the menu items to the menu
		file.add(fileMenuNew);
		file.add(fileMenuOpen);
		file.addSeparator();
		file.add(fileMenuSave);
		file.add(fileMenuSaveAs);
		file.addSeparator();
		file.add(fileMenuExit);

		return file;
	}

	private File chooseFile() {
		// Open dialog box and ask user to select a file
		JFileChooser fileDialog = new JFileChooser();
		fileDialog.setDialogTitle("Specify name of file to save");
		File chosenFile = null;
		int selectedFile = fileDialog.showSaveDialog(fileDialog);

		// If user chooses a file, return the file chosen
		if (selectedFile == JFileChooser.APPROVE_OPTION) {
			return chosenFile = fileDialog.getSelectedFile();
		}
		return chosenFile;
	}

	private String saveAsFile(JTextArea textArea) {
		// Call saveFile() method if user chooses a file
		File fileToSave = chooseFile();
		String newTitle = "";
		if (fileToSave != null) {
			saveFile(fileToSave, textArea);
			newTitle = saveFile(fileToSave, textArea);
		}
		return newTitle;
	}

	private List<String> openFile() {
		String fileContent = "";
		String fileName = "";
		// Get file content if user selected a file
		File fileToOpen = chooseFile();
		if (fileToOpen != null) {
			fileContent = getFileContent(fileToOpen);
			fileName = fileToOpen.getName();
		}
		List<String> list = new ArrayList<String>();
		list.add(fileName);
		list.add(fileContent);
		return list;
	}

	private String saveFile(File fileName, JTextArea textArea) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName)); // BufferedWriter
																					// to
																					// write
																					// file
			writer.write(textArea.getText()); // Get string in the textArea and
												// write to the file
			previouslySaved = fileName;
			writer.close(); // Close writer
			savedFile = true; // Set savedFile Boolean to true
		} catch (IOException err) {
			err.printStackTrace();
		}

		return fileName.getName();
	}

	private String getFileContent(File fileName) {
		JTextArea textArea = new JTextArea();
		try {
			FileReader fileReader = new FileReader(fileName); // FileReader to
																// read file
			BufferedReader buffReader = new BufferedReader(fileReader); // BufferedReader
			textArea.read(buffReader, null); // Get string from the text file
												// and write into the textArea
			buffReader.close(); // Close reader
			textArea.requestFocus(); // Focus on textArea
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return textArea.getText();
	}
}
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class Menus {
	
	// Creates the 'edit' menu
	static JMenu createEditMenu() {
		// Create edit menu and set 'E' as shortcut key
		JMenu edit = new JMenu("Edit");
		edit.setFont(edit.getFont().deriveFont(Font.BOLD, 14));
		edit.setMnemonic(KeyEvent.VK_E);

		List<JMenuItem> editItems = Menus.createEditJMenuItems();

		// Add the menu items to the menu
		edit.add(editItems.get(0));
		edit.add(editItems.get(1));
		edit.addSeparator();
		edit.add(editItems.get(2));
		edit.add(editItems.get(3));
		edit.add(editItems.get(4));
		// edit.addSeparator();
		// edit.add(editItems.get(5)); // Find not implemented
		edit.setEnabled(false);
		return edit;
	}

	static JMenu createHelpMenu() {
		// Create help menu and set 'H' as shortcut key
		JMenu help = new JMenu("Help");
		help.setFont(help.getFont().deriveFont(Font.BOLD, 14));
		help.setMnemonic(KeyEvent.VK_H);

		// Creates the 'Command List' menu item
		JMenuItem commandList = new JMenuItem("Home Page");

		// Creates the 'Command Examples' menu item
		JMenuItem commandExamples = new JMenuItem("Command Examples");

		// Add action listener for 'commandList'
		commandList.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// Show home page
				FrameCreation.showHelpFrame();
			}
		});

		// Add action listener for 'commandExamples'
		commandExamples.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// Show examples page
				FrameCreation.showCommandExamplesFrame();
			}
		});

		// Add the menu items to the menu
		help.add(commandList);
		help.add(commandExamples);
		return help;
	}

	// Create JTextArea with line numbering and scroll bar
	static JScrollPane createTextAreaWithLineNumbering(JTextArea textArea) {
		// Place textArea in a JScrollPane
		JScrollPane textAreaScroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		// Place line numbering on the side with the TextLineNumber class
		TextLineNumber tln = new TextLineNumber(textArea);
		textAreaScroll.setRowHeaderView(tln);
		return textAreaScroll;
	}

	// Imported from
	// http://stackoverflow.com/questions/12030836/undo-functionality-in-jtextarea
	// Start
	static JTextArea textAreaWithUndoAndRedo(JMenu edit, String s) {
		JTextArea textArea = new JTextArea();
		textArea.setText(s);
		textArea.setFont(textArea.getFont().deriveFont(Font.PLAIN, 16));

		UndoManager undoManager = new UndoManager();

		Document doc = textArea.getDocument();

		doc.addUndoableEditListener(new UndoableEditListener() {
			public void undoableEditHappened(UndoableEditEvent e) {
				undoManager.addEdit(e.getEdit());
			}
		});

		InputMap im = textArea.getInputMap(JComponent.WHEN_FOCUSED);
		ActionMap am = textArea.getActionMap();

		im.put(null, "Undo");
		im.put(null, "Redo");

		am.put("Undo", new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				try {
					if (undoManager.canUndo()) {
						undoManager.undo();
					}
				} catch (CannotUndoException exp) {
					exp.printStackTrace();
				}
			}
		});
		am.put("Redo", new AbstractAction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				try {
					if (undoManager.canRedo()) {
						undoManager.redo();
					}
				} catch (CannotUndoException exp) {
					exp.printStackTrace();
				}
			}
		});

		// Imported from
		// http://stackoverflow.com/questions/12030836/undo-functionality-in-jtextarea
		// End

		// Edit Menu's Undo Button
		edit.getItem(0).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					if (undoManager.canUndo()) {
						undoManager.undo();
					}
				} catch (CannotUndoException exp) {
					exp.printStackTrace();
				}
			}
		});

		// Edit Menu's Redo Button
		edit.getItem(1).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					if (undoManager.canRedo()) {
						undoManager.redo();
					}
				} catch (CannotUndoException exp) {
					exp.printStackTrace();
				}
			}
		});

		JMenu editCopy = createEditMenu();
		// Edit Menu's Undo Button
		editCopy.getItem(0).addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				try {
					if (undoManager.canUndo()) {
						undoManager.undo();
					}
				} catch (CannotUndoException exp) {
					exp.printStackTrace();
				}
			}
		});

		JPopupMenu editPopUp = createEditPopupMenu(editCopy);

		textArea.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				maybeShowPopup(e);
			}

			public void mouseReleased(MouseEvent e) {
				maybeShowPopup(e);
			}

			private void maybeShowPopup(MouseEvent e) {
				if (e.isPopupTrigger()) {
					editPopUp.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

		return textArea;
	}

	
	// Creates the 'edit' pop up menu
	static JPopupMenu createEditPopupMenu(JMenu edit) {
		JPopupMenu popUp = new JPopupMenu();
		popUp.add(edit.getItem(0));
		popUp.addSeparator();
		popUp.add(edit.getItem(2));
		popUp.add(edit.getItem(2));
		popUp.add(edit.getItem(2));

		return popUp;
	}

	static List<JMenuItem> createEditJMenuItems() {
		List<JMenuItem> menuItems = new ArrayList<JMenuItem>();

		// Creates the 'Undo' menu item and set CTRL+Z as keystroke shortcut
		JMenuItem editMenuUndo = new JMenuItem("Undo");
		editMenuUndo.setAccelerator(KeyStroke.getKeyStroke('Z', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		// Creates the 'Redo' menu item and set CTRL+Y as keystroke shortcut
		JMenuItem editMenuRedo = new JMenuItem("Redo");
		editMenuRedo.setAccelerator(KeyStroke.getKeyStroke('Y', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		// Creates the 'Cut' menu item and set CTRL+X as keystroke shortcut
		JMenuItem editMenuCut = new JMenuItem(new DefaultEditorKit.CutAction()); // Using
																					// DefaultEditorKit
		editMenuCut.setText("Cut");
		editMenuCut.setAccelerator(KeyStroke.getKeyStroke('X', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		// Creates the 'Copy' menu item and set CTRL+C as keystroke shortcut
		JMenuItem editMenuCopy = new JMenuItem(new DefaultEditorKit.CopyAction());
		editMenuCopy.setText("Copy");
		editMenuCopy.setAccelerator(KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		// Creates the 'Paste' menu item and set CTRL+V as keystroke shortcut
		JMenuItem editMenuPaste = new JMenuItem(new DefaultEditorKit.PasteAction());
		editMenuPaste.setText("Paste");
		editMenuPaste.setAccelerator(KeyStroke.getKeyStroke('V', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		// Creates the 'Find' menu item and set CTRL+F as keystroke shortcut
		JMenuItem editMenuFind = new JMenuItem("Find");
		editMenuFind.setAccelerator(KeyStroke.getKeyStroke('F', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		// Add action listener for 'Find'
		editMenuFind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				// Show find window to search for a specific string
				FrameCreation.showFindFrame();
			}
		});

		menuItems.add(editMenuUndo);
		menuItems.add(editMenuRedo);
		menuItems.add(editMenuCut);
		menuItems.add(editMenuCopy);
		menuItems.add(editMenuPaste);
		menuItems.add(editMenuFind);
		return menuItems;
	}
}

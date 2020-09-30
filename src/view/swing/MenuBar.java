package view.swing;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import model.BoxList;

/**
 * Bar de menu
 * @author Julien Cochet
 */
public class MenuBar extends JMenuBar {

	private static final long serialVersionUID = -310993895391393553L;

	public MenuBar(BoxList selectionBoxes) {
		JMenu menuFile = new JMenu("File");
		menuFile.setMnemonic('F');
		
		JMenuItem menuItemLoad = new JMenuItem("Load");
		menuItemLoad.setMnemonic('L');
		menuItemLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK));
		menuItemLoad.addActionListener((e) -> selectionBoxes.load("export.txt"));

		JMenuItem menuItemSave = new JMenuItem("Save");
		menuItemSave.setMnemonic('S');
		menuItemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK));
		menuItemSave.addActionListener((e) -> selectionBoxes.save());

		menuFile.add(menuItemLoad);
		menuFile.addSeparator();
		menuFile.add(menuItemSave);
		
		JMenu menuEdit = new JMenu("Edit");
		menuEdit.setMnemonic('E');
		
		JMenuItem menuItemClear = new JMenuItem("Clear");
		menuItemClear.setMnemonic('C');
		menuItemClear.addActionListener((e) -> selectionBoxes.clearList());
		
		menuEdit.add(menuItemClear);

		this.add(menuFile);
		this.add(menuEdit);
	}
}

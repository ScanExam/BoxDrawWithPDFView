package view.swing;

import javax.swing.JFrame;
import javax.swing.UIManager;

import model.BoxList;

/**
 * Créé une fenêtre
 * @author Julien Cochet
 */
public class DefaultWindow {

	private JFrame frame;
	private BoxList selectionBoxes;
	
	public DefaultWindow() {
		// Changement du style de l'UI (purement esthétique)
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}

		frame = new JFrame("App");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(420, 594);
		frame.setLocationRelativeTo(null);

		// Objet contenant les boîtes de sélection
		selectionBoxes = new BoxList();
		frame.setJMenuBar(new MenuBar(selectionBoxes));
		// Ajout de l'objet permettant la création de boîtes de sélection
		frame.add(new SelectionPanel(selectionBoxes));
		
		frame.setVisible(true);
	}
}

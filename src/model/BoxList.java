package model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

/**
 * Regroupement de plusieurs boîtes
 * @author Julien Cochet
 */
public class BoxList {

	//----------------------------------------------------------------------------------------------------
	/**
	 * ATTRIBUTS
	 */
	//----------------------------------------------------------------------------------------------------
	
	// Liste des boîtes
	private List<Box> list;
	// Largueur minimum des boîtes (ne peux pas être négatif)
	private int minWidth;
	// Hauteur minimum des boîtes (ne peux pas être négatif)
	private int minHeight;
	// Largueur maximum des boîtes (-1 = pas de maximum)
	private int maxWidth;
	// Hauteur maximum des boîtes (-1 = pas de maximum)
	private int maxHeight;
	// Panel où sont affichées les boîtes (ne sert que si l'objet est affiché dans une interface Swing)
	private JPanel panel;
	

	//----------------------------------------------------------------------------------------------------
	/**
	 * CONSTRUCTEURS
	 */
	//----------------------------------------------------------------------------------------------------
	
	/**
	 * Constructeur par défaut
	 */
	public BoxList() {
		this.setList(new ArrayList<>());
		this.setMinWidth(-1);
		this.setMinHeight(-1);
		this.setMaxWidth(-1);
		this.setMaxHeight(-1);
	}

	/**
	 * Constructeur paramétré avec création d'une nouvelle liste de boîtes
	 * @param minWidth Largueur minimum des boîtes
	 * @param minHeight Hauteur minimum des boîtes
	 * @param maxWidth Largueur maximum des boîtes
 	 * @param maxHeight Hauteur maximum des boîtes
	 */
	public BoxList(int minWidth, int minHeight, int maxWidth, int maxHeight) {
		this.setList(new ArrayList<>());
		this.setMinWidth(minWidth);
		this.setMinHeight(minHeight);
		this.setMaxWidth(maxWidth);
		this.setMaxHeight(maxHeight);
	}

	/**
	 * Constructeur paramétré
	 * @param list Boîtes à importer
	 * @param minWidth Largueur minimum des boîtes
	 * @param minHeight Hauteur minimum des boîtes
	 * @param maxWidth Largueur maximum des boîtes
 	 * @param maxHeight Hauteur maximum des boîtes
	 */
	public BoxList(List<Box> list, int minWidth, int minHeight, int maxWidth, int maxHeight) {
		this.setList(list);
		this.setMinWidth(minWidth);
		this.setMinHeight(minHeight);
		this.setMaxWidth(maxWidth);
		this.setMaxHeight(maxHeight);
	}
	 
	
	//----------------------------------------------------------------------------------------------------
	/**
	 * METHODES
	 */
	//----------------------------------------------------------------------------------------------------

    public void addBox(int x, int y) {
        list.add(new Box(x, y, minWidth, minHeight, "", minWidth, minHeight, maxWidth, maxHeight));
    }
    
    public void addBox(int x, int y, String title) {
        list.add(new Box(x, y, minWidth, minHeight, title, minWidth, minHeight, maxWidth, maxHeight));
    }
    
    public void addBox(int x, int y, int width, int height, String title) {
        list.add(new Box(x, y, width, height, title, minWidth, minHeight, maxWidth, maxHeight));
    }
    
    public void updateBox(int index, int x, int y, int width, int height) {
    	getBox(index).updateCoordinates(x, y);
    	getBox(index).updateSize(width, height);
    }
    
    public void updateBox(Box box, int x, int y, int width, int height) {
    	box.updateCoordinates(x, y);
    	box.updateSize(width, height);
    }
    
	public void updateBounds(int minWidth, int minHeight, int maxWidth, int maxHeight) {
		setMinWidth(minWidth);
		setMinHeight(minHeight);
		setMaxWidth(maxWidth);
		setMaxHeight(maxHeight);
	}
	
	public void removeBox(int index) {
		list.remove(index);
	}
	
	public void removeBox(Box box) {
		list.remove(box);
	}
	
	public void clearList() {
		list.clear();
		refreshPanel();
	}
	
	public void save() {
		Path file = Paths.get("export.txt");
		try {
			Files.write(file, toStringList(), StandardCharsets.UTF_8);
			System.out.println("Save success");
		} catch (IOException e) {
			System.out.println("Save fail");
			e.printStackTrace();
		}
	}
	
	public void load(String path) {
		Path file = Paths.get(path);
		try {
			clearList();
			List<String> boxes = Files.readAllLines(file);
			for(int i = 0; i < boxes.size(); i++) {
				String currentBox = boxes.get(i);
				String[] currentBoxData = currentBox.split(",");
				int x = Integer.parseInt(currentBoxData[1]);
				int y = Integer.parseInt(currentBoxData[2]);
				int width = Integer.parseInt(currentBoxData[3]);
				int height = Integer.parseInt(currentBoxData[4]);
				String title = currentBoxData[0];
				addBox(x, y, width, height, title);
			}
			refreshPanel();
			System.out.println("Load success");
		} catch (IOException e) {
			System.out.println("Load fail");
			e.printStackTrace();
		}
	}

	public List<String> toStringList() {
		List<String> stringList = new LinkedList<String>();
		for(int i = 0; i < size(); i++) {
			stringList.add(getBox(i).toString());
		}
		return stringList;
	}
	
	private void refreshPanel() {
		if(panel != null) {
			panel.repaint();
		}
	}

    
	//----------------------------------------------------------------------------------------------------
	/**
	 * GETTERS
	 */
	//----------------------------------------------------------------------------------------------------

	public List<Box> getList() {
		return list;
	}
	
	public Box getBox(int index) {
		return list.get(index);
	}
	
	public int size() {
		return list.size();
	}
	
	public int getMinWidth() {
		return minWidth;
	}

	public int getMinHeight() {
		return minHeight;
	}
	
	public int getMaxWidth() {
		return maxWidth;
	}
	
	public int getMaxHeight() {
		return maxHeight;
	}
	
	
	//----------------------------------------------------------------------------------------------------
	/**
	 * SETTERS
	 */
	//----------------------------------------------------------------------------------------------------
	
	public void setList(List<Box> list) {
		this.list = list;
	}
	
	public void setMinWidth(int minWidth) {
		if(minWidth < 0) {
			this.minWidth = 0;
		} else {
			this.minWidth = minWidth;
		}
		for(int i = 0; i < size(); i++) {
			getBox(i).setMinWidth(this.minWidth);
		}
	}

	public void setMinHeight(int minHeight) {
		if(minHeight < 0) {
			this.minHeight = 0;
		} else {
			this.minHeight = minHeight;
		}
		for(int i = 0; i < size(); i++) {
			getBox(i).setMinHeight(this.minHeight);
		}
	}
	
	public void setMaxWidth(int maxWidth) {
		if(maxWidth < 0) {
			this.maxWidth = -1;
		} else {
			this.maxWidth = maxWidth;
		}
		for(int i = 0; i < size(); i++) {
			getBox(i).setMaxWidth(this.maxWidth);
		}
	}
	
	public void setMaxHeight(int maxHeight) {
		if(maxHeight < 0) {
			this.maxHeight = -1;
		} else {
			this.maxHeight = maxHeight;
		}
		for(int i = 0; i < size(); i++) {
			getBox(i).setMaxHeight(this.maxHeight);
		}
	}
	
	public void setPanel(JPanel panel) {
		this.panel = panel;
	}

}

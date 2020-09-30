package model;

import java.awt.Rectangle;

/**
 * Donnée d'une boîte en deux dimensions
 * @author Julien Cochet
 */
public class Box {

	//----------------------------------------------------------------------------------------------------
	/**
	 * ATTRIBUTS
	 */
	//----------------------------------------------------------------------------------------------------
	
	// Coordonnée X de la boîte
	private int x;
	// Coordonnée Y de la boîte
	private int y;
	// Largueur de la boîte
	private int width;
	// Hauteur de la boîte
	private int height;
	// Titre de la boîte
	private String title;
	// Largueur minimum de la boîte (ne peux pas être négatif)
	private int minWidth;
	// Hauteur minimum de la boîte (ne peux pas être négatif)
	private int minHeight;
	// Largueur maximum de la boîte (-1 = pas de maximum)
	private int maxWidth;
	// Hauteur maximum de la boîte (-1 = pas de maximum)
	private int maxHeight;


	//----------------------------------------------------------------------------------------------------
	/**
	 * CONSTRUCTEURS
	 */
	//----------------------------------------------------------------------------------------------------
	
	/**
	 * Constructeur par défaut
	 */
	public Box() {
		this.setMinWidth(-1);
		this.setMinHeight(-1);
		this.setMaxWidth(-1);
		this.setMaxHeight(-1);
		this.setX(0);
		this.setY(0);
		this.setWidth(0);
		this.setHeight(0);
		this.setTitle("");
	}

	/**
	 * Constructeur paramétré sans bornes
	 * @param x Coordonnée X de la boîte
	 * @param y Coordonnée Y de la boîte
	 * @param width Largueur de la boîte
	 * @param height Hauteur de la boîte
	 * @param title Titre de la boîte
	 */
	public Box(int x, int y, int width, int height, String title) {
		this.setMinWidth(-1);
		this.setMinHeight(-1);
		this.setMaxWidth(-1);
		this.setMaxHeight(-1);
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		this.setTitle(title);
	}

	/**
	 * Constructeur paramétré
	 * @param x Coordonnée X de la boîte
	 * @param y Coordonnée Y de la boîte
	 * @param width Largueur de la boîte
	 * @param height Hauteur de la boîte
	 * @param title Titre de la boîte
	 * @param minWidth Largueur minimum de la boîte
	 * @param minHeight Hauteur minimum de la boîte
	 * @param maxWidth Largueur maximum de la boîte
 	 * @param maxHeight Hauteur maximum de la boîte
	 */
	public Box(int x, int y, int width, int height, String title, int minWidth, int minHeight, int maxWidth, int maxHeight) {
		this.setMinWidth(minWidth);
		this.setMinHeight(minHeight);
		this.setMaxWidth(maxWidth);
		this.setMaxHeight(maxHeight);
		this.setX(x);
		this.setY(y);
		this.setWidth(width);
		this.setHeight(height);
		this.setTitle(title);
	}
	
	//----------------------------------------------------------------------------------------------------
	/**
	 * METHODES
	 */
	//----------------------------------------------------------------------------------------------------
	
	public void updateCoordinates(int x, int y) {
		setX(x);
		setY(y);
	}

	public void updateSize(int width, int height) {
		setWidth(width);
		setHeight(height);
	}
	
	public void updateBounds(int minWidth, int minHeight, int maxWidth, int maxHeight) {
		setMinWidth(minWidth);
		setMinHeight(minHeight);
		setMaxWidth(maxWidth);
		setMaxHeight(maxHeight);
	}
	
	public Rectangle convertToRectangle() {
		return new Rectangle(x, y, width, height);
	}
	
	public String toString() {
		return title + "," + x + "," + y + "," + width + "," + height;
	}
	
	
	//----------------------------------------------------------------------------------------------------
	/**
	 * GETTERS
	 */
	//----------------------------------------------------------------------------------------------------

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getTitle() {
		return title;
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

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setWidth(int width) {
		if(width < minWidth) {
			this.width = minWidth;
		} else {
			if(maxWidth >= 0 && width > maxWidth) {
				this.width = maxWidth;
			} else {
				this.width = width;
			}
		}
	}
	
	public void setHeight(int height) {
		if(height < minHeight) {
			this.height = minHeight;
		} else {
			if(maxHeight >= 0 && height > maxHeight) {
				this.height = maxHeight;
			} else {
				this.height = height;
			}
		}
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setMinWidth(int minWidth) {
		if(minWidth < 0) {
			this.minWidth = 0;
		} else {
			this.minWidth = minWidth;
		}
		if(width < this.minWidth) {
			width = this.minWidth;
		}
	}


	public void setMinHeight(int minHeight) {
		if(minHeight < 0) {
			this.minHeight = 0;
		} else {
			this.minHeight = minHeight;
		}
		if(height < this.minHeight) {
			height = this.minHeight;
		}
	}


	public void setMaxWidth(int maxWidth) {
		if(maxWidth < 0) {
			this.maxWidth = -1;
		} else {
			this.maxWidth = maxWidth;
		}
		if(this.maxWidth >= 0 && width > this.maxWidth) {
			width = maxWidth;
		}
	}

	public void setMaxHeight(int maxHeight) {
		if(maxHeight < 0) {
			this.maxHeight = -1;
		} else {
			this.maxHeight = maxHeight;
		}
		if(this.maxHeight >= 0 && height > this.maxHeight) {
			height = maxHeight;
		}
	}
	
}

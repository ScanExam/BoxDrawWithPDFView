package view.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

import model.Box;
import model.BoxList;

/**
 * Permet de dessiner des boîtes de sélection
 * @author Julien Cochet
 */
public class SelectionPanel extends JPanel {

	//----------------------------------------------------------------------------------------------------
	/**
	 * ATTRIBUTS
	 */
	//----------------------------------------------------------------------------------------------------
	
	private static final long serialVersionUID = 5249320877761722353L;

	// Boîtes de sélection
	private BoxList selectionBoxes;

	// Etats possibles
	private static int INIT = 0, CREATE = 1, RESIZE = 2, MOVE = 3, DELETE = 4;
	// Etat actuel
	private int currentState = INIT;
	
	// Dernier point cliqué par l'utilisateur
    private Point lastClickPoint;
	// Dernière boîte sélectionné par l'utilisateur
    private Box lastBoxSelected;
    // Indique si la croix d'une boîte à été cliquée
    private boolean crossCliked;
    
	// Largueur minimum des boîtes
	private static int MIN_WIDTH = 64;
	// Hauteur minimum des boîtes
	private static int MIN_HEIGHT = 32;
	// Hauteur des boîtes des titres
	private static int TITLE_HEIGHT = 32;
	
	// Couleur des contours des boîtes
	private Color outLineColor = Color.BLACK;
	// Couleur de l'intérieur des boîtes
	private Color selectionColor = new Color(255, 255, 255, 64);
	// Couleur de l'intérieur des titres
	private Color titleColor = new Color(191, 191, 191, 64);

	
	//----------------------------------------------------------------------------------------------------
	/**
	 * CONSTRUCTEUR
	 */
	//----------------------------------------------------------------------------------------------------
	
	public SelectionPanel(BoxList selectionBoxes) {
        setOpaque(false);
        
		this.selectionBoxes = selectionBoxes;
		this.selectionBoxes.updateBounds(MIN_WIDTH, MIN_HEIGHT, -1, -1);
		this.selectionBoxes.setPanel(this);

        MouseAdapter mouseHandler = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            	lastClickPoint = e.getPoint();
            	crossCliked = false;
            	
            	Box pointedBox = checkPoint(e.getPoint());
            	if(pointedBox == null) {
            		currentState = CREATE;
                	createBox(e);
            		currentState = RESIZE;
            		resizeBox(e, lastBoxSelected);
            	} else {
            		lastBoxSelected = pointedBox;
            		if(!crossCliked) {
                		currentState = MOVE;
                		moveBox(e, lastBoxSelected);
            		} else {
                		currentState = DELETE;
                		deleteBox(lastBoxSelected);
            		}
            	}
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            	currentState = INIT;
            	lastClickPoint = null;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
            	if(currentState == RESIZE) {
                	resizeBox(e, lastBoxSelected);
            	}
            	if(currentState == MOVE) {
            		moveBox(e, lastBoxSelected);
            	}
            }
        };

        addMouseListener(mouseHandler);
        addMouseMotionListener(mouseHandler);
    }

	
	//----------------------------------------------------------------------------------------------------
	/**
	 * METHODES
	 */
	//----------------------------------------------------------------------------------------------------
	
	/**
	 * Retourne la boîte se trouvant au point indiqué ou null s'il n'y en a pas
	 * @param point endroit à observer
	 * @return la boîte ou null
	 */
	private Box checkPoint(Point point) {
		for (Box box : this.selectionBoxes.getList()) {
			// On regarde pour chaque boîtes si le point est dans le titre de la boîte
			if((point.x >= box.getX() && point.x <= (box.getX() + box.getWidth())) &&
			   (point.y >= box.getY()  - TITLE_HEIGHT && point.y <= (box.getY()))) {
				// Si le point est très à droite, on considère la croix dela boîte cliquée
				if((point.x >= (box.getX() + box.getWidth() - 24)) && (point.x <= (box.getX() + box.getWidth()))) {
					crossCliked = true;
				}
				return box;
			}
		}
		return null;
	}
	
	/**
	 * Ajoute une nouvelle boîte
	 * @param e Event
	 */
    private void createBox(MouseEvent e) {
        selectionBoxes.addBox(lastClickPoint.x, lastClickPoint.y, ("Qst " + selectionBoxes.size()));
        lastBoxSelected = selectionBoxes.getBox(selectionBoxes.size() - 1);
        
        repaint();
    }

	/**
	 * Change la taille d'une boîte de ces coordonnées à l'endroit pointé par la souris
	 * @param e Event
	 * @param box Boîte à modifer
	 */
    private void resizeBox(MouseEvent e, Box box) {
        Point dragPoint = e.getPoint();
        
        int x = Math.min(lastClickPoint.x, dragPoint.x);
        int y = Math.min(lastClickPoint.y, dragPoint.y);
        int width = Math.max(lastClickPoint.x - dragPoint.x, dragPoint.x - lastClickPoint.x);
        int height = Math.max(lastClickPoint.y - dragPoint.y, dragPoint.y - lastClickPoint.y);
        selectionBoxes.updateBox(box, x, y, width, height);
        
        repaint();
    }

	/**
	 * Change la position d'une boîte à l'endroit pointé par la souris
	 * @param e Event
	 * @param box Boîte à bouger
	 */
    private void moveBox(MouseEvent e, Box box) {
    	Point dragPoint = e.getPoint();
    	
    	int initialX = box.getX();
    	int initialY = box.getY();
    	int dragX = dragPoint.x - lastClickPoint.x;
    	int dragY = dragPoint.y - lastClickPoint.y;
    	box.updateCoordinates(initialX + dragX, initialY + dragY);
    	
        repaint();
        lastClickPoint = e.getPoint();
    }
    
    /**
     * Supprime une boîte
     * @param box Boîte à retirer
     */
    private void deleteBox(Box box) {
    	selectionBoxes.removeBox(box);
    	
    	repaint();
    }

    
	//----------------------------------------------------------------------------------------------------
	/**
	 * AFFICHAGE
	 */
	//----------------------------------------------------------------------------------------------------
	
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();

        paintSelectionBoxes(g2d, selectionBoxes);
        paintBoxesTitle(g2d, selectionBoxes);
        
        g2d.dispose();
    }
    
    // Dessine les boîtes de sélection
    private void paintSelectionBoxes(Graphics2D g2d, BoxList boxes) {
        // Affichage des contours des boîtes
    	g2d.setColor(outLineColor);
        for(int i = 0; i < boxes.size(); i++) {
            g2d.draw(boxes.getBox(i).convertToRectangle());
        }
        
        // Coloration de l'intérieur des boîtes
        g2d.setColor(selectionColor);
        for(int i = 0; i < boxes.size(); i++) {
        	g2d.fill(boxes.getBox(i).convertToRectangle());
        }
    }
    
    // Ajoute des titres aux boîtes de sélection
    private void paintBoxesTitle(Graphics2D g2d, BoxList boxes) {
    	// Création d'un list de boîtes de titre
    	BoxList boxesTitle = new BoxList(MIN_WIDTH, TITLE_HEIGHT, -1, TITLE_HEIGHT);
		boxesTitle.updateBounds(MIN_WIDTH, MIN_HEIGHT, -1, -1);
    	for(int i = 0; i < boxes.size(); i++) {
    		boxesTitle.addBox(boxes.getBox(i).getX(), boxes.getBox(i).getY() - TITLE_HEIGHT, boxes.getBox(i).getWidth(), TITLE_HEIGHT, boxes.getBox(i).getTitle());
    	}
    	
        // Affichage des contours des boîtes
    	g2d.setColor(outLineColor);
        for(int i = 0; i < boxesTitle.size(); i++) {
        	Rectangle crtRec = boxesTitle.getBox(i).convertToRectangle();
            g2d.draw(crtRec);
            g2d.drawString(boxesTitle.getBox(i).getTitle(), crtRec.x + 8, crtRec.y + 20);
            g2d.drawString("x", crtRec.x + crtRec.width - 14, crtRec.y + 20);
        }

        // Coloration de l'intérieur des titres
        g2d.setColor(titleColor);
        for(int i = 0; i < boxesTitle.size(); i++) {
        	g2d.fill(boxesTitle.getBox(i).convertToRectangle());
        }
    }
    
}

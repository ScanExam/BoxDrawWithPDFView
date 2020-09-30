package view.javaFX;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.LinkedList;

import controller.ExportController;
import controller.ExportController.coord;
import controller.StateMachine;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import pdf.PDFFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.collections.FXCollections;

import java.io.File;
import java.io.IOException;


public class Main extends Application {
	public static final double TITLE_RECT_HEIGHT = 25.0;

	public ExportController exportController = new ExportController();
	private FileChooser fileChooser = new FileChooser();
	private PDFFactory pdfFactory = new PDFFactory();
	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
		
		this.primaryStage = primaryStage;
		try {
			Parent root = FXMLLoader.load(getClass().getResource("TryOne.fxml"));

			Scene scene = new Scene(root, 1230, 720);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);

	}

	public int findSelFromButton(Button source) {
		int i = 0;
		for (Selector sel : selectors) {
			if (sel.CloseButton == source) {
				return i;
			}
			i++;
		}

		return -1;
	}
	
	public int findSelFromTitleRect(Rectangle source) {
		int i = 0;
		for (Selector sel : selectors) {
			if (sel.titleRect == source) {
				return i;
			}
			i++;
		}

		return -1;
	}

	public double getRelative(double current, double max) {

		return current / max;
	}

	public LinkedList<Rectangle> rects = new LinkedList<Rectangle>();
	public LinkedList<Rectangle> titleRects = new LinkedList<Rectangle>();
	public LinkedList<Selector> selectors = new LinkedList<Selector>();

	public Pane drawPane;
	
	
	public ComboBox chooseSourceFile;
	public ComboBox choosePage;
	public ImageView imagePDF;

	public double startDragX = 0;
	public double startDragY = 0;

	public void startDragging(MouseEvent e) {
		System.out.println("B");
		if (StateMachine.getState() == StateMachine.IDLE) {
			
			if (e instanceof MouseEvent) {

				MouseEvent mouseevent = (MouseEvent) e;
				startDragX = mouseevent.getX();
				startDragY = mouseevent.getY();
				Object o = mouseevent.getSource();
				if (o instanceof Pane) {
					StateMachine.setState(StateMachine.CREATE);
					Pane g = (Pane) o; 
					Selector sel = new Selector(startDragX, startDragY, 0, 0);
					g.getChildren().add(sel.getMainRect());
					g.getChildren().add(sel.getTitleRect());
					g.getChildren().add(sel.getText());
					g.getChildren().add(sel.CloseButton);
					sel.CloseButton.setOnAction(new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent e) {
							close(e);
						}
					});
				
					
					sel.titleRect.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent e) {
							moving(e);
						}});
					
					sel.titleRect.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent e) {
							moving(e);
						}});
	
					rects.addFirst(sel.getMainRect());
					titleRects.addFirst(sel.getTitleRect());
					selectors.addFirst(sel);
				}
				if (o instanceof Rectangle) {
					
					System.out.println("hello");
				}
			}
		}
	}

	@FXML
	public void dragging(Event e) {
		System.out.println("C");
		if (e instanceof MouseEvent) {
			if (StateMachine.getState() == StateMachine.CREATE) {
				MouseEvent mouseevent = (MouseEvent) e;
				Node source = (Node) mouseevent.getSource();
				Selector sel = selectors.getFirst();
				double mouseX = mouseevent.getX();
				double mouseY = mouseevent.getY();
				mouseX = Math.min(mouseX, source.maxWidth(-1));
				mouseY = Math.min(mouseY, source.maxHeight(-1));
				mouseX = Math.max(mouseX, 0);
				mouseY = Math.max(mouseY, 0);
	
				if (mouseX >= startDragX) {
					sel.updatePosX(startDragX, mouseX - startDragX);
				} else {
					sel.updatePosX(mouseX, startDragX - mouseX);
	
				}
	
				if (mouseY >= startDragY) {
					sel.updatePosY(startDragY, mouseY - startDragY);
				} else {
					sel.updatePosY(mouseY, startDragY - mouseY);
				}
			}
		}
		if (StateMachine.getState() == StateMachine.MOVE) {
			System.out.println("Moving");
			
		}
	}

	double offsetX = -1;
	double offsetY = -1;
	public void moving(MouseEvent e) {
		System.out.println("A");
		StateMachine.setState(StateMachine.MOVE);
		Selector r = selectors.get(findSelFromTitleRect((Rectangle) e.getSource()));
		if (offsetX < 0 ) {
			 offsetX=e.getX() - r.getMainRect().getX();
		}
		if (offsetY < 0 ) {
			 offsetY=e.getY() - r.getTitleRect().getY();
		}
		
		System.out.println(e.getX() + " - " + r.getMainRect().getX() + "=" + offsetX);
		r.updatePos(e.getX()- offsetX,e.getY() - offsetY);
		System.out.println("moving");
	}
	@FXML
	public void endDragging(Event e) {
		offsetX = -1;
		Rectangle rect = selectors.getFirst().getMainRect();
		Pane source = (Pane) e.getSource();

		double relaX = getRelative(rect.getX(), source.getWidth());
		double relaY = getRelative(rect.getY(), source.getHeight());
		double relaWidth = getRelative(rect.getWidth(), source.getWidth());
		double relaHeight = getRelative(rect.getHeight(), source.getHeight());
		exportController.add(relaX, relaY, relaWidth, relaHeight);
		StateMachine.setState(0);
	}

	public static TextField createTextField(double x, double y, String s) {
		TextField text = new TextField(s);
		text.setLayoutX(x);
		text.setLayoutY(y);
		return text;
	}

	public static Rectangle createRect(double x, double y, double h, double w) {
		Rectangle rect = new Rectangle(x, y, h, w);
		rect.setFill(Color.rgb(200, 200, 200, 0.2));
		rect.setStroke(Color.BLACK);
		rect.setStrokeWidth(1);
		rect.setMouseTransparent(false);
		
		return rect;
	}

	double StartX = 0;
	double StartY = 0;

	@FXML
	public void MouseDown(Event event) {
		System.out.println("hi");
		if (event instanceof MouseEvent) {
			System.out.println("Mouse event");
			MouseEvent mouseevent = (MouseEvent) event;
			StartX = mouseevent.getX();
			StartY = mouseevent.getY();
		}
	}

	@FXML
	public void MouseUp(Event event) {
		if (event instanceof MouseEvent) {
			System.out.println("Mouse event");
			MouseEvent mouseevent = (MouseEvent) event;
			Pane g = (Pane) mouseevent.getSource();
			if (mouseevent.getSource() instanceof Pane) {
				Rectangle rect = new Rectangle(StartX, StartY, mouseevent.getX() - StartX, mouseevent.getY() - StartY);
				rect.setFill(Color.rgb(200, 200, 200, 0.2));
				rect.setStroke(Color.BLACK);
				rect.setStrokeWidth(1);
				g.getChildren().add(rect);

			}
		}

	}

	@FXML
	public void Zoom(Event event) {
		if (event instanceof ScrollEvent) {
			ScrollEvent scrollevent = (ScrollEvent) event;
			Object source = scrollevent.getSource();
			double dirY = scrollevent.getDeltaY();
			Node node = (Node) source;
			if (dirY > 0) {
				node.setScaleX(node.getScaleX() * 0.95);
				node.setScaleY(node.getScaleY() * 0.95);
			} else {
				node.setScaleX(node.getScaleX() * 1.05);
				node.setScaleY(node.getScaleY() * 1.05);
			}
		}
	}

	
	private void addCoords() {
		
	}
	
	@FXML
	public void save() {
		exportController.save();
	}

	// TODO fix
	@FXML
	public void load() {
		exportController.load();
		for (coord c : exportController.coords) {
			Rectangle r = new Rectangle();
			r.setX(c.relativeX * drawPane.getMaxWidth());
			r.setY(c.relativeY * drawPane.getMaxHeight());
			r.setWidth(c.relativeWidth * drawPane.getMaxWidth());
			r.setHeight(c.relativeHeight * drawPane.getMaxHeight());
			r.setFill(Color.rgb(200, 200, 200, 0.2));
			r.setStroke(Color.BLACK);
			r.setStrokeWidth(1);
			drawPane.getChildren().add(r);
			rects.addFirst(r);
		}
	}

	@FXML
	public void close(Event event) {
		Object source = event.getSource();
		if (source instanceof Button) {
			int index = findSelFromButton((Button) source);
			selectors.get(index).removeFrom(drawPane);
			selectors.remove(index);
			exportController.coords.remove(index);
		}

	}

	@FXML
	public void clear() {
		drawPane.getChildren().clear();
		rects.clear();
		exportController.coords.clear();
	}

	@FXML
	public void quit() {
		Platform.exit();
	}
	
	
	@FXML
	public void addFile(Event event) {
		System.out.println("Open file");
        File file = fileChooser.showOpenDialog(primaryStage);

        
        if ((file != null) && !this.pdfFactory.getPdfFileMap().containsKey(file.getName())) {
        	
        	this.pdfFactory.setNewPdfFile(file);

            System.out.println("Fichier chargé");
            
            chooseSourceFile.setItems(FXCollections.observableArrayList(pdfFactory.getPdfFileList()));
        }
        else{
            System.out.println("Aucun fichier selectionné ou déjà chargé");
        }

	}
	
	@FXML
	public void chooseSrcEvent(Event event) {
		
		System.out.println("Action sur menu Source");
        if(chooseSourceFile.getValue()==null){
        	chooseSourceFile.setValue(null);
            choosePage.setValue(null);
            imagePDF.setImage(null);
        }else{
            try {
                choosePage.setItems(FXCollections.observableArrayList(pdfFactory.getPagesOfDocument(chooseSourceFile.getValue().toString())));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            imagePDF.setImage(null);
        }

	}
	
	@FXML
	public void showPageEvent(Event event) {
		
		System.out.println("Action sur menu Page");
		
        if(choosePage.getValue()==null){
            imagePDF.setImage(null);
        }else{
            imagePDF.setImage(null);
            try {
                System.out.println("Actualisation affichage page "+choosePage.getValue());

                imagePDF.setImage(pdfFactory.getPdfFileFromKey(chooseSourceFile.getValue().toString()).getImageFromPDF((int) choosePage.getValue()));
                imagePDF.setFitHeight(600);
                imagePDF.setFitWidth(600);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }
        

	}

}

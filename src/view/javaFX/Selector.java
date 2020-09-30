package view.javaFX;

import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

public class Selector {
	Rectangle mainRect;
	Rectangle titleRect;
	TextField text;
	Button CloseButton;
	
	public Selector(double x, double y, double h, double w ) {
		mainRect = Main.createRect(x, y, h, w);
		titleRect = Main.createRect(x, y-Main.TITLE_RECT_HEIGHT, h, Main.TITLE_RECT_HEIGHT);
		text = Main.createTextField(x, y, "hello");
		CloseButton = new Button();
		CloseButton.setLayoutX(x + w);
		CloseButton.setLayoutY(y);
		CloseButton.setMaxHeight(0);
		CloseButton.setMaxWidth(0);
		CloseButton.setText("X");
		CloseButton.setId("closeButton");
		addEvents();
		
	}
	
	public Selector(Rectangle mainRect, Rectangle titleRect, TextField text) {
		this.mainRect = mainRect;
		this.titleRect = titleRect;
		this.text = text;
		//addEvents();
	}
	public void removeFrom(Pane p) {
		p.getChildren().remove(mainRect);
		p.getChildren().remove(titleRect);
		p.getChildren().remove(text);
		p.getChildren().remove(CloseButton);
	}
	private void addEvents() {
		EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				
			}
		};
		titleRect.addEventFilter(MouseEvent.MOUSE_PRESSED, eventHandler);
	}
	
	public void updatePos(double x, double y, double h, double w) {
		mainRect.setX(x);
		mainRect.setY(y);
		mainRect.setWidth(w);
		mainRect.setHeight(h);
		
		titleRect.setX(x);
		titleRect.setY(y-Main.TITLE_RECT_HEIGHT);
		titleRect.setWidth(w);
		titleRect.setHeight(Main.TITLE_RECT_HEIGHT);
		
		text.setLayoutX(x);
		text.setLayoutY(y - Main.TITLE_RECT_HEIGHT + 2);
	}
	
	public void updatePos(double x, double y) {
		mainRect.setX(x);
		mainRect.setY(y);
	
		
		titleRect.setX(x);
		titleRect.setY(y-Main.TITLE_RECT_HEIGHT);
		
		
		text.setLayoutX(x);
		text.setLayoutY(y - Main.TITLE_RECT_HEIGHT + 2);
	}
	
	public void updatePosX(double x, double w) {
		mainRect.setX(x);
		mainRect.setWidth(w);

		
		titleRect.setX(x);
		titleRect.setWidth(w);
		
		text.setLayoutX(x + 4);
		text.setMaxWidth(w);
		
		CloseButton.setLayoutX(x + w - 20);


	}
	
	public void updatePosY(double y, double h) {

		mainRect.setY(y);
		mainRect.setHeight(h);
		
		titleRect.setY(y-Main.TITLE_RECT_HEIGHT);
		titleRect.setHeight(Main.TITLE_RECT_HEIGHT);
		
		text.setLayoutY(y-Main.TITLE_RECT_HEIGHT);
		
		CloseButton.setLayoutY(y - Main.TITLE_RECT_HEIGHT);
	}
	
	
	public Rectangle getMainRect() {
		return mainRect;
	}

	public void setMainRect(Rectangle mainRect) {
		this.mainRect = mainRect;
	}

	public Rectangle getTitleRect() {
		return titleRect;
	}

	public void setTitleRect(Rectangle titleRect) {
		this.titleRect = titleRect;
	}

	public TextField getText() {
		return text;
	}

	public void setText(TextField text) {
		this.text = text;
	}
}
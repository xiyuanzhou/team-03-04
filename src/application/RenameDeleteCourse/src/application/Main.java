package application;
	

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//Node objects
			Label lbl1 = new Label("New Name of Course:");
			Label lbl2 = new Label("Delete Course?");
			Button btn1 = new Button("Submit");
			Button btn2 = new Button("Delete Course");
			TextField tf = new TextField("");
			
			VBox vbox = new VBox();
			//spacesbetween nodes and edges
			vbox.setPadding(new Insets(15, 15, 15, 15));
			
			//spaces between nodes
			vbox.setSpacing(15);
			vbox.getChildren().addAll(lbl1, tf, btn1);
			vbox.getChildren().addAll(lbl2, btn2);
			
			Scene scene = new Scene(vbox,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			/*BorderPane root = new BorderPane();
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();*/
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

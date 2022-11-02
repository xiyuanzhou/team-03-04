package application;
	
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.*;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			//testing
			Button button = new Button();
			button.setText("Click me!");
			button.setOnAction(new EventHandler<ActionEvent>(){

				@Override
				public void handle(ActionEvent t) {
					// TODO Auto-generated method stub
					System.out.println("Hello world");
				}
			});
			StackPane root = new StackPane();
			root.getChildren().add(button);
			
			Scene scene = new Scene(root,400,400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
	
			
			primaryStage.setTitle("demo");
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

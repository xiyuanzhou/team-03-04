package application;
	
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.*;


/**
 * Index Card Manager 
 * @Verion 0.9
 * @author Xiyuan Zhou
 * @author Honghao Ma
 * @author Mengzhen Zhao
 * @author Zach Woo
 * Team 4
 */

public class Main extends Application {
	/**
	 * The main entry point for all JavaFx applications
	 * has returned
	 * @param primaryStage - the primary stage for this application, onto which the
	 *                     application scene can be set.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			//testing
			// set home page as the first page
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/HomePage.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getClassLoader().getResource("css/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Index Card Manager");
			primaryStage.show();
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		//launch(args);

		Connection connection = null;
		
		try {
			connection = DatabaseUtils.dbConnection();
			Statement stmt = connection.createStatement();
			String sql = "DELETE FROM learnedcourses";
			stmt.executeUpdate(sql);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		launch(args);
	}
}

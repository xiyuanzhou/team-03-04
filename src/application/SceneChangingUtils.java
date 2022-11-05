package application;

import java.io.IOException;

import controller.homePageControl;
import controller.LoginPageControl;
import controller.SignupPageControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SceneChangingUtils {

	public static void changeScene(MouseEvent e, String title, String file) throws IOException {
		// TODO Auto-generated method stub
		Parent root = null;
		FXMLLoader loader = new FXMLLoader(SceneChangingUtils.class.getClassLoader().getResource(file));
		root = loader.load();

		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
	}
	
	public static void changeScene(ActionEvent e, String title, String file) throws IOException {
		// TODO Auto-generated method stub
		Parent root = null;
		FXMLLoader loader = new FXMLLoader(SceneChangingUtils.class.getClassLoader().getResource(file));
		root = loader.load();

		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
		
	}
	

}

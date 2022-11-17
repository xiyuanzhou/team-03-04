package application;

import java.io.IOException;
import java.util.EventObject;

import controller.homePageControl;
import controller.LoginPageControl;
import controller.ModifyAccountControl;
import controller.ResetPasswordControl2;
import controller.SignupPageControl;
import controller.UserPageControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SceneChangingUtils {
	
	
	
	/**
	 * Own function implenment the windows or new windows
	 * 
	 * @param fxml files, window title
	 * 
	 * @return nothing
	 */
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
	
	public static void changeScene(ActionEvent e, String title, String file, String test) throws IOException {
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

	public static void changeScene(MenuButton e, String file, String title) throws IOException {

		// TODO Auto-generated method stub
		Parent root = null;
		FXMLLoader loader = new FXMLLoader(SceneChangingUtils.class.getClassLoader().getResource(file));
		root = loader.load();
		

		Stage stage = (Stage) e.getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
	}
	
	public static void resetpasswordScene(ActionEvent e, String title, String file, String questions) throws IOException{
		
		// TODO Auto-generated method stub
		Parent root = null;
		FXMLLoader loader = new FXMLLoader(SceneChangingUtils.class.getClassLoader().getResource(file));
		root = loader.load();
		
		ResetPasswordControl2 questionpage = loader.getController();
		questionpage.setQuestionInformation(questions);
		

		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
	}
	
	public static void updatedprofileScene(ActionEvent e, String title, String file, String username) throws IOException{
		
		// TODO Auto-generated method stub
		Parent root = null;
		FXMLLoader loader = new FXMLLoader(SceneChangingUtils.class.getClassLoader().getResource(file));
		root = loader.load();
		
		ModifyAccountControl modifyAccountcontrol = loader.getController();
		modifyAccountcontrol.setUserInformation(username,null,null);
		
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
	}
	
}

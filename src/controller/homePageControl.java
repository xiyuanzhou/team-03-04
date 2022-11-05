package controller;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.SceneChangingUtils;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

public class homePageControl implements Initializable{
	@FXML
	private Button signupButton;
	
	@FXML
	private Button loginButton;
	
	
	/**
	 * Called my own function to show the scene change,
	 * which mean allow user when click button,
	 * open a new window
	 * 
	 */
	@FXML
	public void handleBtnSignupWindow(ActionEvent actionEvent) throws IOException{
		signupButton.setOnMouseClicked(e -> {
			try {
				SceneChangingUtils.changeScene(e, "Sign Up", "view/SignUp.fxml");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
	}
	
    @FXML
    public void handleBtnLoginWindow(ActionEvent event) {
    	
    	loginButton.setOnMouseClicked(e -> {
			try {
				SceneChangingUtils.changeScene(e, "Log In", "view/Login.fxml");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
    }

    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
}




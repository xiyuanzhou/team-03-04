package controller;

import application.DatabaseUtils;
import application.SceneChangingUtils;

import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginPageControl implements Initializable {
	
    @FXML
    private Button homepage;
    

    @FXML
    private Button loginBtn;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField usernameTextField;
    
    @FXML
    private Button button_signup;
    
    @FXML
    private Button button_welcome;
    
    @FXML
    private Button button_forgetpassword;


    @FXML
    public void handleBtnHomePageWindow(ActionEvent event) throws IOException{    	

    }


	/**
	 * Called to initialize a controller after its root element has been completely
	 * processed.
	 * 
	 * @param location  - The location used to resolve relative paths for the root
	 *                  object, or null if the location is not known.
	 * @param resources - The resources used to localize the root object, or null if
	 *                  the root object was not localized.
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
    	homepage.setOnMouseClicked(e -> {
			try {
				SceneChangingUtils.changeScene(e, "Index Card Manager", "view/HomePage.fxml");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
    	
    	button_signup.setOnMouseClicked(e -> {
			try {
				SceneChangingUtils.changeScene(e, "Sign Up", "view/SignUp.fxml");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
    	
    	loginBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				try {
					DatabaseUtils.logInUser(event,usernameTextField.getText(),passwordTextField.getText());
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
    		
    	});
    	
    	button_forgetpassword.setOnMouseClicked(e -> {
			try {
				SceneChangingUtils.changeScene(e, "Rset Password", "view/ResetPassword.fxml");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
    	
	}
   
	
}

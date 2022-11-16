package controller;

import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.DatabaseUtils;
import application.SceneChangingUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import edu.sjsu.yazdankhah.crypto.util.PassUtil;


public class SignupPageControl implements Initializable {
	
    @FXML
    private Button homepage;
    
    @FXML
    private Button button_signup;
    
    @FXML
    private Button button_login;
    
    @FXML
    private TextField tf_username;
    
    @FXML
    private TextField tf_password;
    
    @FXML
    private TextField tf_email;
    
    @FXML
    private TextField tf_secretquestion;
    
    @FXML
    private TextField tf_ans;
    
    @FXML
    public void handleBtnHomePageWindow(ActionEvent event) {
       	homepage.setOnMouseClicked(e -> {
    			try {
    				SceneChangingUtils.changeScene(e, "Index Card Manager", "view/HomePage.fxml");
    			} catch (IOException e1) {
    				e1.printStackTrace();
    			}
    		});
       	

       	
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
       	button_login.setOnMouseClicked(e -> {
    			try {
    				SceneChangingUtils.changeScene(e, "Log In", "view/Login.fxml");
    			} catch (IOException e1) {
    				e1.printStackTrace();
    			}
    		});
       	
       	button_signup.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				if (!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty() && !tf_email.getText().trim().isEmpty() && !tf_ans.getText().trim().isEmpty()) {
					try {
						PassUtil passUtil = new PassUtil();
						String passwordEncrypted = passUtil.encrypt(tf_password.getText());
						//DatabaseUtils.signUpUser(event, tf_username.getText(), tf_password.getText(), tf_email.getText(), tf_secretquestion.getText());
						DatabaseUtils.signUpUser(event, tf_username.getText(), passwordEncrypted, tf_email.getText(), tf_secretquestion.getText(),tf_ans.getText());
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					System.out.println("Please fill in all information");
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("Please fill in all information to sign up!");
					alert.show();
				}
			}
       		
       	});
       	
	}
	
	

}

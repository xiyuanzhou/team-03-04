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

public class ResetPasswordControl implements Initializable {
	
    @FXML
    private Button button_back;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_newpassword;
    
    @FXML
    private Button button_reset;
    
    @FXML
    private TextField tf_secretquestion;
    
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
		button_back.setOnMouseClicked(e -> {
			try {
				SceneChangingUtils.changeScene(e, "Index Card", "view/Login.fxml");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		button_reset.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				if (!tf_email.getText().trim().isEmpty() && !tf_newpassword.getText().trim().isEmpty() && !tf_secretquestion.getText().trim().isEmpty()) {
					try {
						DatabaseUtils.resetPassword(event, tf_email.getText(),tf_newpassword.getText(),tf_secretquestion.getText());
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else {
					System.out.println("Please fill in all information");
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("Please fill in all information!");
					alert.show();
				}
			}
	   		
	   	});
		
		
		
	}
	
}



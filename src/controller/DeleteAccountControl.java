package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.DatabaseUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class DeleteAccountControl implements Initializable {

    @FXML
    private Button button_goback;

    @FXML
    private Button button_submit;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_password;

    @FXML
    private TextField tf_username;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		button_goback.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				
				DatabaseUtils.changeScene(event, "view/UserPage.fxml","Index Card",DatabaseUtils.Global.hold_username);
			}
			
		});
		
		button_submit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				if (!tf_username.getText().trim().isEmpty() && !tf_password.getText().trim().isEmpty() && !tf_email.getText().trim().isEmpty()) {
					try {
						DatabaseUtils.deleteAccount(event, tf_username.getText(),tf_password.getText(),tf_email.getText());
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

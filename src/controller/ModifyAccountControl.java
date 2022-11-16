package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.DatabaseUtils;
import application.SceneChangingUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;

public class ModifyAccountControl implements Initializable{
    @FXML
    private Text label_hello;
    
    @FXML
    private Button button_main;
    
    @FXML
    private Button button_delete;
    
    
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		button_main.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				DatabaseUtils.changeScene(event, "view/UserPage.fxml","Index Card",DatabaseUtils.Global.hold_username);
			}
			
		});
		
		button_delete.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
		        Alert a = new Alert(AlertType.NONE);
				// TODO Auto-generated method stub
		        
                a.setAlertType(AlertType.CONFIRMATION);
                a.getDialogPane().setHeaderText("Are you sure want to delete?");
                a.showAndWait();
				
                if (a.getResult() == ButtonType.OK) {
    				try {
    					SceneChangingUtils.changeScene(event, "Delete Account", "view/DeleteAccount.fxml");
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
                }
			}
			
		});
	}
	
	public void setUserInformation(String username) {
		label_hello.setText(username + ", account");
	}

}

package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.DatabaseUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class UserPageControl implements Initializable{
    @FXML
    private Text label_welcome;

    @FXML
    private Button button_logout;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		button_logout.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				DatabaseUtils.changeScene(event, "view/HomePage.fxml", "Index Manager card", null);
			}
			
			
		});
		
		
		
	}
    
	
	public void setUserInformation(String username) {
		label_welcome.setText("Welcome "+ username + "!");
	}
    
}
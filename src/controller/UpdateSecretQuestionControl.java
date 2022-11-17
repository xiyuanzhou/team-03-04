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
import javafx.scene.control.TextField;

public class UpdateSecretQuestionControl implements Initializable{

    @FXML
    private Button button_goback;

    @FXML
    private Button button_updated;

    @FXML
    private TextField tf_newquestion;
    
    @FXML
    private TextField tf_ans;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		button_goback.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				try {
					SceneChangingUtils.updatedprofileScene(event, "user profile","view/ModifyAccount.fxml",DatabaseUtils.Global.hold_username);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		
		
		button_updated.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if (!tf_newquestion.getText().trim().isEmpty() && !tf_ans.getText().trim().isEmpty()) {
					try {
						DatabaseUtils.updateProfiles(event,DatabaseUtils.Global.hold_username, tf_newquestion.getText(),3,tf_ans.getText());
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

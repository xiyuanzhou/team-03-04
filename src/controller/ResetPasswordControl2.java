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
import javafx.scene.text.Text;

public class ResetPasswordControl2 implements Initializable{

    @FXML
    private Button button_homepage;

    @FXML
    private Text label_questions;

    @FXML
    private TextField tf_ans;

    @FXML
    private TextField tf_newpassword;
    
    @FXML
    private Button button_submit;
    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		button_homepage.setOnMouseClicked(e -> {
			try {
				SceneChangingUtils.changeScene(e, "Index Card Manager", "view/HomePage.fxml");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		button_submit.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				if (!tf_ans.getText().trim().isEmpty() && !tf_newpassword.getText().trim().isEmpty()) {
					try {
						DatabaseUtils.resetPassword2(event, tf_ans.getText(),tf_newpassword.getText());
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
	
	public void setQuestionInformation(String questions) {
		label_questions.setText(questions);
	}
    
    
}

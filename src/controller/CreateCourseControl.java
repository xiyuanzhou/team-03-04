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

public class CreateCourseControl implements Initializable{
	
	@FXML
	private Button button_back;
	
	@FXML
	private Button button_newcourse;
	
	@FXML
	private TextField tf_course;
	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		button_back.setOnMouseClicked(e -> {
				try {
					SceneChangingUtils.changeScene(e, "Welcome", "view/UserPage.fxml");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
		
		button_newcourse.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				
				if (!tf_course.getText().trim().isEmpty() ) {
					try {
						DatabaseUtils.newCourse(event, tf_course.getText(),null,null);
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

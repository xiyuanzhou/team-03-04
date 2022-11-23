package controller;

import application.DatabaseUtils;
import application.SceneChangingUtils;
import application.DatabaseUtils.Global;
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


public class RenameCourseControl implements Initializable{
    @FXML
    private Button button_back;

    @FXML
    private Button button_rename;

    @FXML
    private TextField tf_rename;
    
    @FXML
    private TextField tf_changename;


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
		
//		button_back.setOnMouseClicked(e -> {
//			try {
//				SceneChangingUtils.changeScene(e, "Index Card", "view/UserPage.fxml");
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//		});
		
		button_back.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				DatabaseUtils.loginchangeScene(event, "view/UserPage.fxml","Index Card",DatabaseUtils.Global.hold_username,DatabaseUtils.Global.hold_courses);
			}
			
		});
		
		button_rename.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				if (!tf_rename.getText().trim().isEmpty() && !tf_changename.getText().trim().isEmpty()) {
					try {
						DatabaseUtils.renameCourse(event, tf_rename.getText().trim(),tf_changename.getText().trim(),null);
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

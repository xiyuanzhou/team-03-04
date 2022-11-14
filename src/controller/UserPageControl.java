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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;

public class UserPageControl implements Initializable{
    @FXML
    private Text label_welcome;

    @FXML
    private Button button_logout;
    
    @FXML
    private Button button_course;
    
    @FXML
    private Button button_delete;
    
    @FXML
    private Button button_rename;
    
    
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
		button_logout.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
		        Alert a = new Alert(AlertType.NONE);
				// TODO Auto-generated method stub
				//System.out.println(DatabaseUtils.Global.hold_username);
                a.setAlertType(AlertType.CONFIRMATION);
                a.showAndWait();
                if (a.getResult() == ButtonType.OK) {
                    //do stuff
    				DatabaseUtils.Global.hold_username = "";
    				DatabaseUtils.changeScene(event, "view/HomePage.fxml", "Index Manager card", null);
                }
				//user have been logout 
//				DatabaseUtils.Global.hold_username = "";
//				DatabaseUtils.changeScene(event, "view/HomePage.fxml", "Index Manager card", null);
			}
			
			
		});
		
		button_course.setOnMouseClicked(e -> {
			try {
				SceneChangingUtils.changeScene(e, "Create New Course", "view/CreateCourse.fxml");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		button_delete.setOnMouseClicked(e -> {
			try {
				SceneChangingUtils.changeScene(e, "Delete Course", "view/RenameDeleteCourse.fxml");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		button_rename.setOnMouseClicked(e -> {
			try {
				SceneChangingUtils.changeScene(e, "Rename Course", "view/RenameCourse.fxml");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		
	}
    
	
	public void setUserInformation(String username) {
		label_welcome.setText("Welcome "+ username + "!");
	}
    
}
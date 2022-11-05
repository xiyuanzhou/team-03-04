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


public class RenameCourseControl implements Initializable{
    @FXML
    private Button button_back;

    @FXML
    private Button button_rename;

    @FXML
    private TextField tf_rename;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		button_back.setOnMouseClicked(e -> {
			try {
				SceneChangingUtils.changeScene(e, "Sign Up", "view/UserPage.fxml");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
	}
	
	
}

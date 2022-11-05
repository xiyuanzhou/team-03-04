package controller;

import application.SceneChangingUtils;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginPageControl implements Initializable {
	
    @FXML
    private Button homepage;

    @FXML
    public void handleBtnHomePageWindow(ActionEvent event) throws IOException{
    	homepage.setOnMouseClicked(e -> {
			try {
				SceneChangingUtils.changeScene(e, "Index Card Manager", "view/HomePage.fxml");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
    }

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
}
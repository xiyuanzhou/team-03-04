package controller;

import java.net.URL;
import java.util.ResourceBundle;

import application.DatabaseUtils;
import controller.ModifyIndexCardControl.Global;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class CreateIndexCardControl implements Initializable{

    @FXML
    private Button button_mainpage;

    @FXML
    private Button button_submit;

    @FXML
    private Text label_courses;

    @FXML
    private TextArea ta_newindexcard;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		button_mainpage.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				DatabaseUtils.loginchangeScene(event, "view/UserPage.fxml","Index Card",DatabaseUtils.Global.hold_username,DatabaseUtils.Global.hold_courses);
			}
		});
		
		button_submit.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				if (!ta_newindexcard.getPromptText().trim().isEmpty()) {
					try {
						DatabaseUtils.newIndexCard(event,Global.temp_course,ta_newindexcard.getText());
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
	
	public void setcourseInformation(String courses) {
		label_courses.setText(courses);
		
		ModifyIndexCardControl.Global.temp_course = courses;
	}
    
    

}

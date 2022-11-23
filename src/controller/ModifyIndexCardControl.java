package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.DatabaseUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

public class ModifyIndexCardControl implements Initializable{
	public static class Global{
		public static String temp_course = "";
		public static String temp_old_index = "";
	}
	
    @FXML
    private Button button_mainpage;

    @FXML
    private Button button_submit;

    @FXML
    private TextArea ta_newtext;

    @FXML
    private TextArea ta_show;
    
    @FXML
    private Text label_courses;

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
				if (!ta_newtext.getPromptText().trim().isEmpty()) {
					try {
						DatabaseUtils.updateIndexCard(event,Global.temp_course,ta_newtext.getText(),Global.temp_old_index);
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
	
	public void setIndexCardInformation(String courses,String index_card) {
		label_courses.setText(courses);
		ta_show.setPromptText(index_card);
		
		Global.temp_course = courses;
		Global.temp_old_index = index_card;
	}

}

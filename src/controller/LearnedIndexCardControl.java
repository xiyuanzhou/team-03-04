package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.DatabaseUtils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;

public class LearnedIndexCardControl implements Initializable{

    @FXML
    private Button button_back;

    @FXML
    private ListView<String> list_courses;

    @FXML
    private ListView<String> list_learned;

    
    ObservableList list = FXCollections.observableArrayList();
    
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		button_back.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				DatabaseUtils.loginchangeScene(event, "view/UserPage.fxml","Index Card",DatabaseUtils.Global.hold_username,DatabaseUtils.Global.hold_courses);
			}
		});
		
		list_courses.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			String textarea = "";

		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		        // Your action here
		        //System.out.println("Selected item: " + newValue);
		    	ObservableList list_result = FXCollections.observableArrayList();
		    	list_result = DatabaseUtils.getLearnedIndexCard(newValue);
		    	//list_learned.getItems().addAll(list_result);

		    	list_learned.setItems(list_result);

		    	System.out.println(list_result);
		    	//System.out.println("learned list");
		    }
		});
		
	}
	
	
	public void learned_data(ArrayList<String> courses) {
		//String[] list2 = {"cmpe1","cmpe2","cmpe3","cmpe4"};
		list.removeAll(list);
		
		for (int i = 0; i < courses.size(); i++) {
			list.addAll(courses.get(i));
		}
		list_courses.getItems().addAll(list);
		
		//list_courses.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
		//System.out.println(DatabaseUtils.temp_learned.learned_indexs);
		
	}

}

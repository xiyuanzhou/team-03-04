package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.DatabaseUtils;
import application.IndexCard;
import application.SceneChangingUtils;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UserPageControl implements Initializable{
    @FXML
    private Text label_welcome;

//    @FXML
//    private Button button_logout;
    
    @FXML
    private Button button_course;
    
    @FXML
    private Button button_delete;
    
    @FXML
    private Button button_rename;
    
    @FXML
    private MenuButton menubutton_account;
    
    @FXML
    private MenuItem item_profiles;
    
    @FXML
    private MenuItem item_logout;
    
    @FXML
    private ListView<String> list_items;
    
    @FXML
    private ListView<String> list_indexcard;//delete
    
    @FXML
    private Button button_courses;
    
    @FXML
    private TableView<IndexCard> my_tableview;
    
    @FXML
    private TableColumn<IndexCard, String> col_indexcard;
    
    @FXML
    private TableColumn<IndexCard, CheckBox> col_checkbox;
    
    @FXML
    private Button button_modify;
    
    @FXML
    private TextArea show_content;
    
    @FXML
    private Button button_newindexcard;
    
    @FXML
    private Button button_deleteindexcard;
    
    @FXML
    private TableColumn<IndexCard, String> col_status;
    
    @FXML
    private Button button_learned;
    
    
    ObservableList list = FXCollections.observableArrayList();
    
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
//		button_logout.setOnAction(new EventHandler<ActionEvent>() {
//
//			@Override
//			public void handle(ActionEvent event) {
//		        Alert a = new Alert(AlertType.NONE);
//				// TODO Auto-generated method stub
//				//System.out.println(DatabaseUtils.Global.hold_username);
//                a.setAlertType(AlertType.CONFIRMATION);
//                a.getDialogPane().setHeaderText("Are you sure want to Log out?");
//                a.showAndWait();
//                if (a.getResult() == ButtonType.OK) {
//                    //do stuff
//    				DatabaseUtils.Global.hold_username = "";
//    				DatabaseUtils.changeScene(event, "view/HomePage.fxml", "Index Manager card", null);
//                }
//				//user have been logout 
////				DatabaseUtils.Global.hold_username = "";
////				DatabaseUtils.changeScene(event, "view/HomePage.fxml", "Index Manager card", null);
//			}
//			
//			
//		});
		
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
		
		item_profiles.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				DatabaseUtils.changeScene(menubutton_account, "view/ModifyAccount.fxml","User Profiles",DatabaseUtils.Global.hold_username);
				
				DatabaseUtils.test(DatabaseUtils.Global.hold_username);
			}
			
		});
		
		item_logout.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
		        Alert a = new Alert(AlertType.NONE);
				// TODO Auto-generated method stub
				//System.out.println(DatabaseUtils.Global.hold_username);
                a.setAlertType(AlertType.CONFIRMATION);
                a.getDialogPane().setHeaderText("Are you sure want to Log out?");
                a.showAndWait();
                if (a.getResult() == ButtonType.OK) {
                    //do stuff
    				DatabaseUtils.Global.hold_username = "";
    				DatabaseUtils.Global.hold_courses = new ArrayList<String>();
    				DatabaseUtils.temp_learned.learned_courses = new ArrayList<String>();
    				ModifyIndexCardControl.Global.temp_course = "";
    				ModifyIndexCardControl.Global.temp_old_index = "";
    				
    				Connection connection = null;
    				
    				try {
    					connection = DatabaseUtils.dbConnection();
    					Statement stmt = connection.createStatement();
    					String sql = "DELETE FROM learnedcourses";
    					stmt.executeUpdate(sql);
    					
    					
    				} catch (SQLException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
    				
    				try {
						SceneChangingUtils.changeScene(menubutton_account, "view/HomePage.fxml", "Index Manager card");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }
				//user have been logout 
//				DatabaseUtils.Global.hold_username = "";
//				DatabaseUtils.changeScene(event, "view/HomePage.fxml", "Index Manager card", null);
			}
			
			
		});
		
		button_courses.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//list_indexcard.getItems().clear();

				String textarea = "";
				ObservableList list_result = list_items.getSelectionModel().getSelectedItems();
				
				list_result = DatabaseUtils.getIndexCard(list_result.get(0).toString());
				//System.out.println(list_result.get(0));
				//
//				ObservableList<IndexCard> data = FXCollections.observableArrayList(
//						new IndexCard("checking1 sasdasdsdadasdasdadadasdasdasdd",new CheckBox()),
//						new IndexCard("checking2d dsasdadasdasdasdadasdasdasd",new CheckBox())
//						);
				ObservableList<IndexCard> data = FXCollections.observableArrayList();

				for (Object item : list_result) {
					//System.out.println((String) item);
					data.add(new IndexCard((String) item,new CheckBox(),"**"));
				}
				
				col_indexcard.setCellValueFactory(new PropertyValueFactory<IndexCard,String>("index_notes"));
				col_checkbox.setCellValueFactory(new PropertyValueFactory<IndexCard,CheckBox>("checkbox"));
				col_status.setCellValueFactory(new PropertyValueFactory<IndexCard,String>("status"));
				
				my_tableview.setItems(data);
				
				my_tableview.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
				
				my_tableview.setOnMousePressed(e ->{
					ObservableList<IndexCard> temp = my_tableview.getSelectionModel().getSelectedItems();
					
					//System.out.println(temp.get(0).getIndex_notes());
					//System.out.println("clikc again");
					show_content.setPromptText(temp.get(0).getIndex_notes());
					
//					temp.get(0).getCheckbox().setOnAction(action->{
//						System.out.println("checkbox click");
//						
//					});
					
					temp.get(0).getCheckbox().selectedProperty().addListener((ObservableValue<? extends Boolean>ov, Boolean old_val, Boolean new_val)->{
						//System.out.println(temp.get(0).getCheckbox().getText() + " changed from " + old_val + " to " + new_val);
						//System .out.println(old_val);
						if (new_val == true) {
							//System.out.println("1");
							//System.out.println(temp.get(0).getIndex_notes());
		    				ObservableList<IndexCard> learned_data = my_tableview.getSelectionModel().getSelectedItems();
		    				String index_cards = learned_data.get(0).getIndex_notes();
		    				String courses = list_items.getSelectionModel().getSelectedItems().get(0);
		    				
		    				DatabaseUtils.saveLearnedIndexCard(courses,index_cards);
		    				
						}else {
							System.out.println("checkbox off");

						}
					});
					
		        });

				
				//System.out.println(data.get(0).getCheckbox());


				//				for (Object item : list_result) {
//					textarea += String.format("%s%n", (String) item);
//				}
//				list_indexcard.getItems().addAll(list_result);

			}
			
			
		});
		button_modify.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
		        Alert a = new Alert(AlertType.NONE);
				// TODO Auto-generated method stub
		        
                a.setAlertType(AlertType.CONFIRMATION);
                a.getDialogPane().setHeaderText("Are you sure want to Modify the index cards?");
                a.showAndWait();
                if (a.getResult() == ButtonType.OK) {
                    //do stuff
    				ObservableList<IndexCard> data = my_tableview.getSelectionModel().getSelectedItems();
    				String index_cards = data.get(0).getIndex_notes();
    				String courses = list_items.getSelectionModel().getSelectedItems().get(0);
    				//System.out.println(data.get(0).getIndex_notes());
    				System.out.print(list_items.getSelectionModel().getSelectedItems().get(0));
    				
    				try {
    					SceneChangingUtils.updatedindexcardScene(arg0, "Modify Index Card","view/ModifyIndexCard.fxml",courses,index_cards);
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
                }

			}
			
		});
		
		button_newindexcard.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
		        Alert a = new Alert(AlertType.NONE);
				// TODO Auto-generated method stub
		        
                a.setAlertType(AlertType.CONFIRMATION);
                a.getDialogPane().setHeaderText("Are you sure want to Add an new index cards?");
                a.showAndWait();
                if (a.getResult() == ButtonType.OK) {
                    //do stuff
    				ObservableList<IndexCard> data = my_tableview.getSelectionModel().getSelectedItems();
    				//String index_cards = data.get(0).getIndex_notes();
    				String courses = list_items.getSelectionModel().getSelectedItems().get(0);
    				//System.out.println(data.get(0).getIndex_notes());
    				//System.out.print(list_items.getSelectionModel().getSelectedItems().get(0));
    				
    				try {
    					SceneChangingUtils.newindexcardScene(arg0, "Add Index Card","view/CreateIndexCard.fxml",courses);
    				} catch (IOException e) {
    					// TODO Auto-generated catch block
    					e.printStackTrace();
    				}
                }

			}
			
		});
		
		button_deleteindexcard.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
		        Alert a = new Alert(AlertType.NONE);
				// TODO Auto-generated method stub
		        
                a.setAlertType(AlertType.CONFIRMATION);
                a.getDialogPane().setHeaderText("Are you sure want to Delete this index cards?");
                a.showAndWait();
                if (a.getResult() == ButtonType.OK) {
                    //do stuff
    				ObservableList<IndexCard> data = my_tableview.getSelectionModel().getSelectedItems();
    				String index_cards = data.get(0).getIndex_notes();
    				String courses = list_items.getSelectionModel().getSelectedItems().get(0);
    				
    				//System.out.println(data.get(1).getCheckbox().selectedProperty());
    				//System.out.print(list_items.getSelectionModel().getSelectedItems().get(0));
    				//System.out.println(data.get(0).getCheckbox());
    				
    				//System.out.println(data);

					try {
						DatabaseUtils.deleteIndexCard(arg0, courses, index_cards);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
                }

			}
			
		});
		
		button_learned.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				DatabaseUtils.load_learned_index_cards();
				

				try {
					SceneChangingUtils.learnedindexcardScene(arg0,"Learned Index Cards", "view/LearnedIndexCard.fxml",DatabaseUtils.temp_learned.learned_courses);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		

		
	}
    
	
	public void setUserInformation(String username) {
		label_welcome.setText("Welcome "+ username + "!");
	}
    
	public void loadData(ArrayList<String> courses) {
		//String[] list2 = {"cmpe1","cmpe2","cmpe3","cmpe4"};
		list.removeAll(list);
		
		for (int i = 0; i < courses.size(); i++) {
			list.addAll(courses.get(i));
		}
		list_items.getItems().addAll(list);
		
		//
//		ObservableList<IndexCard> data = FXCollections.observableArrayList(
//				new IndexCard("checking1 s",new CheckBox()),
//				new IndexCard("checking2d ds",new CheckBox())
//				);
//		
//		col_indexcard.setCellValueFactory(new PropertyValueFactory<IndexCard,String>("index_notes"));
//		col_checkbox.setCellValueFactory(new PropertyValueFactory<IndexCard,CheckBox>("checkbox"));
//		my_tableview.setItems(data);
		//
		list_items.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		
	}
	
}
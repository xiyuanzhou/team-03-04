package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EventObject;

import controller.homePageControl;
import controller.CreateIndexCardControl;
import controller.LearnedIndexCardControl;
import controller.LoginPageControl;
import controller.ModifyAccountControl;
import controller.ModifyIndexCardControl;
import controller.ResetPasswordControl2;
import controller.SignupPageControl;
import controller.UnlearnedIndexCardControl;
import controller.UserPageControl;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SceneChangingUtils {
	
	
	
	/**
	 * Own function implenment the windows or new windows
	 * 
	 * @param fxml files, window title
	 * 
	 * @return nothing
	 */
	public static void changeScene(MouseEvent e, String title, String file) throws IOException {
		// TODO Auto-generated method stub
		Parent root = null;
		FXMLLoader loader = new FXMLLoader(SceneChangingUtils.class.getClassLoader().getResource(file));
		root = loader.load();

		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
	}
	
	public static void changeScene(ActionEvent e, String title, String file) throws IOException {
		// TODO Auto-generated method stub
		Parent root = null;
		FXMLLoader loader = new FXMLLoader(SceneChangingUtils.class.getClassLoader().getResource(file));
		root = loader.load();

		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
		
	}
	
	public static void changeScene(ActionEvent e, String title, String file, String test) throws IOException {
		// TODO Auto-generated method stub
		Parent root = null;
		FXMLLoader loader = new FXMLLoader(SceneChangingUtils.class.getClassLoader().getResource(file));
		root = loader.load();
		

		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
		
	}

	public static void changeScene(MenuButton e, String file, String title) throws IOException {

		// TODO Auto-generated method stub
		Parent root = null;
		FXMLLoader loader = new FXMLLoader(SceneChangingUtils.class.getClassLoader().getResource(file));
		root = loader.load();
		

		Stage stage = (Stage) e.getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
	}
	
	public static void resetpasswordScene(ActionEvent e, String title, String file, String questions) throws IOException{
		
		// TODO Auto-generated method stub
		Parent root = null;
		FXMLLoader loader = new FXMLLoader(SceneChangingUtils.class.getClassLoader().getResource(file));
		root = loader.load();
		
		ResetPasswordControl2 questionpage = loader.getController();
		questionpage.setQuestionInformation(questions);
		

		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
	}
	
	public static void updatedprofileScene(ActionEvent event, String fxmlFile, String title,  String username) throws IOException{
//		
//		// TODO Auto-generated method stub
//		Parent root = null;
//		FXMLLoader loader = new FXMLLoader(SceneChangingUtils.class.getClassLoader().getResource(file));
//		root = loader.load();
//		
//		ModifyAccountControl modifyAccountcontrol = loader.getController();
//		modifyAccountcontrol.setUserInformation(username,null,null);
//		
//		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
//		Scene scene = new Scene(root);
//		stage.setTitle(title);
//		stage.setScene(scene);
//		stage.show();
//		stage.centerOnScreen();
		Parent root = null;
		
		Connection connection = null;

		PreparedStatement psInsert = null;

		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;
		
		String temp_email = null;
		String temp_question =null;
		String temp_password =null;
		
		if (username != null) {	
			
			try {
				connection = DatabaseUtils.dbConnection();
			    System.out.println("Opened database successfully");
				psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
				psCheckUserExists.setString(1,username);
				resultSet = psCheckUserExists.executeQuery();
				
				if (resultSet.isBeforeFirst()) {
					temp_email = resultSet.getString("email");
					temp_question = resultSet.getString("secret_question");
					temp_password = resultSet.getString("password");
				}
				FXMLLoader loader = new FXMLLoader(DatabaseUtils.class.getClassLoader().getResource(fxmlFile));
				root = loader.load();
				ModifyAccountControl modifyAccountControl = loader.getController();
				modifyAccountControl.setUserInformation(username,temp_email,temp_question,temp_password);
				
			}catch (IOException e){
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if(resultSet != null) {
					try {
						resultSet.close();
						
					}catch(SQLException e) {
						e.printStackTrace();
					}
				}
				if (psCheckUserExists != null) {
					try {
						psCheckUserExists.close();
					} catch ( SQLException e) {
						e.printStackTrace();
					}
				}
				if (psInsert != null) {
					try {
						psInsert.close();
						
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				if (connection != null) {
					try { 
						connection.close();
					} catch(SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}else {
			try {
				root = FXMLLoader.load(DatabaseUtils.class.getClassLoader().getResource(fxmlFile));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setTitle(title);
		stage.setScene(new Scene(root));
		stage.show();
	}
	
	public static void updatedindexcardScene(ActionEvent e, String title, String file, String courses,String index_cards) throws IOException{
		
		// TODO Auto-generated method stub
		Parent root = null;
		FXMLLoader loader = new FXMLLoader(SceneChangingUtils.class.getClassLoader().getResource(file));
		root = loader.load();
		
		ModifyIndexCardControl modifyindexcardcontrol = loader.getController();
		modifyindexcardcontrol.setIndexCardInformation(courses, index_cards);
		
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
	}
	
	public static void newindexcardScene(ActionEvent e, String title, String file, String courses) throws IOException{
		
		// TODO Auto-generated method stub
		Parent root = null;
		FXMLLoader loader = new FXMLLoader(SceneChangingUtils.class.getClassLoader().getResource(file));
		root = loader.load();
		
		CreateIndexCardControl createindexcardcontrol = loader.getController();
		createindexcardcontrol.setcourseInformation(courses);
		
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
	}
	
	public static void learnedindexcardScene(ActionEvent e, String title, String file,ArrayList<String> courses) throws IOException{
		
		// TODO Auto-generated method stub
		Parent root = null;
		FXMLLoader loader = new FXMLLoader(SceneChangingUtils.class.getClassLoader().getResource(file));
		root = loader.load();
		
		LearnedIndexCardControl learnedindexcardcontrol = loader.getController();
		learnedindexcardcontrol.learned_data(courses);
		
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
	}

	
	public static void unlearnedindexcardScene(ActionEvent e, String title, String file,ArrayList<String> courses) throws IOException{
		
		// TODO Auto-generated method stub
		Parent root = null;
		FXMLLoader loader = new FXMLLoader(SceneChangingUtils.class.getClassLoader().getResource(file));
		root = loader.load();
		
		UnlearnedIndexCardControl unlearnedindexcardcontrol = loader.getController();
		unlearnedindexcardcontrol.unlearned_data(courses);
		
		Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
		Scene scene = new Scene(root);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
		stage.centerOnScreen();
	}

	
}

package application;

import java.io.IOException;
import java.sql.*;

import controller.UserPageControl;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class DatabaseUtils {
	public static void changeScene(ActionEvent event, String fxmlFile, String title,  String username) {
		Parent root = null;
		
		if (username != null) {
			
			try {
				FXMLLoader loader = new FXMLLoader(DatabaseUtils.class.getClassLoader().getResource(fxmlFile));
				root = loader.load();
				UserPageControl userPageControl = loader.getController();
				userPageControl.setUserInformation(username);
				
			}catch (IOException e){
				e.printStackTrace();
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
	
	public static void signUpUser(ActionEvent event, String username, String password, String email) throws ClassNotFoundException {
		Connection connection = null;
	    Statement stmt = null;

		PreparedStatement psInsert = null;
		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;
		
		try {

			Class.forName("org.sqlite.JDBC");
		    connection = DriverManager.getConnection("jdbc:sqlite:UserDb.sqlite");
		    System.out.println("Opened database successfully");
		    //connection.close();
		    
		    
		    //System.out.println("Table created successfully");
			
			psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
			psCheckUserExists.setString(1,username);
			
			resultSet = psCheckUserExists.executeQuery();
			
			if (resultSet.isBeforeFirst()) {
				System.out.println("User already exists");
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("You cannot use this username.");
				alert.show();
			}else {
				psInsert = connection.prepareStatement("INSERT INTO users (username, password, email) VALUES (?,?,?)");
				psInsert.setString(1, username);
				psInsert.setString(2, password);
				psInsert.setString(3, email);
				
				psInsert.executeUpdate();
				
				changeScene(event,"view/UserPage.fxml","Welcome!", username);
			}
			

		}catch(SQLException e ) {
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

		
	}
	
	public static void logInUser(ActionEvent event, String username, String password) throws ClassNotFoundException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet =null;
		
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:UserDb.sqlite");
			preparedStatement = connection.prepareStatement("SELECT password FROM users WHERE username =?");
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			
			if (!resultSet.isBeforeFirst()) {
				System.out.println("User not found in the database!");
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Provided credentials are incorrect!");
				alert.show();
			}else {
				while (resultSet.next()) {
					String retrievedPassword = resultSet.getString("password");
					if (retrievedPassword.equals(password)) {
						changeScene(event,"view/UserPage.fxml","Welcome!",username);
					}else {
						System.out.println("Passwords did not match!");
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setContentText("The provided credentials are incorrect!");
						alert.show();
					}
				}
			}
		}catch (SQLException e){
			e.printStackTrace();
		}finally {
			if(resultSet != null) {
				try {
					resultSet.close();
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
				} catch ( SQLException e) {
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
	}
}

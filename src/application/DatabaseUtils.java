package application;

import java.io.IOException;
import java.sql.*;

import controller.ModifyAccountControl;
import controller.UserPageControl;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import edu.sjsu.yazdankhah.crypto.util.PassUtil;



public class DatabaseUtils {
	public class Global{
		public static String hold_username = "";
		
	}
	
	/**
	 * Handles all SQLite connections
	 * 
	 * @return connection object
	 */
	public static Connection dbConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:UserDb.sqlite");
			return conn;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	/**
	 * Chaning each the window each time click on the button
	 * 
	 * @param event, fxmlFile, window title, username
	 * @return nothing
	 */
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
		stage.setTitle("Index Card");
		stage.setScene(new Scene(root));
		stage.show();
		
	}
	
	/**
	 * Chaning each the window each time click on the button
	 * 
	 * @param event, fxmlFile, window title, username
	 * @return nothing
	 */
	public static void changeScene(MenuButton event, String fxmlFile, String title,  String username) {
		Parent root = null;
		
		if (username != null) {
			
			try {
				FXMLLoader loader = new FXMLLoader(DatabaseUtils.class.getClassLoader().getResource(fxmlFile));
				root = loader.load();
				ModifyAccountControl modifyAccountControl = loader.getController();
				modifyAccountControl.setUserInformation(username);
				
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
		
		Stage stage = (Stage) event.getScene().getWindow();
		stage.setTitle(title);
		stage.setScene(new Scene(root));
		stage.show();
		
	}
	
	
	/**
	 * By signing up for user, passing value to funtion and access into database sqlite
	 * 
	 * @param event, username, password, email
	 * @return nothing
	 */
	public static void signUpUser(ActionEvent event, String username, String password, String email,String secretquestion,String secretquestion_ans) throws ClassNotFoundException {
		Connection connection = null;
	    Statement stmt = null;

		PreparedStatement psInsert = null;
		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;
		
		try {
			
			//Class.forName("org.sqlite.JDBC");
			
			//database inside the build path (jar)
		    //connection = DriverManager.getConnection("jdbc:sqlite:UserDb.sqlite");
			connection = dbConnection();
			
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
				psInsert = connection.prepareStatement("INSERT INTO users (username, password, email, secret_question, secret_question_ans) VALUES (?,?,?,?,?)");
				psInsert.setString(1, username);
				psInsert.setString(2, password);
				psInsert.setString(3, email);
				psInsert.setString(4, secretquestion);
				psInsert.setString(5, secretquestion_ans);
				
				psInsert.executeUpdate();
				
				Global.hold_username = username;
		        Alert a = new Alert(AlertType.NONE);
				// TODO Auto-generated method stub
				//System.out.println(DatabaseUtils.Global.hold_username);
                a.setAlertType(AlertType.INFORMATION);
                a.getDialogPane().setHeaderText("Account created success!");
                a.showAndWait();
                
				changeScene(event,"view/UserPage.fxml","Welcome!", Global.hold_username);
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
	
	
	/**
	 * By loin function for user, passing value to funtion and access into database sqlite
	 * check if the username and password matching the database 
	 * 
	 * @param event, username, password
	 * @return nothing
	 */
	public static void logInUser(ActionEvent event, String username, String password) throws ClassNotFoundException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet =null;
		
		//String dBPassword = "";
		String passwordDecrypted = "";
		PassUtil passUtil = new PassUtil();
		
		try {
//			Class.forName("org.sqlite.JDBC");
//			connection = DriverManager.getConnection("jdbc:sqlite:UserDb.sqlite");
			connection = dbConnection();

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
					passwordDecrypted = passUtil.decrypt(retrievedPassword);

					if (passwordDecrypted.equals(password)) {
						Global.hold_username = username;
				        Alert a = new Alert(AlertType.NONE);
						// TODO Auto-generated method stub
						//System.out.println(DatabaseUtils.Global.hold_username);
		                a.setAlertType(AlertType.INFORMATION);
		                a.getDialogPane().setHeaderText("Log in success!");
		                a.showAndWait();
						changeScene(event,"view/UserPage.fxml","Welcome!",Global.hold_username);
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
	
	/**
	 * By Add new course function, add course into the database
	 * 
	 * @param  event, new courses, password, email
	 * @return nothing
	 */
	public static void newCourse(ActionEvent event, String courses, String password, String email) throws ClassNotFoundException {
		
		Connection connection = null;
	    Statement stmt = null;

		PreparedStatement psInsert = null;
		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;
		
		try {

//			Class.forName("org.sqlite.JDBC");
//		    connection = DriverManager.getConnection("jdbc:sqlite:UserDb.sqlite");
			connection = dbConnection();

		    System.out.println("Opened database successfully");
		    //connection.close();
		    
		    
		    //System.out.println("Table created successfully");
			
			psCheckUserExists = connection.prepareStatement("SELECT * FROM courses WHERE coursename = ?");
			psCheckUserExists.setString(1,courses);
			
			resultSet = psCheckUserExists.executeQuery();
			
			if (resultSet.isBeforeFirst()) {
				System.out.println("courses exist ");
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("You cannot add this courses.");
				alert.show();
			}else {
				psInsert = connection.prepareStatement("INSERT INTO courses (coursename) VALUES (?)");
				psInsert.setString(1, courses);
				
				psInsert.executeUpdate();
				
				changeScene(event,"view/UserPage.fxml","Add successful!", null);
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
	
	/**
	 * Delete course function by user enter course name and search database for the informatin
	 * check if found in db
	 * 
	 * @param  event,  coursename,  password, email
	 * @return nothing
	 */
	public static void deleteCourse(ActionEvent event, String coursename, String password,String email) throws ClassNotFoundException {
		Connection connection = null;
	    Statement stmt = null;

		PreparedStatement psInsert = null;
		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;
		
		try {

//			Class.forName("org.sqlite.JDBC");
//		    connection = DriverManager.getConnection("jdbc:sqlite:UserDb.sqlite");
			connection = dbConnection();

		    System.out.println("Opened database successfully");
		    //connection.close();
		    
		    
		    //System.out.println("Table created successfully");
			
			psCheckUserExists = connection.prepareStatement("SELECT * FROM courses WHERE coursename = ?");
			psCheckUserExists.setString(1,coursename);
			
			resultSet = psCheckUserExists.executeQuery();
			
			if (resultSet.isBeforeFirst()) {
				System.out.println("courses found");
				psInsert = connection.prepareStatement("DELETE FROM courses WHERE coursename = '"+coursename+"'");
				psInsert.executeUpdate();
				
				changeScene(event,"view/UserPage.fxml","Welcome!", "delete successful");

			}else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("can't find course");
				alert.show();
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
	
	/**
	 * rename course function by user enter course name and search database for the informatin
	 * check if found in db, after it rename new name that user enter
	 * 
	 * @param  event,  coursename,  password, email
	 * @return nothing
	 */
	public static void renameCourse(ActionEvent event, String coursename, String rename,String email) throws ClassNotFoundException {
		Connection connection = null;
	    Statement stmt = null;

		PreparedStatement psInsert = null;
		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;
		
		try {

//			Class.forName("org.sqlite.JDBC");
//		    connection = DriverManager.getConnection("jdbc:sqlite:UserDb.sqlite");
			connection = dbConnection();

		    System.out.println("Opened database successfully");
		    //connection.close();
		    
		    
		    //System.out.println("Table created successfully");
			
			psCheckUserExists = connection.prepareStatement("SELECT * FROM courses WHERE coursename = ?");
			psCheckUserExists.setString(1,coursename);
			
			resultSet = psCheckUserExists.executeQuery();
			
			if (resultSet.isBeforeFirst()) {
				System.out.println("courses found");
				psInsert = connection.prepareStatement("UPDATE courses set coursename = '"+rename+"' where coursename = '"+coursename+"'");
				psInsert.executeUpdate();
				
				changeScene(event,"view/UserPage.fxml","Welcome!", "Rename successful");

			}else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("can't find course");
				alert.show();
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
	
	/**
	 * reset password function, it allow user to passing email by verfiy and enter new password
	 * only email for checking user exist
	 * 
	 * @param  event, password, email
	 * @return nothing
	 */
	
	public static void resetPassword(ActionEvent event, String email, String username) throws ClassNotFoundException {
		Connection connection = null;
	    
		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;
		String email1 = null;
		String username1 = null;
		String questions = null;
		
        Alert a = new Alert(AlertType.NONE);

		try {

//			Class.forName("org.sqlite.JDBC");
//		    connection = DriverManager.getConnection("jdbc:sqlite:UserDb.sqlite");
			connection = dbConnection();

		    System.out.println("Opened database successfully");
		    //connection.close();
		    
		    
		    //System.out.println("Table created successfully");
			
			psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE email = ? AND username = ?");
			psCheckUserExists.setString(1,email);
			psCheckUserExists.setString(2, username);
			
			resultSet = psCheckUserExists.executeQuery();
			
			if (resultSet.isBeforeFirst()) {
				System.out.println("email found");
				System.out.println("user found");
				//email1 = resultSet.getString("email");
				//username1 = resultSet.getString("username");
				questions = resultSet.getString("secret_question");
				
				//System.out.println(email1 + "  " + username1);
				//System.out.println(questions);

                a.setAlertType(AlertType.INFORMATION);
                a.getDialogPane().setHeaderText("User have been found! Jump to the Secret Questions");
                a.showAndWait();
				try {
					Global.hold_username = resultSet.getString("username");
					SceneChangingUtils.resetpasswordScene(event,"secret question","view/ResetPassword2.fxml",questions);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("can't find email or user name");
				alert.show();
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
			if (connection != null) {
				try { 
					connection.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	public static void resetPassword2(ActionEvent event, String answer,String newpassword) throws ClassNotFoundException {
		Connection connection = null;
	    Statement stmt = null;
	    
		PreparedStatement psInsert = null;
		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;
		
        Alert a = new Alert(AlertType.NONE);

		try {

//			Class.forName("org.sqlite.JDBC");
//		    connection = DriverManager.getConnection("jdbc:sqlite:UserDb.sqlite");
			connection = dbConnection();

		    System.out.println("Opened database successfully");
		    //connection.close();
		    
		    
		    //System.out.println("Table created successfully");
			
			psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND secret_question_ans = ?");
			psCheckUserExists.setString(1,Global.hold_username);
			psCheckUserExists.setString(2, answer);
			
			resultSet = psCheckUserExists.executeQuery();
			
			if (resultSet.isBeforeFirst()) {
				System.out.println("answer is correct found");
				
				PassUtil passUtil = new PassUtil();
				String encryptedPass = passUtil.encrypt(newpassword);
				
				psInsert = connection.prepareStatement("UPDATE users set password = '"+encryptedPass+"' where username = '"+Global.hold_username+"'");
				psInsert.executeUpdate();
				
                a.setAlertType(AlertType.INFORMATION);
                a.getDialogPane().setHeaderText("Password have been reset!");
                a.showAndWait();
				try {
					SceneChangingUtils.changeScene(event,"Welcome!","view/Login.fxml");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Answer is wrong");
				alert.show();
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

	public static void deleteAccount(ActionEvent event, String username, String password,String email) throws ClassNotFoundException {
		Connection connection = null;

		PreparedStatement psInsert = null;
		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;
		
		String passwordDecrypted = "";
		PassUtil passUtil = new PassUtil();
		
		try {
			connection = dbConnection();

		    System.out.println("Opened database successfully");
		    //connection.close();
		    
		    //System.out.println("Table created successfully");
			
			psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND email = ?");
			psCheckUserExists.setString(1,username);
			psCheckUserExists.setString(2,email);

			resultSet = psCheckUserExists.executeQuery();
			
			if (resultSet.isBeforeFirst()) {
				System.out.println("User found");
				
				String retrievedPassword = resultSet.getString("password");
				passwordDecrypted = passUtil.decrypt(retrievedPassword);
				
				if (passwordDecrypted.equals(password)) {
					Global.hold_username = username;
					psInsert = connection.prepareStatement("DELETE FROM users WHERE username = '"+username+"'");
					psInsert.executeUpdate();
					
			        Alert a = new Alert(AlertType.NONE);
					// TODO Auto-generated method stub
			        
	                a.setAlertType(AlertType.INFORMATION);
	                a.getDialogPane().setHeaderText("Delete success!");
	                a.showAndWait();
					
					changeScene(event,"view/HomePage.fxml","Welcome!", null);	
					
					}else {
					System.out.println("Passwords did not match!");
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("The provided credentials are incorrect!");
					alert.show();
				}

			}else {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Won't match");
				alert.show();
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
	
	
	
	
	public static void test(String user) {
		Connection connection = null;
		
		PreparedStatement preparedStatement = null;
		ResultSet result = null;
		Statement create = null;
		String username = null;
		String email = null;
		String questions = null;
		
		try {

			connection = dbConnection();

		    System.out.println("Opened database successfully");
		    //connection.close();
			preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
			preparedStatement.setString(1, user);
			result = preparedStatement.executeQuery();
			
			while (result.next()) {
				username = result.getString("username");
				email = result.getString("email");
				questions = result.getString("secret_question");
			}
			
			System.out.println("Account information " + username + " and " + email);
			System.out.println("Your questions " + questions);

		}catch(SQLException e ) {
			e.printStackTrace();
			
		}finally {
			if(result != null) {
				try {
					result.close();
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if (create != null) {
				try {
					create.close();
				} catch ( SQLException e) {
					e.printStackTrace();
				}
				
			}
			if (preparedStatement != null) {
				try {
					preparedStatement.close();
					
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
	
}

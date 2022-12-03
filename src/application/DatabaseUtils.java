package application;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

import controller.ModifyAccountControl;
import controller.UserPageControl;
import javafx.collections.FXCollections;
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
	
	/**
	 * Setting a global username and course list for passing the variables
	 * 
	 * @return connection object
	 */
	public static class Global{
		public static String hold_username = "";
		public static ArrayList<String> hold_courses = new ArrayList<String>();
		public static ArrayList<String> unlearned_courses = new ArrayList<String>();
		public static int userid = 0;
	}
	
	/**
	 * Setting a global learned course and learned index list for passing the variables
	 * 
	 * @return connection object
	 */
	public static class temp_learned{
		public static ArrayList<String> learned_courses = new ArrayList<String>();
		//public static ArrayList<String> learned_indexs = new ArrayList<String>();
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
	 * The function only changing the secene to userpage fxml
	 * 
	 * @param event, fxmlFile, window title, username, the list of courses 
	 * @return nothing
	 */
	public static void loginchangeScene(ActionEvent event, String fxmlFile, String title,  String username, ArrayList<String> courses) {
		Parent root = null;
		
		if (username != null) {
			
			try {
				FXMLLoader loader = new FXMLLoader(DatabaseUtils.class.getClassLoader().getResource(fxmlFile));
				root = loader.load();
				UserPageControl userPageControl = loader.getController();
				userPageControl.setUserInformation(username);
				userPageControl.loadData(courses);
				
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
		
		Connection connection = null;

		PreparedStatement psInsert = null;

		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;
		
		String temp_email = null;
		String temp_question =null;
		String temp_password = null;
		
		if (username != null) {	
			
			try {
				connection = dbConnection();
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
		
		Stage stage = (Stage) event.getScene().getWindow();
		stage.setTitle(title);
		stage.setScene(new Scene(root));
		stage.show();
		
	}
	
	
	/**
	 * The function allow changing to account modify fxml and shown all the user information
	 * 
	 * @param event, fxmlFile, window title, username
	 * @return nothing
	 */
	public static void changeScene2(ActionEvent event, String fxmlFile, String title,  String username) {
		Parent root = null;
		
		Connection connection = null;

		PreparedStatement psInsert = null;

		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;
		
		String temp_email = null;
		String temp_question =null;
		String temp_password = null;
		
		if (username != null) {	
			
			try {
				connection = dbConnection();
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

	
	/**
	 * By signing up for user, passing value to funtion and access into database sqlite
	 * 
	 * @param event, username, password, email
	 * @return nothing
	 */
	public static void signUpUser(ActionEvent event, String username, String password, String email,String secretquestion,String secretquestion_ans) throws ClassNotFoundException {
		Connection connection = null;

		PreparedStatement psInsert = null;
		PreparedStatement psCheckUserExists = null;
		PreparedStatement preparedStatement1 = null;

		ResultSet resultSet = null;
		ResultSet resultSet1 =null;
	    ArrayList<String> arrays = new ArrayList<String>();


		try {

			connection = dbConnection();
			
		    System.out.println("Opened database successfully");
		    //connection.close();
		    
		    
		    //System.out.println("Table created successfully");
			
			psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
			//preparedStatement1 = connection.prepareStatement("SELECT * FROM courses ");

			psCheckUserExists.setString(1,username);
			
			resultSet = psCheckUserExists.executeQuery();
			//resultSet1 = preparedStatement1.executeQuery();
//			while (resultSet1.next()) {
//				arrays.add(resultSet1.getString(1));
//				//System.out.println(resultSet1.getString(2));
//			}
//			Global.hold_courses = arrays;
			//System.out.println(arrays);
			
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
				connection.close();
				
				connection = dbConnection();
				preparedStatement1 = connection.prepareStatement("SELECT userid FROM users WHERE username = '"+username+"' ");
				resultSet1 = preparedStatement1.executeQuery();
				
				Global.userid = resultSet1.getInt(1);
				
				Global.hold_username = username;
				
				System.out.println("new user id : " + Global.userid + " name: " +  Global.hold_username);
		        Alert a = new Alert(AlertType.NONE);
				// TODO Auto-generated method stub
				//System.out.println(DatabaseUtils.Global.hold_username);
                a.setAlertType(AlertType.INFORMATION);
                a.getDialogPane().setHeaderText("Account created success!");
                a.showAndWait();
                
				//changeScene(event,"view/UserPage.fxml","Welcome!", Global.hold_username);
				DatabaseUtils.loginchangeScene(event, "view/UserPage.fxml","Index Card",DatabaseUtils.Global.hold_username,DatabaseUtils.Global.hold_courses);

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
		PreparedStatement preparedStatement1 = null;
		

		
		ResultSet resultSet =null;
		ResultSet resultSet1 =null;
		
		ResultSet r3 = null;
		
		String passwordDecrypted = "";
		PassUtil passUtil = new PassUtil();
		
	    ArrayList<String> arrays = new ArrayList<String>();

		try {
			connection = dbConnection();

			preparedStatement = connection.prepareStatement("SELECT password,userid FROM users WHERE username =?");
			//preparedStatement1 = connection.prepareStatement("SELECT * FROM courses ");
			//preparedStatement1 = connection.prepareStatement("SELECT courses FROM allcourses where courses_holder = '"+Global.userid+"'");

			//delete table
//			String test1 = "cs151";
			PreparedStatement psInsert = null;
//			psInsert = connection.prepareStatement("DROP TABLE IF EXISTS '"+test1+"' ");
//			psInsert = connection.prepareStatement("CREATE TABLE IF NOT EXISTS warehouses (\n"
//	                + "	id integer PRIMARY KEY,\n"
//	                + "	name text NOT NULL,\n"
//	                + "	capacity text NOT NULL\n"
//	                + ");");
//			psInsert = connection.prepareStatement("ALTER TABLE warehouses RENAME TO warehouses1");
//			psInsert = connection.prepareStatement("SELECT * FROM indexcard WHERE coursesname = 'cs151' ");
//			r3 = psInsert.executeQuery();
//			while (r3.next()) {
//				System.out.println(r3.getString(1));
//				System.out.println(r3.getString(2));
//			}
//			psInsert.executeUpdate();
//			System.out.println("here");
			
//	        String sql = "CREATE TABLE IF NOT EXISTS warehouses (\n"
//	                + "	id integer PRIMARY KEY,\n"
//	                + "	name text NOT NULL,\n"
//	                + "	capacity real\n"
//	                + ");";
	        
			
			//end
			
			preparedStatement.setString(1, username);
			
			resultSet = preparedStatement.executeQuery();
			
			//resultSet1 = preparedStatement1.executeQuery();
//			while (resultSet1.next()) {
//				arrays.add(resultSet1.getString(1));
//				//System.out.println(resultSet1.getString(2));
//			}
//			Global.hold_courses = arrays;
//			System.out.println(arrays);

			if (!resultSet.isBeforeFirst()) {
				System.out.println("User not found in the database!");
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Provided credentials are incorrect!");
				alert.show();
			}else {
				while (resultSet.next()) {

					String retrievedPassword = resultSet.getString("password");
					//int getId = resultSet.getInt("userid");
					passwordDecrypted = passUtil.decrypt(retrievedPassword);

					if (passwordDecrypted.equals(password)) {
						Global.hold_username = username;
						Global.userid = resultSet.getInt("userid");
						System.out.println("myid is : " + Global.userid );

						preparedStatement1 = connection.prepareStatement("SELECT courses FROM allcourses where courses_holder = '"+Global.userid+"'");
						resultSet1 = preparedStatement1.executeQuery();

						while (resultSet1.next()) {
							arrays.add(resultSet1.getString(1));
							//Global.unlearned_courses.add(resultSet1.getString(1));
							//System.out.println(resultSet1.getString(2));
						}
						Global.hold_courses = arrays;
						System.out.println(Global.unlearned_courses);
		                loginchangeScene(event,"view/UserPage.fxml","Welcome!",Global.hold_username,arrays);
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

		PreparedStatement psInsert = null;
		PreparedStatement tableInsert = null;
		Statement create = null;
		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;
		
		PreparedStatement whole_list = null;
		ResultSet resultList = null;
		try {

			connection = dbConnection();

		    System.out.println("Opened database successfully");
			
			psCheckUserExists = connection.prepareStatement("SELECT * FROM allcourses WHERE courses = ? AND courses_holder = ?");
			psCheckUserExists.setString(1,courses);
			psCheckUserExists.setInt(2, Global.userid);
			resultSet = psCheckUserExists.executeQuery();
			
			if (resultSet.isBeforeFirst()) {
				System.out.println("courses exist ");
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("You cannot add this courses.");
				alert.show();
			}else {
				psInsert = connection.prepareStatement("INSERT INTO allcourses (courses,courses_holder) VALUES (?,?)");
				psInsert.setString(1, courses);
				psInsert.setInt(2, Global.userid);
				
				psInsert.executeUpdate();
				connection.close();
				
				connection = dbConnection();
//				tableInsert = connection.prepareStatement("CREATE TABLE IF NOT EXISTS "+courses+" (\n"
//                + "id integer PRIMARY KEY,\n"
//                + "indexcard VARCHAR(20000) NOT NULL\n"
//                + ");");
				
//				String sql = "CREATE TABLE "+courses+" (" + "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "
//						+ "indexcard VARCHAR(200000) NOT NULL)";
//				create = connection.createStatement();
//				create.executeUpdate(sql);
//				tableInsert.executeUpdate();
				DatabaseMetaData dbm = connection.getMetaData();
				// check if "employee" table is there
				ResultSet tables = dbm.getTables(null, null, courses, null);
				if (tables.next()) {
				  // Table exists
					System.out.println("Table exists not do anything");
				}
				else {
				  // Table does not exist
					connection.close();
					connection = dbConnection();
					
					System.out.println("Table not exists creating ");
					String sql = "CREATE TABLE "+courses+" (" + "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE, "
					+ "indexcard TEXT,"
					+ "holder INTEGER,"
					+ "FOREIGN KEY(holder) REFERENCES users(userid))";
					create = connection.createStatement();
					create.executeUpdate(sql);

				}
				connection.close();
	
				connection = dbConnection();
				whole_list = connection.prepareStatement("SELECT courses FROM allcourses where courses_holder = '"+Global.userid+"'");//
				resultList = whole_list.executeQuery();//
				Global.hold_courses = new ArrayList<String>();
				while (resultList.next()) {
					Global.hold_courses.add(resultList.getString(1));
				}
				loginchangeScene(event,"view/UserPage.fxml","Welcome!", "Add NewCourse successful",Global.hold_courses);
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

		PreparedStatement psInsert = null;
		PreparedStatement del_coursetable = null;
		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;
		
		PreparedStatement whole_list = null;
		ResultSet resultList = null;
		
		try {
			connection = dbConnection();

		    System.out.println("Opened database successfully");

			
			psCheckUserExists = connection.prepareStatement("SELECT * FROM allcourses WHERE courses = ? AND courses_holder = ?");
			psCheckUserExists.setString(1,coursename);
			psCheckUserExists.setInt(2, Global.userid);
			
			resultSet = psCheckUserExists.executeQuery();
			
			if (resultSet.isBeforeFirst()) {
				System.out.println("courses found");
				psInsert = connection.prepareStatement("DELETE FROM allcourses WHERE courses = '"+coursename+"' AND courses_holder = '"+Global.userid+"' ");
				psInsert.executeUpdate();
				
				
				whole_list = connection.prepareStatement("SELECT courses FROM allcourses where courses_holder = '"+Global.userid+"'");//
				resultList = whole_list.executeQuery();//
				
				Global.hold_courses = new ArrayList<String>();
				while (resultList.next()) {
					Global.hold_courses.add(resultList.getString(1));
				}
				connection.close();
				connection = dbConnection();
				
				//del_coursetable = connection.prepareStatement("DROP TABLE IF EXISTS '"+coursename+"' ");
				//del_coursetable.executeUpdate();
				del_coursetable = connection.prepareStatement("DELETE FROM '"+coursename+"' WHERE holder = '"+Global.userid+"' ");
				del_coursetable.executeUpdate();
				
				loginchangeScene(event,"view/UserPage.fxml","Welcome!", "Delete successful",Global.hold_courses);

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
			if (whole_list != null) {
				try {
					whole_list.close();
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if (resultList != null) {
				try {
					resultList.close();
					
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
			if (del_coursetable != null) {
				try {
					del_coursetable.close();
				}catch (SQLException e) {
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

		PreparedStatement psInsert = null;
		PreparedStatement whole_list = null;//
		PreparedStatement rename_table = null;
		
		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;
		ResultSet resultList = null;//

		
		try {
			connection = dbConnection();

		    System.out.println("Opened database successfully");
			
			psCheckUserExists = connection.prepareStatement("SELECT * FROM allcourses WHERE courses = ?");
						
			psCheckUserExists.setString(1,coursename);
			
			resultSet = psCheckUserExists.executeQuery();
			

			if (resultSet.isBeforeFirst()) {
				System.out.println("courses found");
				psInsert = connection.prepareStatement("UPDATE allcourses set courses = '"+rename+"' where courses = '"+coursename+"'");
				psInsert.executeUpdate();
				
				whole_list = connection.prepareStatement("SELECT * FROM courses");//
				resultList = whole_list.executeQuery();//
				Global.hold_courses = new ArrayList<String>();
				while (resultList.next()) {
					Global.hold_courses.add(resultList.getString(1));
				}
				connection.close();
				
				connection = dbConnection();
				rename_table = connection.prepareStatement("ALTER TABLE "+coursename+" RENAME TO "+rename+" ");
				rename_table.executeUpdate();
				connection.close();
				loginchangeScene(event,"view/UserPage.fxml","Welcome!", "Rename successful",Global.hold_courses);

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
			if (whole_list != null) {
				try {
					whole_list.close();
				} catch ( SQLException e) {
					e.printStackTrace();
				}
				
			}
			if (resultList != null) {
				try {
					resultList.close();
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
	 * reset password function, it allow user to passing email and username by verfiy account exist
	 * only email for checking user exist
	 * 
	 * @param  event, username, email
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
			connection = dbConnection();

		    System.out.println("Opened database successfully");
		    //connection.close();
		    			
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
	
	
	/**
	 * reset password2 function, it allow user to new password and reset it
	 * This is after account been logged in
	 * 
	 * @param  event, ans, newpassword 
	 * @return nothing
	 */
	public static void resetPassword2(ActionEvent event, String answer,String newpassword) throws ClassNotFoundException {
		Connection connection = null;
	    
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

	
	/**
	 * delete account function allow user delete the account
	 * need username and user password and email to verify to delete
	 * 
	 * @param  event, username, email,password
	 * @return nothing
	 */
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
	
	
	/**
	 * update function allow user choose to which information wants to updated
	 * user could choose to which options to updated
	 * 
	 * @param  event, username, new updated information, options
	 * @return nothing
	 */
	public static void updateProfiles(ActionEvent event, String username, String new_updated, int options,String sqa) throws ClassNotFoundException {
		Connection connection = null;

		PreparedStatement psInsert = null;

		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;
		
		try {
			connection = dbConnection();

		    System.out.println("Opened database successfully");
		    
		    if (options == 1) {
				psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
				psCheckUserExists.setString(1,new_updated);
				resultSet = psCheckUserExists.executeQuery();
				
				if (resultSet.isBeforeFirst()) {
					System.out.println("User already exists");
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("You cannot use this username.");
					alert.showAndWait();
				}
				else {
					psInsert = connection.prepareStatement("UPDATE users set username = '"+new_updated+"' where username = '"+username+"'");
					psInsert.executeUpdate();
					
					Global.hold_username = new_updated;
					
					System.out.println(Global.hold_username + " is the name");
					
					//changeScene(event,"view/UserPage.fxml","Welcome!", "Updated successful");
					DatabaseUtils.loginchangeScene(event, "view/UserPage.fxml","Index Card",DatabaseUtils.Global.hold_username,DatabaseUtils.Global.hold_courses);

				}
		    }else if (options == 2) {
				psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
				psCheckUserExists.setString(1,username);
				resultSet = psCheckUserExists.executeQuery();
				
				if (resultSet.isBeforeFirst()) {
					String temp = resultSet.getString("email");
					
					psInsert = connection.prepareStatement("UPDATE users set email = '"+new_updated+"' where email = '"+temp+"'");
					psInsert.executeUpdate();					
					
					//changeScene(event,"view/UserPage.fxml","Welcome!", "Updated successful");
					DatabaseUtils.loginchangeScene(event, "view/UserPage.fxml","Index Card",DatabaseUtils.Global.hold_username,DatabaseUtils.Global.hold_courses);

				}
		    }else if (options == 3) {
				psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
				psCheckUserExists.setString(1,username);
				resultSet = psCheckUserExists.executeQuery();
				
				if (resultSet.isBeforeFirst()) {
					
					psInsert = connection.prepareStatement("UPDATE users set secret_question = '"+new_updated+"',secret_question_ans = '"+sqa+"' where username = '"+username+"'");

					psInsert.executeUpdate();
					
					//changeScene(event,"view/UserPage.fxml","Welcome!", "Updated successful");
					DatabaseUtils.loginchangeScene(event, "view/UserPage.fxml","Index Card",DatabaseUtils.Global.hold_username,DatabaseUtils.Global.hold_courses);

				}
		    }else if (options == 4) {
				psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
				psCheckUserExists.setString(1,username);
				resultSet = psCheckUserExists.executeQuery();
				
				if (resultSet.isBeforeFirst()) {
					
					PassUtil passUtil = new PassUtil();
					String passwordEncrypted = passUtil.encrypt(new_updated);
					
					psInsert = connection.prepareStatement("UPDATE users set password = '"+passwordEncrypted+"' where username = '"+username+"'");

					psInsert.executeUpdate();
					
					//changeScene(event,"view/UserPage.fxml","Welcome!", "Updated successful");
					DatabaseUtils.loginchangeScene(event, "view/UserPage.fxml","Index Card",DatabaseUtils.Global.hold_username,DatabaseUtils.Global.hold_courses);

				}
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
	 * Function allow user passing a old index cards and replace to the 
	 * new index card
	 * 
	 * @param  event, course, new_index, old_index
	 * @return nothing
	 */
	public static void updateIndexCard(ActionEvent event, String courses, String modify_index, String old_index) throws ClassNotFoundException {
		Connection connection = null;

		PreparedStatement psInsert = null;
		
		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;

		
		try {
			connection = dbConnection();

		    System.out.println("Opened database successfully");
			
			psCheckUserExists = connection.prepareStatement("SELECT * FROM allcourses WHERE courses = ? AND courses_holder = ?");
						
			psCheckUserExists.setString(1,courses);
			psCheckUserExists.setInt(2, Global.userid);
			
			resultSet = psCheckUserExists.executeQuery();
			

			if (resultSet.isBeforeFirst()) {
				System.out.println("courses found");
				
				psInsert = connection.prepareStatement("UPDATE "+courses+" set indexcard = '"+modify_index+"' where indexcard = '"+old_index+"' AND holder = '"+Global.userid+"'");
				psInsert.executeUpdate();
				
				loginchangeScene(event,"view/UserPage.fxml","Welcome!", "Updated cards successful",Global.hold_courses);

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
	 * Function allow user passing new index card and add into database
	 * function taking the variables
	 * 
	 * @param  event, course, new_index
	 * @return nothing
	 */
	public static void newIndexCard(ActionEvent event, String courses, String new_index) throws ClassNotFoundException {
		Connection connection = null;

		PreparedStatement psInsert = null;
		PreparedStatement psInsert1 = null;
		
		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;

		
		try {
			connection = dbConnection();

		    System.out.println("Opened database successfully");
			
			psCheckUserExists = connection.prepareStatement("SELECT * FROM allcourses WHERE courses = ? AND courses_holder = ?");
						
			psCheckUserExists.setString(1,courses);
			psCheckUserExists.setInt(2, Global.userid);
			
			
			resultSet = psCheckUserExists.executeQuery();
			

			if (resultSet.isBeforeFirst()) {
				System.out.println("courses found");
				
				psInsert = connection.prepareStatement("INSERT INTO "+courses+" (indexcard,holder) VALUES (?,?)");
				psInsert1 = connection.prepareStatement("INSERT INTO unlearnedcourses (courses,indexcards,holder) VALUES (?,?,?)");

				psInsert.setString(1, new_index);
				psInsert.setInt(2, Global.userid);
				
				psInsert1.setString(1, courses);
				psInsert1.setString(2,new_index);
				psInsert1.setInt(3, Global.userid);
				
				psInsert.executeUpdate();
				psInsert1.executeUpdate();
				
				loginchangeScene(event,"view/UserPage.fxml","Welcome!", "Add new index cards successful",Global.hold_courses);

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
	 * function allow user put the index card that wants to delete
	 * inside the job it's to open database and modify
	 * 
	 * @param  event, course, index card
	 * @return nothing
	 */
	public static void deleteIndexCard(ActionEvent event, String courses, String delete_index) throws ClassNotFoundException {
		Connection connection = null;

		PreparedStatement psInsert = null;
		
		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;

		
		try {
			connection = dbConnection();

		    System.out.println("Opened database successfully");
			
			psCheckUserExists = connection.prepareStatement("SELECT * FROM allcourses WHERE courses = ? AND courses_holder = ?");
						
			psCheckUserExists.setString(1,courses);
			psCheckUserExists.setInt(2, Global.userid);
			resultSet = psCheckUserExists.executeQuery();
			

			if (resultSet.isBeforeFirst()) {
				System.out.println("courses found");
				
				psInsert = connection.prepareStatement("DELETE FROM "+courses+" WHERE indexcard = '"+delete_index+"' AND holder = '"+Global.userid+"' ");
				
				psInsert.executeUpdate();
				
				loginchangeScene(event,"view/UserPage.fxml","Welcome!", "Delete index card successful",Global.hold_courses);

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
	 * The function took the course name and find in the database
	 * and return the ObservableList
	 * 
	 * @param  event, course, index card
	 * @return index cards as ObservableList type
	 */
	public static ObservableList getIndexCard(String course_name) {
		//System.out.println(course_name);
		ObservableList index_cardlist = FXCollections.observableArrayList();

		Connection connection = dbConnection();
		PreparedStatement psInsert = null;

		ResultSet resultSet = null;
		
		ResultSet tables = null;
		
		try {
		    DatabaseMetaData databaseMetaData = connection.getMetaData();
		    tables = databaseMetaData.getTables(null, null, course_name, null);

		    if (tables.next()) {
		    	System.out.println("Tables found");
		    	//psInsert = connection.prepareStatement("SELECT * FROM "+course_name+" ");
		    	psInsert = connection.prepareStatement("SELECT indexcard FROM '"+course_name+"' where holder = '"+Global.userid+"'");
				resultSet = psInsert.executeQuery();
				while (resultSet.next()) {
					index_cardlist.add(resultSet.getString(1));
				}

		    }else {
		    	System.out.println("we can't find tables");
		    }
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(connection != null) {
				try {
					connection.close();
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if(tables != null) {
				try {
					tables.close();
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
		}

		//System.out.println(index_cardlist);
		
		
		return index_cardlist;
	}
	
	
	/**
	 * The function took the course name and find in the database
	 * and return the ObservableList
	 * 
	 * @param  course, index card
	 * @return index cards as ObservableList type
	 */
	public static ObservableList getLearnedIndexCard(String course_name) {
		//System.out.println(course_name);
		ObservableList index_cardlist = FXCollections.observableArrayList();

		Connection connection = dbConnection();
		PreparedStatement psInsert = null;

		ResultSet resultSet = null;
				
		try {
			connection = dbConnection();
			//preparedStatement = connection.prepareStatement("SELECT * FROM learnedcourses WHERE courses = ?");
			psInsert = connection.prepareStatement("SELECT DISTINCT indexcards FROM learnedcourses WHERE courses = '"+course_name+"' AND holder = '"+Global.userid+"'");
			resultSet = psInsert.executeQuery();
			while(resultSet.next()) {
				//temp_learned.learned_indexs.add(resultSet.getString(1));
				index_cardlist.add(resultSet.getString(1));
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(connection != null) {
				try {
					connection.close();
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if(resultSet != null) {
				try {
					resultSet.close();
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if(psInsert != null) {
				try {
					psInsert.close();
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}

			
		}

		//System.out.println(index_cardlist);
		
		
		return index_cardlist;
	}
	
	
	/**
	 * The function took the course name and find in the database
	 * and return the ObservableList
	 * 
	 * @param  course, index card
	 * @return index cards as ObservableList type
	 */
	public static ObservableList getunLearnedIndexCard(String course_name) {
		//System.out.println(course_name);
		ObservableList index_cardlist = FXCollections.observableArrayList();

		Connection connection = dbConnection();
		PreparedStatement psInsert = null;

		ResultSet resultSet = null;
				
		try {
			connection = dbConnection();
			//preparedStatement = connection.prepareStatement("SELECT * FROM learnedcourses WHERE courses = ?");
			psInsert = connection.prepareStatement("SELECT DISTINCT indexcards FROM unlearnedcourses WHERE courses = '"+course_name+"' AND holder = '"+Global.userid+"'");
			resultSet = psInsert.executeQuery();
			while(resultSet.next()) {
				//temp_learned.learned_indexs.add(resultSet.getString(1));
				index_cardlist.add(resultSet.getString(1));
			}
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(connection != null) {
				try {
					connection.close();
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if(resultSet != null) {
				try {
					resultSet.close();
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if(psInsert != null) {
				try {
					psInsert.close();
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}

			
		}

		//System.out.println(index_cardlist);
		
		
		return index_cardlist;
	}
	
	/**
	 * The function recvied coursename and index cards
	 * and return the ObservableList
	 * 
	 * @param  course, index card
	 * @return None, nothing
	 */
	public static void saveLearnedIndexCard(String courses, String index_cards){
		Connection connection = null;

		PreparedStatement psInsert = null;
		PreparedStatement psInsert1 = null;

		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;	
		
		try {
			connection = dbConnection();
			System.out.println("open learned index card database!");
			psCheckUserExists = connection.prepareStatement("SELECT * FROM learnedcourses WHERE courses = ? AND indexcards = ? AND holder = ?");
			psCheckUserExists.setString(1,courses);
			psCheckUserExists.setString(2,index_cards);
			psCheckUserExists.setInt(3, Global.userid);
			
			resultSet = psCheckUserExists.executeQuery();
			
			if (resultSet.isBeforeFirst()) {
				System.out.println("It already save in database");
			}else {
				psInsert = connection.prepareStatement("INSERT INTO learnedcourses (courses,indexcards,holder) VALUES (?,?,?)");
				psInsert.setString(1, courses);
				psInsert.setString(2, index_cards);
				psInsert.setInt(3, Global.userid);

				psInsert.executeUpdate();
				
				connection.close();
				connection = dbConnection();
				psInsert1 = connection.prepareStatement("DELETE FROM unlearnedcourses WHERE indexcards = '"+index_cards+"' AND holder = '"+Global.userid+"' ");
				
				psInsert1.executeUpdate();
				
				
			}
		}catch(SQLException e) {
			
		}finally {
			if (connection != null) {
				try {
					connection.close();
				} catch ( SQLException e) {
					e.printStackTrace();
				}
			}
			if (psInsert != null) {
				try {
					psInsert.close();
				} catch ( SQLException e) {
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
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch ( SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
	
	/**
	 * The function no paramenter receviced 
	 * no return, the function purpose open the
	 * database, and stored the learned index cards
	 * 
	 * @param  course, index card
	 * @return None, nothing
	 */
	public static void load_learned_index_cards() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet =null;
		int i = 0;
		try {
			connection = dbConnection();
			//preparedStatement = connection.prepareStatement("SELECT * FROM learnedcourses WHERE courses = ?");
			preparedStatement = connection.prepareStatement("SELECT courses FROM learnedcourses WHERE holder = '"+Global.userid+"'");

			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				//temp_learned.learned_courses.add(resultSet.getString(1));
				if (!temp_learned.learned_courses.contains(resultSet.getString(1))) {
					temp_learned.learned_courses.add(resultSet.getString(1));

				}
			}
			
			System.out.println(temp_learned.learned_courses);
			//System.out.println("zhe li");
		}catch(SQLException e) {
			
		}finally {
			if(resultSet != null) {
				try {
					resultSet.close();
					
				}catch(SQLException e) {
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
	 * The function no paramenter receviced 
	 * no return, the function purpose open the
	 * database, and stored the learned index cards
	 * 
	 * @param  course, index card
	 * @return None, nothing
	 */
	public static void load_unlearned_index_cards() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet =null;
		int i = 0;
		try {
			connection = dbConnection();
			//preparedStatement = connection.prepareStatement("SELECT * FROM learnedcourses WHERE courses = ?");
			preparedStatement = connection.prepareStatement("SELECT courses FROM unlearnedcourses WHERE holder = '"+Global.userid+"'");

			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				//temp_learned.learned_courses.add(resultSet.getString(1));
				if (!Global.unlearned_courses.contains(resultSet.getString(1))) {
					Global.unlearned_courses.add(resultSet.getString(1));

				}
			}
			
			System.out.println(Global.unlearned_courses);
			//System.out.println("zhe li");
		}catch(SQLException e) {
			
		}finally {
			if(resultSet != null) {
				try {
					resultSet.close();
					
				}catch(SQLException e) {
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

		PreparedStatement psInsert = null;
		PreparedStatement create = null;

		PreparedStatement psCheckUserExists = null;
		ResultSet resultSet = null;
		
		try {
			connection = dbConnection();
			//psCheckUserExists = connection.prepareStatement("SELECT users.username FROM users INNER JOIN coursesname2 on userid = courses_holder where courses_holder =3 ");
			//psCheckUserExists = connection.prepareStatement("SELECT courses FROM coursesname2 where courses_holder = 5");
			//psCheckUserExists = connection.prepareStatement("SELECT users.username FROM users INNER JOIN cs1512 on userid = holder where holder = 5 ");
			psCheckUserExists = connection.prepareStatement("SELECT indexcard FROM cs1513 where holder = 1");
			//psCheckUserExists = connection.prepareStatement("CREATE TABLE cs888 AS SELECT * FROM cs999");
			resultSet = psCheckUserExists.executeQuery();
			//create = connection.prepareStatement("CREATE TABLE cs888 AS SELECT * FROM cs999");
			//create.executeUpdate();
//			while(resultSet.next()) {
//				//System.out.println(resultSet.getString(1));
//			}
			
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
			if (create != null) {
				try {
					create.close();
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
}

# ***Index Card Manager***
![Bitbucket open issues](https://img.shields.io/bitbucket/issues/xiyuanzhou/fullstack_selflearning) ![GitHub repo size](https://img.shields.io/github/repo-size/xiyuanzhou/fullstack_selflearning) ![GitHub last commit](https://img.shields.io/github/last-commit/xiyuanzhou/fullstack_selflearning)
> `Team-03-04 members: XiyuanZhou, HonghaoMa, MengzhenZhao, ZachWoo`
 
- Project version 0.5 completed
    - Login ✅
    - Signup ✅
    - Delete/Update Course ✅
    - Reset password ✅
***
> (MacOS Recommand)

### __Github (simple)Guideline__ 🐒
> go to terminal
```git
    git clone https://github.com/xiyuanzhou/team-03-04.git
    git checkout -b (Your name branch)
```
> Everytime update/edit some files check first
> (git status allow you to see what files have been modified)
```git
    git status
    ------------------------
    git add .               -> add into ready to commit position
    git status              -> check one more time if it modified files have been add
    ------------------------
    git commit -m 'updated' -> another word comment what have you done
    ------------------------
    git push                -> push all the updated to GitHub
``` 
> Updated new version code from Github
```git
    git switch main         -> swicth to the main branch first
    git pull                -> pull/download merge all the new files into main branch 
    ------------------------
    git merge (Your name branch)   -> merge/updated your own branch
    git switch (Your name branch)  -> Now go back to your own branch, al the code should be updated
```
### __JavaFx SetUp - Eclipse__ 👽
> MacOS version recommand

> javafx jar download [JavaFx.jar](https://gluonhq.com/products/javafx/)

> MacOS eclipse -> Preferences -> java -> Build Path -> User Libaries: -> New -> (name a libary) -> Add External JARs

> Run Configurations -> VM arguments -> --module-path (your jar local files path) --add-modules javafx.controls,javafx.fxml

### __Sqlite Database SetUp__ 👽
> Download [sqlite Database app](https://sqlitebrowser.org/) for Mac

> Before access into db.splite, [sqlite-jdbc.jar](https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc) need to be download

> Path for sqlite-jdbc -> Right click Main.java -> Build Path -> Configure Build Path -> Libaries -> classpath -> Add External JARs (sqlite-jdbc)
```java
//all the files should be inside the src path or it mean same folder
/**
each time code access into the Database need to include this two line code
*/
    Class.forName("org.sqlite.JDBC");
    connection = DriverManager.getConnection("jdbc:sqlite:UserDb.sqlite");
```
### __FXML files - build by SceneBuilder__ 👽
> SceneBuilder download from website https://gluonhq.com/products/scene-builder/

> SetUp SceneBuilder path -> Preference -> JavaFx -> SceneBuilder executable -> Browse(your scenebuilder app)

### __JavaFx Project path tree__ 👍
```bash
team-03-04
├── src
│   ├── application
│       └── Main.java (always run main)
│       └── DatabaseUtils.java (only deal with database, access and edited)
│   ├── controller (controller section only dealing with income, such as button, textfield,etc...)
│       └── LoginPageControl.java
│       └── ...Control.java
│       └── ...Control.java
│       └── .
│       └── .
├── resource
│       ├── css
│       ├── images
│       └── view (FXML files)
│           └── .
│           └── .
│           └── .
│           └── .
├── README.md
├── UserDb.sqlite
└── sqlite-jdbc-3.7.2.jar
```
### __Project Concept n/ logic__ 🌈 
* Main.java applicatio only the way running/debug (linked)-> view/HomePage.fxml (files) 🔴 
```java
    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("view/HomePage.fxml"));
``` 
* FXML edited by (SceneBuilder) 🔴 
    - while editing the UI/view looks the button, textfield need to be have ${\color{red}fx:id}$ name. 
    - The ${\color{red}fx:id}$ name is the way connect to the button or textfield and doing action.
    - Also, the fxml files need to have controller class to receive the button and edited the action of the button.
    - ${\color{green}Example}$: login.fxml need to connected -> logincontroller.java -> ${\color{red}fx:id=loginbutton}$ -> below code shown: (logincontroller.java pages)
    ```java
        @FXML
        private Button loginbutton;
        @Override
	    public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
    	    loginbutton.setOnMouseClicked(e -> {
			    try {
				    SceneChangingUtils.changeScene(e, "Index Card Manager", "view/logincontroller.fxml");
			    } catch (IOException e1) {
				    e1.printStackTrace();
			    }
		    });
        }
    ```
    - The way connect to the (FXML files) -> fxml controller
    ```java
        fx:controller="controller.LoginController"
    ```
> FXML files -> Controller java class -> doing action

* Controller doing action by generate new java class for only doing button or textfield action 🔴 
    - What the teams does was have two java class by doing control the action ${\color{blue}SceneChangeUtils.java}$ and ${\color{blue}DatabaseUtils.java}$.
        - ${\color{blue}SceneChangeUtils.java}$ only control the pages that not relate to the Datebase.
        - ${\color{blue}DatabaseUtils.java}$ only control the pages or buttons that relate/or need access to the Database
        > Actually, using either one it doesn't matter, it just better orgization.
    - Please check out this two Utils java class their job only do action -> jump to different window page or edit/delete/updated information from the pages shown. ${\color{blue}(controller.java(information/input)-> Call function -> Utils.java -> doing action/updated/delete... -> NewPageWindows)}$

* Getting information from User/UI pages 🔴 
    - Assume I have a TextField ${\color{red}fx:id=tf-coursename}$
    ```java
        /**
        Here is the way to get the text information from the user
        */
        tf_coursename.getText()
    ```
* Splite Database (simple) 🔴 
    - > How read/find data
    ```java
        //accessing into users table -> username variables 
        psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");

        //insert the new users into database
        psInsert = connection.prepareStatement("INSERT INTO users (username, password, email) VALUES (?,?,?)");
    ```
>*Version 0.5 stopping here* ⛽






# ***Team4***
![Bitbucket open issues](https://img.shields.io/bitbucket/issues/xiyuanzhou/fullstack_selflearning) ![GitHub repo size](https://img.shields.io/github/repo-size/xiyuanzhou/fullstack_selflearning) ![GitHub last commit](https://img.shields.io/github/last-commit/xiyuanzhou/fullstack_selflearning)
> `Team members: XiyuanZhou, HonghaoMa, MengzhenZhao, ZachWoo` 
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





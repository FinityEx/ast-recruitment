# ast-recruitment
Demo project made in pure Java 11 and React.


## Requirements to compile and boot:

-Properly set ```JAVA_HOME``` in Windows' path

-Installed ```npm``` and ```node.js``` for front-end made in react



## Instructions on Windows:

Start ```ast-task-launch.bat``` script. It's going to automatically start maven's wrapper which is then going to download proper maven version,
```clean install``` the backend app (as well as run tests, results are logged in console), launch the created .jar file, 
then move into _ast-recruitment-fe_ directory containing the react app, where it then executes ```npm install``` and ```npm start```. 

Two views can be accessed by clicking either the _Home_ or _Admin_ buttons at the top. 
Java backend is set to run at ```8080``` port. Endpoints are listed in Main class, possible methods listed in respective Handler classes.

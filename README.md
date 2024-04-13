<h1> Health Monitoring App for Java Sprint Semester 3</h1>

<h2>User Documentation</h2>

<hr><br>
<p>This Program is a Health Monitoring App for Users/Doctors to view/add/update/delete Medicine Reminders, Health Data, Medical Records.<br>
It allows a user to login and check their medical information, while also allowing a doctor to log in to check their patients and add/view data for them. It's purpose is to keep track of data and to help patients stay on top of their <br>
fitness goals, while providing health care professionals insight into their patients health.</p>

<br> 



<h3>User Class</h3>


<br>

<p> The User class is used to identify the Patient based on their Id, Name, Email, Password and to see if they're a doctor, it has a connection to the User Dao Class and Doctor Class.</p>




<h3>UserDao</h3>


<br>

<p>The UserDao class is responsible for generating, deleting, fetching, verifying or updating a user. It inherits the User Class</p>





<h3>HealthDataDao</h3>


<br>
<p>The HealthDataDao class is responsible for receiving, updating and deleting the HealthData that it inherits from the HealthData Class</p>




<h3>Health Data Class</h3>


<br>
<p>The HealthData Class gets all the data needed for the User using, getters and setters. It has a relation to the Recommendation system and HealthDataDao</p>


<h3>Recommendation System</h3>


<br>
<p>The Recommendation System essentially is used to compare the Users healthdata with set data, to generate recommendations based on the comparable data. It also allows for generation, additions and updates to the Recommendations. It inherits the HealthData Class</p>

<h3>Doctor</h3>


<br>
<p>The Doctor Class is an extension of the User Class with two additional attributes with getters and setters for them, it has a relation to both User and DoctorPortalDao</p>

<h3>DoctorPortalDao</h3>

<p>The DoctorPortalDao is a class that is used by the doctors, to get ids, add reminders, remove patients, update patients. It inherits, HealthDataDao, userDao and Doctor.</p>

<h3>MedicineReminder Manager</h3>


<br>
<p>MedicineReminder Manager is a class that inherits the Medicine data from Medicine Reminder, it is used to generate and fetch, reminders and due reminders, aswell as update and delete. </p>

<h3>Medicine Reminder</h3>

<br>


<p>Medicine Reminder inherits data from the User Class and is used to get the Medicine a user takes based on dose, name, schedule its taken, start and end date. </p>

<hr>
<br>

<h2>How to access class Diagram</h2>

<p> Click Code button, clone it to your desktop and you'll have access to the files, Class Diagram is labeled as ClassDiagram.uxf, it requires a **VSCODE** Extension called **UMLet**.</p>


<hr>
<hr>

<h2>Development Documentation</h2>

<p>All documentation in relation to the development is within the docs folder inside the repository.<br>

<h2>Source code Structure:</h2>

. <br>
├── .gitattributes <br>
├── classdiagram.uxf <br>
├── DatabaseConnection.java <br>
├── Doctor.java <br>
├── DoctorPortalDao.java <br>
├── HealthData.java <br>
├── HealthDataDao.java <br>
├── HealthMonitoringApp.java <br>
├── MedicineReminder.java <br>
├── MedicineReminderManager.java <br>
├── RecommendationSystem.java <br>
├── User.java<br>
├── UserDao.java<br>
├── docs/<br>
│   ├── allclasses-index.html<br>
│   ├── allpackages-index.html<br>
│   ├── DatabaseConnection.html<br>
│   ├── Doctor.html<br>
│   ├── DoctorPortalDao.html<br>
│   ├── element-list<br>
│   ├── HealthData.html<br>
│   ├── HealthDataDao.html<br>
│   ├── HealthMonitoringApp.html<br>
│   ├── help-doc.html<br>
│   ├── index-all.html<br>
│   ├── index.html<br>
│   └── ...
├── lib/<br>
└── script/<br>

<h2> Setting up a database </h2>

<br>

<p> Inside of the Source code files, there is a script folder that has all of the create tables to allow you to create your own database. The primary language used for the database in this program is SQL, so you'll need the latest version of postgres.</p>
</p>

<hr>

<h2>Deployment Docs</h2>

<br>

<h2> How to install and run</h2>

<p> - Clone the repository 
<br>- Open the root folder in an IDE
<br>- Run a compiler, in terminal type: javac -cp "lib/*" HealthMonitoringApp.java 
<br>- The program will compile the necesarry libraries and the main class afterwards you will be able to run and debug the program.<p>

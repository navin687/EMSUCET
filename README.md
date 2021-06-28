# EMSUCET

Android application on Employee Management system
A Project Report





By

  Ravi Kumar(IT)
       Navin Kumar Das(IT)
  Ajay Murmu(IT)
Anurag Kumar(IT)


MAY 2017

SUBMITTED AS REPORT OF FINAL YEAR PROJECT

Stream
Information Technology




 

University College of Engineering and Technology
V.B.U Hazaribagh



BONAFIDE CERTIFICATE


This is to certify that this project report entitled “ANDROID APPLICATION  ON EMPLOYEE MANAGEMENT SYSTEM”, submitted on May 11th,2018 by the following members of the group in partial fulfilment for the award of B.Tech degree in Information Technology,is a record of the work carried out by them under my guidance and supervision.



Name of the student: ANURAG KUMAR			Signature:
Name of the student: RAVI KUMAR				Signature:
Name of the student: NAVIN KUMAR DAS		           Signature:
Name of the student:AJAY MURMU			           Signature:



Assistant professer AMIT  KUMAR
PROJECT MENTOR




SIGNATURE












ACKNOWLEDGEMENT

We have taken efforts in this project. However, it would not have been possible without the kind support and help of many individual. We would like to extend my sincere thanks to all of them.

We would like to take the opportunity to express my humble gratitude to Assistant 
Professer Amit Kumar under whom we executed this project. His constant guidance and willingness to share his vast knowledge made me understand this project and its manifestations in great depths and helped me to complete the project.


We would like to thank all faculty members and staff of the Department of Information Technology, UCET for their generous help in various ways for the completion of this project.


Finally, yet importantly, we would like to express my heartfelt thanks to my beloved parents for their blessings, my friends and classmates for their help and wishes for the successful completion of this project.















Table of Contents	Page No
1.	Abstract	05
2.	Introduction and Objectives of the Project	06
3.	Tools/Platform, Hardware and Software Requirement specifications	07
4.	Design	08
5.	Data Model	09-10
6.	Functional Requirements	10-11
7.	Non-functional Requirements	12
8.	Coding	
9.	Screenshot	
10.	Future scope	
11.	Conclusion	
12.	References	















1.	Abstract
The main focus of this paper is providing an idea of employee management system by using Android application development. This is the achievement of our project.
It is the programming based Android application which control the management of employee working system. The whole data and resources were saved in the console of “Firebase”. There is an Admin  which act as a manager for the employee system, who can access the entire data and information about the employee.
Using the “Firebase Console” user can easily manage the data and information as an employee or an admin from anywhere or any device.

	Android application on Employee Management System includes the following:
	 1.  Login Page: In login page the user log in using his Username and Password.	
	2.  In case of new user click on “Register” through admin mobile















2.	 Introduction and Objectives of the Project

•	Android application on Employee Management System is basically used by manager.
•	This Android application helps for keeping records of employees.
•	The “EMPLOYEE MANAGEMENT SYSTEM” has been developed to override the problems prevailing in the practicing manual system. This software is supported to eliminate and in some cases reduce the hardships faced by this existing system.
Moreover this system is designed for the particular need of the company to carry out operations in a smooth and effective manner.
The application is reduced as much as possible to avoid errors while entering the data.
It also provides error message while entering invalid data.
No formal knowledge is needed for the user to use this system. Thus by this all it proves it is user friendly.













3.	Tools/Platform, Hardware and Software Requirement specifications.

Tools
    1 android studio version 3.1
    2 SQlite database
    3 Firebase connectivity (Authentication & Realtime Database)


Platform
1.	Android
Hardware Requirement Specification
•	4GB RAM minimum, 8GB RAM recommended
•	2GB of available disk space minimum , 4 GB Recommended (500 MB for IDE + 1.5 GB for android SDK and emulator system image)
•	1280 X 800 minimum screen resolution
•	For accelerated emulator : 64- bit operating  system and Intel  processor with support for Intel VT-x, Intel EM64T (Intel 64), and Execute Disable (XD) Bit functionality

Software Requirement Specification
•	Android studio 
•	Android sdk
•	Jdk 8
•	SQLite database
Language Requirement

•	JAVA
•	XML






4. Data Model
ER Diagram
The entity-relationship diagram is a data modeling technique that graphically represents an information systems entities and the relationships between those entities. An ER diagram is a conceptual and representational model of data which is used to represent the system framework infrastructure
The ER diagram contains following elements :
•	Entities
•	Relationship
•	Attributes


  
   6. Design

The design phase emphasizes on the transformation of customer requirements as defined in the SRS document, into a form that is suitable for coding.

The design phase can be broadly classified in two levels

ˆ	Preliminary or high level design
ˆ	
ˆ	Detailed design

       The preliminary design can be further divided into two sub categories

ˆ	Function Oriented Software Design

ˆ	Object Oriented Software Design

ˆ	



Function Oriented Software Design

This design model can be represented by drawing the DFDs (Data Flow Diagrams) for the given SRS document.

A data flow diagram is a graphical representation of the data flow through an information system which is used to model the process aspects of the system. DFD is the preliminary step used to create an overview of the system. DFD is used for structured design.The context-level DFD is then exploded to produce a Level 1 DFD which models the details of the system. The Level 1 DFD shows how the system is divided into sub-systems (processes), and how each processes deals with one or more of the data flows to or from an external entity, and how the processes together provide all of the functionality of the system. The level 1 DFD also identifies the internal data stores which must be there for the system to do its job, and shows the data flow between the various parts of the system.
In the below Level 1 DFD , the attendance system has been decomposed further into 3 processes which are namely sign up, sign in, admin. Each processes is accessed by the admin.
 
                                             Level 1 DFD diagram




  7.UML Modeling

UML, as the name shows, is a modeling language. It is used to specify, draw, visualize and document the parts of the software.

It provides a set of notations (such as rectangles, ellipses, lines etc.) to create the visual model of a system. This phase is used to design different UML diagrams corresponding to the application development.

Use Case Diagram

Use case diagrams are the diagrammatic representation depicting employee interactions with the system. This diagram shows different types of employee and various ways in which these employee interact with the system.

 Show the use case diagram for an Employer. It shows all the different possible ways in which an Employer can use the management system. Manager can use the system through their mobile phones to take the employee details. The Manager after logging into the application can view the employee details. Then he can view the list of enrolled employee in a particular department and can take employee details department wise. The employee can also view own register details at later stage. Manager can directly upload the employee details in the firebase console through his mobile phone.

The employee login leads to all the options that can be performed by the manager. Its basic function is to view the details taken by the manager. Then the manager can perform different functions. He can view the list of the employee. Then he can either view the employee details or can upload the details directly in the server





 
 
	












 


 


 




















13.	 Future scope

•	It may help collecting perfect management in details.In a very short time ,the collecying will be obvious,simple and sensible.
•	It will help a person to Know the management of passed year perfectly and vividly.
•	It also help-s current all works relative to employee management system.
•	It will be also reduced the cost of collecting the management and collecting procedure will go on smoothly.
•	We can add printer in future.
•	We will give more advance software for employee management system including more facilities.
•	We will host the platform on online servers to make user can sign up from anywhere.
•	Create the master and slave database structure to reduce the overload of the database queries.
•	Implemented the backup mechanism for taking backup of codebase on regular basis on different servers.
•	Employee Recuitment Management.


























15. Conclusion



•	The objective of the software planning is to provide a frame work that enables the manager to make reasonable estimates made within a limited time at the beginning of the  software project and should be updated regularly as the project progresses.
•	Several user friendly coding have also adapted.


























16. Reference


•	https://www.google.co.in Google for problem solving.
•	https://console.firebase.google.com firebase console for project development.
•	https://developer.android.com for android component knowledge.
•	https://docs.oracle.com/javase/7/docs/api for java component knowledge.
•	https://in.udacity.com/course/android for android learing course.


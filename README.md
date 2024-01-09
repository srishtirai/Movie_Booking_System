# Final Project for CSYE6220 - Enterprise Software Design
> Northeastern University, College of Engineering

## Professor: Yusuf Ozbek

## Movie Booking System
The Movie Ticket Booking System is designed to cater to a diverse set of users which includes
the consumers who want to browse through movies, check ratings book tickets etc. and
administrators and staff who manage the movie listings, theaters, and bookings. The primary
consumers are people who want a convenient platform for exploring available movies and
checking showtimes and ratings. These users can register for an account to access personalized
features like ticket booking, booking history and email notifications.
For consumers, the journey begins with exploring the platform's movie listings. They can browse
through a variety of movies, each accompanied by detailed information. Registered users can
manage their profiles, track past and upcoming bookings, and receive timely notifications. On
the other side, administrators oversee the entire movie management system. They have the
authority to add, update, or remove movie listings, theatres, and showtimes. While staff will have
restricted access, they will not be able to remove movie listings or showtimes, etc.

## Table of Contents

1. [Installation](#installation)
2. [Usage](#usage)
3. [Database Configuration](#database-configuration)
4. [Notable Design points](#notable-design-points)
5. [Database Schema](#database-schema)

## Installation

Software Installaion Prerequisites:

1. Java Development Kit (JDK)
2. Spring Tool Suite (STS)
3. Build Tool - Maven

Steps to run the project:

1. Clone the repository:

	```bash
	git clone https://github.com/srishtirai/Movie_Booking_System
	cd Movie_Booking_System
	 ```
2. Import the Project:

	1. Open STS.
	2. Go to File > Import....
	3. Choose Existing Maven Projects
	4. Navigate to the location where you saved your Spring Boot project and select the project directory.
	5. Click Finish to import the project.

3. Build the Project:

	Once the project is imported, STS will automatically start building it. You can check the progress in the console at the bottom.

4. Configure the Application Properties:

	Open the application.properties and configure database connection details.

5. Run the Application:

	Right-click on the main class file (CourseRegistrationApplication) in the src/main/java directory.
	Select Run As > Spring Boot App.

Once the application has started, you can access it by opening a web browser and navigating to http://localhost:8080

## Usage

Access the application in your web browser: [http://localhost:8080](http://localhost:8080/esdFinalProject/)

Roles:

  Guest
  	View movies, theatres and showtimes
  User
  	View movies, theatres and showtimes
  	Edit profile
  	Book tickets
  Staff
  	View movies, theatres and showtimes
  	Add, edit and delete movies, theatres and showtimes
  	View users and delete them
  Admin
  	All the abilities of the staff
  	Approval for staff account

Features:

  Login 
  	Login with either email or password
  	Staff account needs to be approved before login
  	Check for login errors -> User not found / Wrong password
  	
  Sign up
  	Input validation for all fields
  	Check if the Username/email already exists in the system
  
  Profile
  	Edit the username and phone number
  	Change password -> Checks are done for the Current password match
  
  Movies
  	View a list of movies
  	Search based on name, genre or both
  	List showtimes for a particular movie
  	Book tickets in case of user
  		Handles scenarios where capacity is filled
  	Add, edit and delete movies
  	Add, edit and delete showtimes
  	
  Theatres
  	View a list of theatres
  	Search based on theatre name or movie name
  	List showtimes for a particular theatre
  	Book tickets in case of user
  		Handles scenarios where capacity is filled
  	Add, edit and delete theatres
  	Add, edit and delete showtimes
  
  Booking History
  	Users can view all their previous booking details
  
  User management
  	Staff can view and delete users
  	Admin can view and delete users and staff
  	Admin can approve staff accounts
  
  Movie Booking
  	Payment interface handled via Stripe
  	Error cases handled in case of failure
  
  Email Services
  	Email sent when new staff sign-up is requested and when it is approved
  	Email sent to the user when Movie booking is completed
  
  Security
  	Password encoding handled
  	URL access based on roles
  	Input validations done for all form inputs
   

## Database Configuration

The application is configured to use MySQL as the database.
Update the `application.properties` file with your MySQL database configuration:
`src/main/resources/application.properties`
```
spring.datasource.url=jdbc:mysql://localhost:3306/final_project_esd
spring.datasource.username=[Username]
spring.datasource.password=[Password]
spring.jpa.hibernate.ddl-auto=update
```
 Entities are designed to represent real-world objects, and relationships are modelled to reflect the business logic.
 
 ## Notable Design points
 
1. GUI - We have used **Thymleaf** templates to create interactive pages.
2. Database - **MySQL** has been used to store the data for the application.
3. ORM - **Hibernate** has been used to maintain data persistence.
4. Framework  - **Spring boot** has been used to create a stand-alone application.
5. Security - **Spring Security** has been implemented to ensure session management and role-based authentication & authorization
 
 ## Database Schema

Create a new schema named course_registration_db in MySql and the tables are created automatically based on the JPA entity definitions.
![Screenshot 2023-12-29 223702](https://github.com/srishtirai/Movie_Booking_System/assets/44725079/a00ebaa2-b106-4af1-990c-6fead2895552)


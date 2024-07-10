Banking Application

This is a simple banking application developed in Java using Swing for the user interface and MySQL for the database. The application allows users to register, log in, check their balance, deposit money, withdraw money, and view their transaction history.


Table of Contents

1.	General Description
2.	Features
3.	Architecture
4.	Technical Requirements
5.	Usage
6.	Future Improvements
7.	Contributing
8.	License


General Description


The Banking Application is designed to simulate basic banking operations such as registration, login, balance inquiry, deposit, withdrawal, and transaction history management. It uses a MySQL database to store user data and transaction records.
Features
•	User Registration
•	User Login
•	Check Balance
•	Deposit Money
•	Withdraw Money
•	View Transaction History
•	User-friendly Interface


Architecture


The application is divided into several components:
1.	Start: The application begins execution in the main method and initializes the Login frame.
2.	Database Connectivity: Establishes a connection to the MySQL database using JDBC.
3.	First Frame:
	Register: New users can create an account.
	Login: Existing users can log in.
4.	Main Frame: Displays the user's account information and options for various operations.
5.	Operations:
	Deposit: Users can deposit money into their account.
	Withdrawal: Users can withdraw money from their account.
	Check Balance: Users can view their current balance.
	Transaction History: Users can view their transaction history.
6.	Automatic Updation in Database: Updates balance and transaction records after each transaction.
7.	Close Application: Handles application closure with user confirmation.
8.	Program Execution Completed: Terminates the program execution.

Usage

1.	Register: Open the application and click "Register" to create a new account.
2.	Login: Enter your username and password to log in.
3.	Main Menu: Access various banking operations such as checking balance, depositing money, withdrawing money, and viewing transaction history.
4.	Close Application: Click "Close" to exit the application.

Future Improvements

•	Enhance security by hashing passwords.
•	Implement account recovery options.
•	Add support for multiple account types.
•	Improve the user interface design.
•	Integrate with external payment gateways.

Contributing

Contributions are welcome! Please feel free to submit a pull request or open an issue to suggest improvements or report bugs.

License

This project is licensed under the MIT License. 


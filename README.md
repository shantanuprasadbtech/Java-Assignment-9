# Java-Assignment-9

This program uses the MySQL database to store menu items. The following methods are implemented to manage the database:

createDatabase(String username, String password)

This method creates a new database named "platemate" if it does not already exist. It takes the username and password for the MySQL server as input. It uses the java.sql.Connection and java.sql.Statement classes to create a connection to the MySQL server and execute SQL commands.

createTable(String username, String password)

This method creates a new table named "menucard" in the "platemate" database if it does not already exist. The table has columns for item ID, name, price, description, and availability status. It takes the username and password for the MySQL server as input.

insertRecord(String username, String password, String name, double price, String description, int statusInt)

This method inserts a new menu item into the "menucard" table. It takes the username and password for the MySQL server, as well as the name, price, description, and availability status of the menu item as input. 

deleteRecord(String username, String password, int id)

This method deletes a menu item with the given ID from the "menucard" table. It takes the username and password for the MySQL server, as well as the ID of the menu item to delete as input. 

updateRecord(String username, String password, int id, String name, double price, String description, int statusInt)

This method updates the details of a menu item with the given ID in the "menucard" table. It takes the username and password for the MySQL server, as well as the ID, name, price, description, and availability status of the menu item as input. 

import java.io.*;
import java.util.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// Class definition
public class DatabaseManager {
    // Initializing input streams
    static Scanner sc = new Scanner(System.in);
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
        try {
            // Get MySQL server username and password from user input
            System.out.println("Enter your MySQL server username: ");
            String username = sc.nextLine();
            System.out.println("Enter your MySQL server password: ");
            String password = sc.nextLine();

            // Create database and table
            DatabaseManager dm = new DatabaseManager();
            dm.createDatabase(username, password);
            dm.createTable(username, password);

            // Perform menu item management operations
            boolean quit = false;
            do {
                System.out.println("\n1) Add menu item");
                System.out.println("2) Delete menu item");
                System.out.println("3) Update menu item");
                System.out.println("4) Quit");
                System.out.println("Enter which task you want: ");
                int choice = sc.nextInt();

                switch (choice) {
                    case 1:
                        // Get menu item details from user and insert into table
                        System.out.println("Enter item name: ");
                        String name = br.readLine();
                        System.out.println("Enter item price: ");
                        double price = Double.parseDouble(br.readLine());
                        System.out.println("Enter item description: ");
                        String description = br.readLine();
                        System.out.println("Enter item availability status (True or False): ");
                        boolean status = Boolean.parseBoolean(br.readLine());
                        int statusInt;
                        if (status) {
                            statusInt = 1;
                        } else {
                            statusInt = 0;
                        }
                        dm.insertRecord(username, password, name, price, description, statusInt);
                        System.out.println("Menu item successfully added. ");
                        break;
                    case 2:
                        // Get ID of menu item to delete from user and delete it from table
                        System.out.println("Enter id of item to delete: ");
                        int id = Integer.parseInt(br.readLine());
                        dm.deleteRecord(username, password, id);
                        System.out.println("Menu item successfully deleted ");
                        break;
                    case 3:
                        // Get ID and updated details of menu item from user and update it in table
                        System.out.println("Enter id of item to update: ");
                        id = Integer.parseInt(br.readLine());
                        System.out.println("Enter new item name: ");
                        name = br.readLine();
                        System.out.println("Enter new item price: ");
                        price = Double.parseDouble(br.readLine());
                        System.out.println("Enter new item description: ");
                        description = br.readLine();
                        System.out.println("Enter new item availability status (True or False): ");
                        status = Boolean.parseBoolean(br.readLine());
                        statusInt = status ? 1 : 0;
                        dm.updateRecord(username, password, id, name, price, description, statusInt);
                        System.out.println("Menu item successfully updated. ");
                        break;
                    case 4:
                        // Quit the program
                        quit = true;
                        break;
                }
            } while (!quit);
        }
        catch (InputMismatchException | IOException e) {
            // Handle invalid input exceptions
            System.out.println("Invalid input");
        }

    }

    // Method to create database
    public static void createDatabase(String username, String password) {
        try (java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/", username, password);
             Statement stmt = conn.createStatement()) {

            String CreateSql = "CREATE DATABASE IF NOT EXISTS platemate";
            stmt.executeUpdate(CreateSql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    // Method to create table
    public static void createTable(String username, String password){
        try (java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/platemate", username, password);
             Statement stmt = conn.createStatement()) {
            String createTableSql = "CREATE TABLE IF NOT EXISTS menucard "
                    + "(id int primary key auto_increment,"
                    + " name varchar(20) NOT NULL,"
                    + " price numeric NOT NULL,"
                    + " description varchar(100) NOT NULL,"
                    + " status boolean)";

            stmt.executeUpdate(createTableSql);
            System.out.print("Successfully created database and table for the menu.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    // Method to insert records into the table
    public static void insertRecord(String username, String password, String name, double price, String description, int statusInt) {
        try (java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/platemate",username, password);
             Statement stmt = conn.createStatement()) {

            // Insert values entered by user into the table
            String insertSql = "INSERT INTO menucard (name, price, description, status) " +
                    "VALUES ('" + name + "', " + price + ", '" + description + "', '" + statusInt + "')";
            stmt.executeUpdate(insertSql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to delete records
    public static void deleteRecord(String username, String password, int id){
        try (java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/platemate",username, password);
             Statement stmt = conn.createStatement()) {

            String DeleteSql = "DELETE FROM menucard WHERE id= " + id;
            stmt.executeUpdate(DeleteSql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Method to update records
    public static void updateRecord(String username, String password, int id, String name, double price, String description, int statusInt) {
        try (java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/platemate", username, password);
             Statement stmt = conn.createStatement()) {

            String updateSql = "UPDATE menucard SET "
                    + " name = '" + name + "', "
                    + " price = " + price + ", "
                    + " description = '" + description + "', "
                    + " status = " + statusInt
                    + " WHERE id = " + id;

            stmt.executeUpdate(updateSql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

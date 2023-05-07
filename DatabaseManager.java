package edu.symbi.aiml2021.platemate;
import java.io.*;
import java.util.*;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    static Scanner sc = new Scanner(System.in);
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) {
        try {
            System.out.println("Enter your MySQL server username: ");
            String username = sc.nextLine();
            System.out.println("Enter your MySQL server password: ");
            String password = sc.nextLine();

            DatabaseManager dm = new DatabaseManager();
            dm.createDatabase(username, password);
            dm.createTable(username, password);



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
                        System.out.println("Enter id of item to delete: ");
                        int id = Integer.parseInt(br.readLine());
                        dm.deleteRecord(username, password, id);
                        System.out.println("Menu item successfully deleted ");
                        break;
                    case 3:
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
                        quit = true;
                        break;
                }
            }
            while (!quit);
        }
        catch (InputMismatchException | IOException e) {
            System.out.println("Invalid input");
        }

    }
    public static void createDatabase(String username, String password) {
        try (java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/", username, password);
             Statement stmt = conn.createStatement()) {

            String CreateSql = "CREATE DATABASE IF NOT EXISTS platemate";
            stmt.executeUpdate(CreateSql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
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

    public static void deleteRecord(String username, String password, int id){
        try (java.sql.Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/platemate",username, password);
             Statement stmt = conn.createStatement()) {

            String DeleteSql = "DELETE FROM menucard WHERE id= " + id;
            stmt.executeUpdate(DeleteSql);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

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

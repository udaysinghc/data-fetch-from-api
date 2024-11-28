package org.example;

import java.sql.*;

public class WorkbenchDbConnection {
    private static final String JDBC_URL = "jdbc:mysql://uat-azure-db.mariadb.database.azure.com:3306/?protocol=tcp";

    //  private static final String JDBC_URL = "jdbc:mysql://localhost:3306/uat-azure-db.mariadb.database.azure.com";
    private static final String USERNAME = "***************";
    private static final String PASSWORD = "***************";

    public static void main(String[] abc) throws SQLException {

        // Establishing connection
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            System.out.println("Connected to the database!");

            // Creating statement
            Statement statement = connection.createStatement();

            // Executing a simple query
            String query = "SELECT * FROM case_management.case";
            ResultSet resultSet = statement.executeQuery(query);

            // Processing the result set
            while (resultSet.next()) {
                // Retrieve data from each row
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                // Process the data as needed
                System.out.println("ID: " + id + ", Name: " + name);
            }
        } catch (SQLException e) {
            // Handle any errors
            e.printStackTrace();
        }
    }
}
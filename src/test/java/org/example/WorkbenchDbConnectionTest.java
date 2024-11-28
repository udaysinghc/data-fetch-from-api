package org.example;

import org.testng.annotations.Test;

import java.sql.*;

public class WorkbenchDbConnectionTest {
    private static final String JDBC_URL = "jdbc:mysql://uat-azure-db.mariadb.database.azure.com:3306/case_management";
    private static final String USERNAME = "***************";
    private static final String PASSWORD = "*****************";

    @Test
    public void validateDatabaseFetch() {
        // Fetch the API data
        FetchCaseList.main(null);
        Integer apiId = FetchCaseList.Id;
        String apiName = FetchCaseList.name;

        // Display fetched API data
        System.out.println("API Data:");
        System.out.println("ID: " + apiId);
        System.out.println("Name: " + apiName);

        // Establishing DB connection
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            System.out.println("Connected to the database!");

            // Creating a query to fetch data by ID
            String query = "SELECT id, name FROM `case` WHERE id = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, apiId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Retrieve data from the database
                        int dbId = resultSet.getInt("id");
                        String dbName = resultSet.getString("name");

                        // Compare database and API data
                        System.out.println("Database Data:");
                        System.out.println("ID: " + dbId);
                        System.out.println("Name: " + dbName);

                        if (apiId.equals(dbId) && apiName.equals(dbName)) {
                            System.out.println("Verification Successful: Data matches!");
                        } else {
                            System.out.println("Verification Failed: Data does not match!");
                        }
                    } else {
                        System.out.println("Verification Failed: No record found for ID " + apiId + " in the database.");
                    }
                }
            }
        } catch (SQLException e) {
            // Handle any SQL errors
            e.printStackTrace();
        }
    }
}
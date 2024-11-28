package org.example;

import io.restassured.RestAssured;
import io.restassured.path.json.*;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;

import java.util.*;
public class FetchCaseList {

    public static  Integer Id;
    public static String name;
    public static void main(String[] args) {
        // Base URI of the API
        String baseUri = "https://aps-cases-microservice-srv.uat-azure.xara.ai/v1/case/caseList";

        // Create JSON body for the request
        JSONObject requestBody = new JSONObject();
        requestBody.put("filter_model", "{}");
        requestBody.put("columns", new Object[] {}); // Empty array
        requestBody.put("order_by", "id");

        JSONObject paginationInfo = new JSONObject();
        paginationInfo.put("page_size", 10);
        paginationInfo.put("page", 1);

        requestBody.put("pagination_information", paginationInfo);
        requestBody.put("order_in", "asc");

        // Set the x-auth-token
        String authToken = "916ab301-b58e-46d0-b383-5b099fa52253";

        // Configure RestAssured
        RestAssured.baseURI = baseUri;
        RequestSpecification request = RestAssured.given();

        // Set headers and body
        request.header("Content-Type", "application/json");
        request.header("x-auth-token", authToken);
        request.body(requestBody.toString());

        // Make POST request and capture response
        Response response = request.post();
        String responseBody = response.asString();
        JsonPath jsonPath = new JsonPath(responseBody);
        // Print status code and response body
        System.out.println("Status Code: " + response.getStatusCode());
        Id = jsonPath.getInt("results[0].id");
        name = jsonPath.getString("results[0].name");
        // Print the extracted fields
        System.out.println("First ID: " + Id);
        System.out.println("name: " + name);
        // Fetch all IDs from the results array
        List <Integer> ids = jsonPath.getList("results.id");
        System.out.println("All IDs: " + ids);
    }
}

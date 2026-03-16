package com.globant.automation.request;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;

import java.util.Map;

public class RequestBuilder {

    private static final String API_KEY_HEADER = "api_key";

    /**
     * Sens a POST request to the specified endpoint.
     *
     * @param baseurl Base URL of the API
     * @param path Endpoint path
     * @param body Request body to be serialized to JSON
     * @param apikey API key used for authentication
     * @return Response returned by the API
     */
    public static Response postRequest(String baseurl, String path, Object body, String apikey){
        RequestSpecification requestSpecification = RestAssured.given()
                .baseUri(baseurl)
                .body(body)
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter());

        if (apikey != null){
            requestSpecification.header(API_KEY_HEADER, apikey);
        }

        return requestSpecification.post(path);
    }

    /**
     * Sends a GET request to the specified endpoint.
     *
     * @param baseurl Base URL of the API
     * @param path Endpoint path
     * @param queryparams Optional query parameters
     * @param apikey API key used for authentication
     * @return Response returned by the API
     */
    public static Response getRequest(String baseurl, String path, Map<String, Object> queryparams, String apikey) {
        RequestSpecification requestSpecification = RestAssured.given()
                .baseUri(baseurl)
                .basePath(path)
                .header(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType())
                .filter(new RequestLoggingFilter())
                .filter(new ResponseLoggingFilter());

        if (queryparams != null){
            requestSpecification.queryParams(queryparams);
        }

        if (apikey != null){
            requestSpecification.header(API_KEY_HEADER, apikey);
        }

        return requestSpecification.get();
    }

}
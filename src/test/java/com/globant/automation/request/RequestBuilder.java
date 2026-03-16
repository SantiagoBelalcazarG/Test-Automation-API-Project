package com.globant.automation.request;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.http.HttpHeaders;
import org.apache.http.entity.ContentType;

public class RequestBuilder {

    private static final String API_KEY_HEADER = "api_key";

    public static Response postResquest(String baseurl, String path, Object body, String apikey){
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

}
package com.globant.automation.test;

import com.globant.automation.config.TestRunner;
import com.globant.automation.model.ApiResponseDTO;
import com.globant.automation.request.RequestBuilder;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GetUserTest extends TestRunner {

    @Test(testName = "Validate user login")
    public void loginUserTest(){
        Map<String, Object> queryparams = new HashMap<>();
        queryparams.put("username", "santiagojb");
        queryparams.put("password", "1234test");

        Response response = RequestBuilder.getRequest(getBaseurl(), "/user/login", queryparams, getApikey());
        ApiResponseDTO apiResponseDTO = response.as(ApiResponseDTO.class);

        assertEquals(response.getStatusCode(), 200, "The status code doesn't match");
        assertEquals(apiResponseDTO.getCode(), Integer.valueOf(200), "Response code should match");
        assertEquals(apiResponseDTO.getType(), "unknown", "The response type should match.");
        assertTrue(apiResponseDTO.getMessage().contains("logged in user session"), "Login message should confirm session");
    }

    @Test(testName = "Validate user logout")
    public void logoutUserTest(){
        Response response = RequestBuilder.getRequest(getBaseurl(), "/user/logout", null, getApikey());
        ApiResponseDTO apiResponseDTO = response.as(ApiResponseDTO.class);

        assertEquals(response.getStatusCode(), 200, "The status code doesn't match");
        assertEquals(apiResponseDTO.getCode(), Integer.valueOf(200), "Response code should match");
        assertEquals(apiResponseDTO.getType(), "unknown", "The response type should match.");
        assertEquals(apiResponseDTO.getMessage(), "ok", "Logout message should be ok");
    }

}
package com.globant.automation.test;

import com.globant.automation.config.TestRunner;
import com.globant.automation.model.ApiResponseDTO;
import com.globant.automation.model.UserDTO;
import com.globant.automation.request.RequestBuilder;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class GetUserTest extends TestRunner {

    private Long userId;

    @BeforeTest(description = "Creates a user before executing the tests to ensure a valid user exists")
    public void setup(){
        userId = System.currentTimeMillis();

        UserDTO userDTO = UserDTO.builder()
                .id(userId)
                .username("santiagojb")
                .password("1234test")
                .firstName("santiago")
                .lastName("belalcazar")
                .email("santiago@test.com")
                .build();

        Response response = RequestBuilder.postRequest(getBaseurl(), "/user", userDTO, getApikey());
        ApiResponseDTO apiResponseDTO = response.as(ApiResponseDTO.class);

        assertEquals(response.getStatusCode(), 200, "The status code doesn't match.");
        assertEquals(apiResponseDTO.getMessage(), String.valueOf(userDTO.getId()), "The message should match the user id.");
    }

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
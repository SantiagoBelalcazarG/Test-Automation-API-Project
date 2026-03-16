package com.globant.automation.test;

import com.globant.automation.config.TestRunner;
import com.globant.automation.model.ApiResponseDTO;
import com.globant.automation.model.CreateUserDTO;
import com.globant.automation.request.RequestBuilder;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class PostUserTest extends TestRunner {

    @Test(testName = "Validate user creation")
    public void createUserTest(){
        CreateUserDTO createUserDTO = CreateUserDTO.builder()
                .id(1L)
                .username("santiagojb")
                .password("1234test")
                .firstName("santiago")
                .lastName("belalcazar")
                .email("santiago@test.com")
                .build();

        Response response = RequestBuilder.postResquest(getBaseurl(), "/user", createUserDTO, getApikey());
        ApiResponseDTO apiResponseDTO = response.as(ApiResponseDTO.class);

        assertEquals(response.getStatusCode(), 200, "The status code doesn't match.");
        assertNotNull(apiResponseDTO, "The response body should not be null.");
        assertEquals(apiResponseDTO.getCode(), Integer.valueOf(200), "The response code should match.");
        assertEquals(apiResponseDTO.getType(), "unknown", "The response type should match.");
        assertEquals(apiResponseDTO.getMessage(), String.valueOf(createUserDTO.getId()), "The message should match the user id.");
    }

}
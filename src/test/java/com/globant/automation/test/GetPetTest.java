package com.globant.automation.test;

import com.globant.automation.config.TestRunner;
import com.globant.automation.model.PetDTO;
import com.globant.automation.request.RequestBuilder;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class GetPetTest extends TestRunner {

    private Long petId;

    @BeforeTest
    public void setup(){
        petId = System.currentTimeMillis();

        PetDTO petDTO = PetDTO.builder()
                .id(petId)
                .name("test")
                .status("available")
                .build();

        Response response = RequestBuilder.postRequest(getBaseurl(), "/pet", petDTO, getApikey());
        PetDTO pet = response.as(PetDTO.class);

        assertEquals(response.getStatusCode(), 200, "The status code doesn't match.");
        assertEquals(pet.getId(), petId, "The pet id should match.");
    }

    @Test(testName = "Verify pets with available status are found")
    public void petAvailableTest() {
        Map<String, Object> queryparams = new HashMap<>();
        queryparams.put("status", "available");

        Response response = RequestBuilder.getRequest(getBaseurl(), "/pet/findByStatus", queryparams, getApikey());
        List<PetDTO> petsResponseDTO = response.as(new TypeRef<List<PetDTO>>() {});

        assertEquals(response.getStatusCode(), 200, "The status code doesn't match.");
        assertNotNull(petsResponseDTO, "The pets list should not be null.");
        for (PetDTO pet : petsResponseDTO){assertEquals(pet.getStatus(), "available", "The pet status should be available");}
    }

    @Test(testName = "Varify pet is found by id")
    public void petByIdTest(){
        Response response = RequestBuilder.getRequest(getBaseurl(), "/pet/" + petId, null, getApikey());
        PetDTO petResponseDTO = response.as(PetDTO.class);

        assertEquals(response.getStatusCode(), 200, "The status code doesn't match.");
        assertNotNull(petResponseDTO, "The pet response should not be null.");
        assertEquals(petResponseDTO.getId(), petId, "The pet id should match.");
        assertEquals(petResponseDTO.getName(), "test", "The pet name should match");
        assertEquals(petResponseDTO.getStatus(), "available", "The pet status should match");
    }

}
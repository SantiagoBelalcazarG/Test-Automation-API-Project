package com.globant.automation.test;

import com.globant.automation.config.TestRunner;
import com.globant.automation.model.OrderDTO;
import com.globant.automation.model.PetDTO;
import com.globant.automation.request.RequestBuilder;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class PostStoreTest extends TestRunner {

    private Long petId;

    @BeforeTest(description = "Creates a pet before executing the tests to ensure a valid petId exists")
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

    @Test(testName = "Validate store order creation")
    public void createStoreOrderTest() {
        OrderDTO orderDTO = OrderDTO.builder()
                .id(9001L)
                .petId(petId)
                .quantity(1)
                .shipDate("2026-03-16T20:15:30.448+0000")
                .status("placed")
                .complete(true)
                .build();

        Response response = RequestBuilder.postRequest(getBaseurl(), "/store/order", orderDTO, getApikey());
        OrderDTO orderResponseDTO = response.as(OrderDTO.class);

        assertEquals(response.getStatusCode(), 200, "The status code doesn't match.");
        assertNotNull(orderResponseDTO, "The order response should not be null.");
        assertEquals(orderResponseDTO.getId(), orderDTO.getId(), "The order id should match.");
        assertEquals(orderResponseDTO.getPetId(), orderDTO.getPetId(), "The pet id should match.");
        assertEquals(orderResponseDTO.getQuantity(), orderDTO.getQuantity(), "The quantity should match.");
        assertEquals(orderResponseDTO.getShipDate(), orderDTO.getShipDate(), "The shipDate should match.");
        assertEquals(orderResponseDTO.getStatus(), orderDTO.getStatus(), "The status should match.");
        assertEquals(orderResponseDTO.getComplete(), orderDTO.getComplete(), "The complete value should match.");
    }

}
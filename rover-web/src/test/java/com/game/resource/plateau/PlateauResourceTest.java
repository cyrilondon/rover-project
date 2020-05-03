package com.game.resource.plateau;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.junit.Test;

import com.game.BaseUnitTest;
import com.game.Main;

public class PlateauResourceTest extends BaseUnitTest {

	/**
	 * Test to see that the message "Got it!" is sent in the response.
	 */
	@Test
	public void testGetIt() {
		String responseMsg = target.path("v1/plateau").request().get(String.class);
		assertEquals("Got Plateau Resource!", responseMsg);
	}

	@Test
	public void testInitializePlateauAndGet() {
		String plateauUUID = "13567a5d-a21c-495e-80a3-d12adaf8585c";
		Response response = initializePlateau(plateauUUID);
		// check the Response status = 201 for newly created Resource
		assertEquals(201, response.getStatus());
		// check the Location URI response header
		//http://localhost:8080/game/v1/plateau/13567a5d-a21c-495e-80a3-d12adaf8585c
	    assertEquals(response.getHeaderString(HttpHeaders.LOCATION), Main.BASE_URI + "v1/plateau/" + plateauUUID);

		// rest call to get the Plateau with UUID = plateauUUID
		String getResponse = target.path(String.format("v1/plateau/%s", plateauUUID)).request().get(String.class);
		String expectedResponse = "{\"height\":5,\"uuid\":\"13567a5d-a21c-495e-80a3-d12adaf8585c\",\"width\":5}";

		assertEquals(expectedResponse, getResponse);	

	}
	
}

package com.game.resource.plateau;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.game.BaseUnitTest;

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
		initializePlateau(plateauUUID);

		// rest call to get the Plateau with UUID = plateauUUID
		String response = target.path(String.format("v1/plateau/%s", plateauUUID)).request().get(String.class);
		String expectedResponse = "{\"height\":5,\"uuid\":\"13567a5d-a21c-495e-80a3-d12adaf8585c\",\"width\":5}";

		assertEquals(expectedResponse, response);	

	}

	
}

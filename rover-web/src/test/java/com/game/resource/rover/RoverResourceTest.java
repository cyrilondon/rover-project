package com.game.resource.rover;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.game.BaseUnitTest;

public class RoverResourceTest extends BaseUnitTest {

	/**
	 * Test to see that the message "Got it!" is sent in the response.
	 */
	@Test
	public void testGetIt() {
		String responseMsg = target.path("v1/rover").request().get(String.class);
		assertEquals("Got a Rover Resource!", responseMsg);
	}

	@Test
	public void testInitializeRoverAndGet() {

		// initialize first a Plateau (needed as we are only in memory for now)
		String plateauUUID = "13567a5e-a21c-495e-80a3-d12adaf8585c";
		initializePlateau(plateauUUID);

		String roverName = "ROVER_TEST";
		int abscissa = 2;
		int ordinate = 3;
		String orientation = "N";

		String entity = String.format(
				"{\"plateauUuid\": \"%s\", \"name\": \"%s\", \"abscissa\": %d, \"ordinate\": %d, \"orientation\": \"%s\"}",
				plateauUUID, roverName, abscissa, ordinate, orientation);
		target.path("v1/rover/initialize").request().put(Entity.entity(entity, MediaType.APPLICATION_JSON));

		// rest call to get the Plateau with UUID = plateauUUID
		String response = target.path(String.format("v1/rover/%s/%s", roverName, plateauUUID)).request().get(String.class);
		String expectedResponse = "{\"abscissa\":2,\"name\":\"ROVER_TEST\",\"ordinate\":3,\"orientation\":\"N\",\"plateauUuid\":\"13567a5e-a21c-495e-80a3-d12adaf8585c\"}";

		assertEquals(expectedResponse, response);

	}
}

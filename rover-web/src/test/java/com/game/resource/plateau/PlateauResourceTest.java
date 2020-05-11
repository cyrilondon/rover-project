package com.game.resource.plateau;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.NotFoundException;
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
	public void testInitializeGetWithString() {
		String plateauUUID = "13567a5d-a21c-495e-80a3-d12adaf8585c";
		Response response = initializePlateau(plateauUUID);
		// check the Response status = 201 for newly created Resource
		assertEquals(201, response.getStatus());
		// check the Location URI response header
		// http://localhost:8080/game/v1/plateau/13567a5d-a21c-495e-80a3-d12adaf8585c
		assertEquals(response.getHeaderString(HttpHeaders.LOCATION), Main.BASE_URI + "v1/plateau/" + plateauUUID);

		// rest call to get the Plateau with UUID = plateauUUID
		String getResponse = target.path(String.format("v1/plateau/%s", plateauUUID)).request().get(String.class);
		String expectedResponse = "{\"height\":5,\"uuid\":\"13567a5d-a21c-495e-80a3-d12adaf8585c\",\"width\":5}";

		assertEquals(expectedResponse, getResponse);
	}
	
	@Test
	public void testInitializeGetWithesponse() {
		String plateauUUID = "13567a5d-a21c-495e-80a3-d12adaf8585c";
		initializePlateau(plateauUUID);
		
		// rest call to get the Plateau with UUID = plateauUUID
		Response getResponse = target.path(String.format("v1/plateau/%s", plateauUUID)).request().get(Response.class);
		String expectedStringResponse = "{\"height\":5,\"uuid\":\"13567a5d-a21c-495e-80a3-d12adaf8585c\",\"width\":5}";

		assertEquals(200, getResponse.getStatus());
		assertEquals(expectedStringResponse, getResponse.readEntity(String.class));
	
	}

	/**
	 * We call getPlateau without initialization We expect a 404 Not Found Response
	 * code
	 */
	@Test
	public void testGetPlateauWithNotFoundException() {

		String plateauUUID = "13567a5d-a21c-495e-80a3-d12adaf8585c";
		// rest call to get the Plateau with UUID = plateauUUID but no initialization
		try {
			target.path(String.format("v1/plateau/%s", plateauUUID)).request().get(String.class);
		} catch (NotFoundException e) {
			// Jersey test client creates a brand new Exception with lost information
			// cf org.glassfish.jersey.client.JerseyInvocation.convertToException
			// this will NOT happen in a Curl for example where we get the full root cause information
			assertEquals("HTTP 404 Not Found", e.getMessage());
			assertEquals(String.format("[ERR-002] Entity [Plateau] with Id [%s] not found in the Application Repository", plateauUUID), (e.getResponse().readEntity(String.class)));
		}
	}
	
	
	/**
	 * We call getPlateau without initialization We expect a 404 Not Found Response
	 * code
	 */
	@Test
	public void testGetPlateauWithNotFoundExceptionAndResponseReturn() {

		String plateauUUID = "13567a5d-a21c-495e-80a3-d12adaf8585d";
		// rest call to get the Plateau with UUID = plateauUUID but no initialization
		Response response = target.path(String.format("v1/plateau/%s", plateauUUID)).request().get(Response.class);
		assertEquals(response.getStatusInfo().getStatusCode(), 404);
		assertEquals(response.getStatusInfo().getReasonPhrase(), "Not Found");
		assertEquals(String.format("[ERR-002] Entity [Plateau] with Id [%s] not found in the Application Repository", plateauUUID), (response.readEntity(String.class)));
	}

	/**
	 * We call getPlateau without initialization We expect a 404 Not Found Response
	 * code
	 */
	@Test
	public void testInitializePlateauWithNegativeDimensions() {

		String plateauUUID = "13567a5d-a21c-495e-80a3-d12adaf8585c";
		Response response = initializePlateau(plateauUUID, -3, 3);
		// check the Response status = 500 for newly created Resource
		// Jersey test client creates a brand new Exception with lost information
		// this will NOT happen in a Curl for example where we get the root cause information
		assertEquals(response.getStatusInfo().getStatusCode(), 500);
		assertEquals(response.getStatusInfo().getReasonPhrase(), "Internal Server Error");

	}

}

package com.game;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PlateauResourceTest {

	private HttpServer server;
	private WebTarget target;

	@Before
	public void setUp() throws Exception {
		// start the server
		server = Main.startServer();
		// create the client
		ClientConfig clientConfig = new ClientConfig();
		clientConfig.register(org.glassfish.jersey.jsonb.JsonBindingFeature.class);
		Client c = ClientBuilder.newClient(clientConfig);

		target = c.target(Main.BASE_URI);
	}

	@After
	public void tearDown() throws Exception {
		server.shutdown();
	}

	/**
	 * Test to see that the message "Got it!" is sent in the response.
	 */
	@Test
	public void testGetIt() {
		String responseMsg = target.path("plateau").request().get(String.class);
		assertEquals("Got a Plateau!", responseMsg);
	}

	@Test
	public void testInitializePlateau() {

		// Preparing json message
		String UUID = "53567a5d-a21c-495e-80a3-d12adaf8585c";
		String entity = String.format("{\"uuid\": \"%s\", \"width\": 5, \"height\": 5}", UUID);

		target.path("v1/plateau/initialize").request().put(Entity.entity(entity, MediaType.APPLICATION_JSON));

	}

}

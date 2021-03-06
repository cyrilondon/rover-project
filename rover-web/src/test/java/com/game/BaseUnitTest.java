package com.game;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.client.ClientConfig;
import org.junit.After;
import org.junit.Before;

public class BaseUnitTest {

	protected HttpServer server;

	protected WebTarget target;

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

	protected Response initializePlateau(String UUID) {
		return initializePlateau(UUID, 5, 5);
	}

	protected Response initializePlateau(String UUID, int width, int height) {
		
		// Preparing json message
		String entity = String.format("{\"uuid\": \"%s\", \"width\": %d, \"height\": %d}", UUID, width, height);

		// rest call to initialize a Plateau with UUID= plateauUUID and width=height=5
		return target.path("v1/plateau/initialize").request().post(Entity.entity(entity, MediaType.APPLICATION_JSON));
	}

}

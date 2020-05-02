package com.game.resource.plateau;

import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.game.domain.application.command.plateau.PlateauInitializeCommand;
import com.game.domain.application.context.GameContext;
import com.game.domain.application.service.GameService;
import com.game.resource.plateau.dto.PlateauInitializeCommandDto;

/**
 * Root resource (exposed at "plateau" path)
 */
@Path("v1/plateau")
public class PlateauResource {

	GameService gameService = GameContext.getInstance().getGameService();

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to the
	 * client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return "Got a Plateau!";
	}

	@PUT
	@Path("/initialize")
	@Consumes(MediaType.APPLICATION_JSON)
	public void initializePlateau(PlateauInitializeCommandDto commandDto) {

		PlateauInitializeCommand command = new PlateauInitializeCommand.Builder()
				.withId(UUID.fromString(commandDto.getUuid())).withWidth(commandDto.getWidth())
				.withHeight(commandDto.getHeight()).build();
		
		gameService.execute(command);
	}

}

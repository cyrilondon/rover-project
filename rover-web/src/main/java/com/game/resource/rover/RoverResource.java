package com.game.resource.rover;

import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.game.domain.application.command.rover.RoverGetCommand;
import com.game.domain.application.command.rover.RoverInitializeCommand;
import com.game.domain.application.context.GameContext;
import com.game.domain.application.service.GameService;
import com.game.domain.model.entity.rover.Rover;
import com.game.domain.model.entity.rover.RoverIdentifier;
import com.game.resource.rover.dto.RoverDto;
import com.game.resource.rover.dto.RoverInitializeCommandDto;

/**
 * Root resource (exposed at "v1/rover" path)
 */
@Path("v1/rover")
public class RoverResource {

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
		return "Got a Rover Resource!";
	}

	@PUT
	@Path("/initialize")
	@Consumes(MediaType.APPLICATION_JSON)
	public void initializeRover(RoverInitializeCommandDto commandDto) {

		RoverInitializeCommand command = new RoverInitializeCommand.Builder()
				.withPlateauUuid(commandDto.getPlateauUuid()).withName(commandDto.getName())
				.withAbscissa(commandDto.getAbscissa()).withOrdinate(commandDto.getOrdinate())
				.withOrientation(commandDto.getOrientation().charAt(0)).build();

		gameService.execute(command);

	}

	@GET
	@Path("{name}/{plateauId}")
	@Produces(MediaType.APPLICATION_JSON)
	public RoverDto getRover(@PathParam("name") String name, @PathParam("plateauId") String plateauId) {
		
		RoverIdentifier roverIdentifier = new RoverIdentifier(UUID.fromString(plateauId), name);
		RoverGetCommand command = new RoverGetCommand(roverIdentifier);
		Rover rover = gameService.execute(command);
		return new RoverDto(rover.getId().getName(), rover.getId().getPlateauId().toString(),
				rover.getOrientation().getValue(), rover.getXPosition(), rover.getYPosition());
	}

}

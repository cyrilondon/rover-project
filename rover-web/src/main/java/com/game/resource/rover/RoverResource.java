package com.game.resource.rover;

import java.net.URI;
import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.game.Main;
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

	@POST
	@Path("/initialize")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response initializeRover(RoverInitializeCommandDto commandDto) {

		// map the client Dto command to application command PlateauInitializeCommand 
		RoverInitializeCommand command = new RoverInitializeCommand.Builder()
				.withPlateauUuid(commandDto.getPlateauUuid()).withName(commandDto.getName())
				.withAbscissa(commandDto.getAbscissa()).withOrdinate(commandDto.getOrdinate())
				.withOrientation(commandDto.getOrientation().charAt(0)).build();

		// call the Application Primary Port for creation/initialization
		gameService.execute(command);

		// return the Response with status 201 = created + location header with UUID of
		// the created resource/rover
		URI createdUri = URI.create(new StringBuilder(Main.BASE_URI).append("v1/rover/").append(commandDto.getName())
				.append("/").append(commandDto.getPlateauUuid()).toString());
		return Response.created(createdUri).build();

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

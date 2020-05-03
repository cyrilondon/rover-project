package com.game.resource.plateau;

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
import com.game.domain.application.command.plateau.PlateauGetCommand;
import com.game.domain.application.command.plateau.PlateauInitializeCommand;
import com.game.domain.application.context.GameContext;
import com.game.domain.application.service.GameService;
import com.game.domain.model.entity.plateau.Plateau;
import com.game.resource.plateau.dto.PlateauDto;
import com.game.resource.plateau.dto.PlateauInitializeCommandDto;

/**
 * Root resource (exposed at "v1/plateau" path)
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
		return "Got Plateau Resource!";
	}

	@POST
	@Path("/initialize")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response initializePlateau(PlateauInitializeCommandDto commandDto) {

		// map the client Dto command to application command PlateauInitializeCommand 
		PlateauInitializeCommand command = new PlateauInitializeCommand.Builder()
				.withId(UUID.fromString(commandDto.getUuid())).withWidth(commandDto.getWidth())
				.withHeight(commandDto.getHeight()).build();
		
		// call the Application Primary Port for creation/initialization
		gameService.execute(command);
		
		// return the Response with status 201 = created + location header with UUID of the created resource/plateau
		URI createdUri = URI.create(Main.BASE_URI + "v1/plateau/" + commandDto.getUuid());
		return Response.created(createdUri).build();
		
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public PlateauDto getPlateau(@PathParam("id") UUID  uuid) {
		
			PlateauGetCommand command = new PlateauGetCommand(uuid);
			Plateau plateau = gameService.execute(command);
			
			return new PlateauDto(plateau.getId().toString(), plateau.getWidth(), plateau.getHeight());
	}

}

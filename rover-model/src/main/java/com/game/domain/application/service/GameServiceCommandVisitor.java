package com.game.domain.application.service;

import com.game.domain.application.command.plateau.PlateauGetCommand;
import com.game.domain.application.command.plateau.PlateauInitializeCommand;
import com.game.domain.application.command.rover.RoverGetCommand;
import com.game.domain.application.command.rover.RoverInitializeCommand;
import com.game.domain.application.command.rover.RoverMoveCommand;
import com.game.domain.application.command.rover.RoverTurnCommand;
import com.game.domain.model.entity.plateau.Plateau;
import com.game.domain.model.entity.rover.Rover;

public class GameServiceCommandVisitor {
	
	private GameServiceImpl gameService;;
	
	public GameServiceCommandVisitor(GameServiceImpl gameService) {
		this.gameService = gameService;
	}
	
	public void visit(PlateauInitializeCommand command) {
		gameService.execute(command);
	}
	
	public Plateau visit(PlateauGetCommand command) {
		return gameService.execute(command);
	}
	
	public void visit(RoverInitializeCommand command) {
		gameService.execute(command);
	}
	
	public void visit(RoverMoveCommand command) {
		gameService.execute(command);
	}
	
	public void visit(RoverTurnCommand command) {
		gameService.execute(command);
	}

	public Rover visit(RoverGetCommand roverGetCommand) {
		return gameService.execute(roverGetCommand);
	}

}

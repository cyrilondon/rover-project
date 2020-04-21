package com.game.domain.application.service;

import com.game.domain.application.command.plateau.PlateauInitializeCommand;
import com.game.domain.application.command.rover.RoverInitializeCommand;
import com.game.domain.application.command.rover.RoverMoveCommand;
import com.game.domain.application.command.rover.RoverTurnCommand;

public class GameServiceCommandVisitor {
	
	private GameServiceImpl gameService;;
	
	public GameServiceCommandVisitor(GameServiceImpl gameService) {
		this.gameService = gameService;
	}
	
	public void visit(PlateauInitializeCommand command) {
		gameService.execute(command);
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

}
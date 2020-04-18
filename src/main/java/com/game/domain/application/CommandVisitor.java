package com.game.domain.application;

import com.game.domain.application.command.PlateauInitializeCommand;
import com.game.domain.application.command.RoverInitializeCommand;
import com.game.domain.application.command.RoverTurnCommand;
import com.game.domain.application.command.RoverMoveCommand;

public class CommandVisitor {
	
	private GameServiceImpl gameService = new GameServiceImpl();
	
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

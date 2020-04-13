package com.game.domain.application;

import com.game.domain.application.command.InitializePlateauCommand;
import com.game.domain.application.command.InitializeRoverCommand;
import com.game.domain.application.command.MakeTurnRoverCommand;
import com.game.domain.application.command.MoveRoverCommand;

public class CommandVisitor {
	
	private GameService gameService = GameContext.getInstance().getGameService();
	
	public void visit(InitializePlateauCommand command) {
		gameService.execute(command);
	}
	
	public void visit(InitializeRoverCommand command) {
		gameService.execute(command);
	}
	
	public void visit(MoveRoverCommand command) {
		gameService.execute(command);
	}
	
	public void visit(MakeTurnRoverCommand command) {
		gameService.execute(command);
	}

}

package com.game.domain.application.command.rover;

import com.game.domain.application.CommandVisitor;
import com.game.domain.application.command.ApplicationCommand;
import com.game.domain.model.entity.rover.RoverIdentifier;
import com.game.domain.model.entity.rover.RoverTurnInstruction;

public class RoverTurnCommand implements ApplicationCommand  {
	
	RoverTurnInstruction turn;
	
	RoverIdentifier roverId;
	
	public RoverTurnCommand(RoverIdentifier roverId,  RoverTurnInstruction turn) {
		this.roverId = roverId;
		this.turn = turn;
	}

	public RoverTurnInstruction getTurn() {
		return turn;
	}

	public RoverIdentifier getRoverId() {
		return roverId;
	}

	@Override
	public void acceptVisitor(CommandVisitor visitor) {
		visitor.visit(this);
	}

}

package com.game.domain.application.command.rover;

import com.game.domain.application.command.VoidApplicationCommand;
import com.game.domain.application.service.GameServiceCommandVisitor;
import com.game.domain.model.entity.rover.RoverIdentifier;
import com.game.domain.model.entity.rover.RoverTurnInstruction;

public class RoverTurnCommand implements VoidApplicationCommand  {
	
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
	public Void acceptVisitor(GameServiceCommandVisitor visitor) {
		visitor.visit(this);
		return null;
	}

}

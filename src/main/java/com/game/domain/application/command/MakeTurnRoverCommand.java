package com.game.domain.application.command;

import com.game.domain.application.CommandVisitor;
import com.game.domain.model.entity.RoverIdentifier;
import com.game.domain.model.entity.RoverTurnInstruction;

public class MakeTurnRoverCommand implements ApplicationCommand  {
	
	RoverTurnInstruction turn;
	
	RoverIdentifier roverId;
	
	public MakeTurnRoverCommand(RoverIdentifier roverId,  RoverTurnInstruction turn) {
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

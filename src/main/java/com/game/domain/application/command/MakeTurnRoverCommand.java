package com.game.domain.application.command;

import com.game.domain.application.CommandVisitor;
import com.game.domain.model.entity.RoverIdentifier;
import com.game.domain.model.entity.RoverInstruction;

public class MakeTurnRoverCommand implements ApplicationCommand  {
	
	RoverInstruction turn;
	
	RoverIdentifier roverId;
	
	public MakeTurnRoverCommand(RoverIdentifier roverId,  RoverInstruction turn) {
		this.roverId = roverId;
		this.turn = turn;
	}

	public RoverInstruction getTurn() {
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

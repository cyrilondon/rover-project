package com.game.domain.application.command.rover;

import com.game.domain.application.command.ReturnApplicationCommand;
import com.game.domain.application.service.GameServiceCommandVisitor;
import com.game.domain.model.entity.rover.Rover;
import com.game.domain.model.entity.rover.RoverIdentifier;

public class RoverGetCommand implements ReturnApplicationCommand<Rover>{
	
	private RoverIdentifier roverIdentifier;
	
	public RoverGetCommand(RoverIdentifier roverId) {
		this.roverIdentifier = roverId;
	}

	public RoverIdentifier getRoverIdentifier() {
		return roverIdentifier;
	}

	@Override
	public Rover acceptVisitor(GameServiceCommandVisitor visitor) {
		return visitor.visit(this);
	}

}

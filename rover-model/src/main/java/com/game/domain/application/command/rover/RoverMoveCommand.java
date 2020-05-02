package com.game.domain.application.command.rover;

import com.game.domain.application.command.VoidApplicationCommand;
import com.game.domain.application.service.GameServiceCommandVisitor;
import com.game.domain.model.entity.rover.RoverIdentifier;

/**
 * Command sent from the client to move a Rover with given name
 * by a certain number of moves
 *
 */
public class RoverMoveCommand implements VoidApplicationCommand {

	private RoverIdentifier roverId;
	
	private int numberOfMoves;
	
	public RoverMoveCommand(RoverIdentifier roverId, int numberOfMoves) {
		this.roverId = roverId;
		this.numberOfMoves = numberOfMoves;
	}

	public RoverIdentifier getRoverId() {
		return roverId;
	}

	public int getNumberOfMoves() {
		return numberOfMoves;
	}

	@Override
	public Void acceptVisitor(GameServiceCommandVisitor visitor) {
		visitor.visit(this);
		return null;
	}
	
}

package com.game.domain.model.exception;

import com.game.domain.model.entity.rover.Rover;
import com.game.domain.model.entity.rover.RoverIdentifier;

public class RoverNotFoundException extends EntityNotFoundException {
	
	private static final long serialVersionUID = 1L;
	
	public RoverNotFoundException(RoverIdentifier key) {
		super(Rover.class.getSimpleName(), key);
	}


}

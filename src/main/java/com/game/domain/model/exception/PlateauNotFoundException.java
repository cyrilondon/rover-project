package com.game.domain.model.exception;

import java.util.UUID;

import com.game.domain.model.entity.plateau.Plateau;

public class PlateauNotFoundException extends EntityNotFoundException {
	
	private static final long serialVersionUID = 1L;

	public PlateauNotFoundException(UUID key) {
		super(Plateau.class.getSimpleName(), key);
	}

}

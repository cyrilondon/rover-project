package com.game.domain.model.service.plateau;

import java.util.UUID;

import com.game.domain.application.context.GameContext;
import com.game.domain.model.entity.dimensions.RelativisticTwoDimensions;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensions;
import com.game.domain.model.entity.plateau.Plateau;

public class PlateauFactory {
	
	public static Plateau createPlateau(UUID uuid, TwoDimensionalCoordinates dimensions, int speed) {
		
		Plateau plateau = null;
		if (speed < GameContext.MINIMAL_RELATIVISTIC_SPEED) {
			plateau = new Plateau(uuid, new TwoDimensions(
					new TwoDimensionalCoordinates(dimensions.getAbscissa(), dimensions.getOrdinate())));
		} else {
			plateau = new Plateau(uuid, new RelativisticTwoDimensions(speed, new TwoDimensions(
					(new TwoDimensionalCoordinates(dimensions.getAbscissa(), dimensions.getOrdinate())))));
		}
		
		return plateau;
		
	}

}

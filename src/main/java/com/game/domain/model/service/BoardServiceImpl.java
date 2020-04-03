package com.game.domain.model.service;

import com.game.domain.model.entity.Board;
import com.game.domain.model.entity.dimensions.RelativisticTwoDimensions;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.dimensions.TwoDimensions;

/**
 * Domain service which has the responsibility to handle the entity
 * {@link Board}
 *
 */
public class BoardServiceImpl implements DomainService {

	public Board initializeBoard(TwoDimensionalCoordinates coordinates) {
		TwoDimensions dimensions = new TwoDimensions(
				new TwoDimensionalCoordinates(coordinates.getAbscissa(), coordinates.getOrdinate()));
		return new Board(dimensions);
	}

	/**
	 * Initializes the board as observed from an observer with speed v
	 * 
	 * @param speed       observer speed
	 * @param coordinates with rest dimensions
	 * @return relativistic board
	 */
	public Board initializeRelativisticBoard(int speed, TwoDimensionalCoordinates coordinates) {
		RelativisticTwoDimensions dimensions = new RelativisticTwoDimensions(speed,
				(new TwoDimensionalCoordinates(coordinates.getAbscissa(), coordinates.getOrdinate())));
		return new Board(dimensions);
	}

}

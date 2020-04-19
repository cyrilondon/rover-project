package com.game.domain.model.event.subscriber.rover;

import com.game.domain.application.context.GameContext;
import com.game.domain.model.entity.rover.Rover;
import com.game.domain.model.event.DomainEventSubscriber;
import com.game.domain.model.event.rover.RoverInitializedEvent;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.exception.IllegalArgumentGameException;
import com.game.domain.model.exception.PlateauNotFoundException;

public class RoverInitializedEventSubscriber implements DomainEventSubscriber<RoverInitializedEvent> {

	@Override
	public void handleEvent(RoverInitializedEvent event) {

		// 1. loads the Plateau
		try {
		GameContext.getInstance().getPlateauService().loadPlateau(event.getRoverId().getPlateauId());
		} catch (PlateauNotFoundException e) {
			throw new IllegalArgumentGameException(String.format(GameExceptionLabels.ERROR_MESSAGE_SEPARATION_PATTERN,
					e.getMessage(), GameExceptionLabels.ADDING_ROVER_NOT_ALLOWED));
		}

		GameContext.getInstance().getRoverService().getRoverRepository()
				.add(new Rover(event.getRoverId(), event.getPosition(),
						event.getOrientation()));

	}

	@Override
	public Class<RoverInitializedEvent> subscribedToEventType() {
		return RoverInitializedEvent.class;
	}

}

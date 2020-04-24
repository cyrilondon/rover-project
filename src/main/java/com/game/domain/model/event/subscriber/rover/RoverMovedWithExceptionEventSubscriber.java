package com.game.domain.model.event.subscriber.rover;

import com.game.domain.application.context.GameContext;
import com.game.domain.model.event.DomainEventSubscriber;
import com.game.domain.model.event.rover.RoverMovedWithExceptionEvent;
import com.game.domain.model.exception.GameException;

public class RoverMovedWithExceptionEventSubscriber implements DomainEventSubscriber<RoverMovedWithExceptionEvent> {

	@Override
	public void handleEvent(RoverMovedWithExceptionEvent event) {

		// 1. remove the persistent rover from the game
		GameContext.getInstance().getRoverService().removeRover(event.getRoverId());

		// 2. set the last rover position as free on the Plateau
		GameContext.getInstance().getPlateauService().updatePlateauWithFreeLocation(
				event.getPlateauUuid(), event.getRoverPreviousPosition());
		
		throw new GameException(event.getException().getMessage());

	}

	@Override
	public Class<RoverMovedWithExceptionEvent> subscribedToEventType() {
		return RoverMovedWithExceptionEvent.class;
	}

}

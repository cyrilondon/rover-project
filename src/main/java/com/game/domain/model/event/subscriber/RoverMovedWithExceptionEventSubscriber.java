package com.game.domain.model.event.subscriber;

import com.game.domain.application.GameContext;
import com.game.domain.model.event.DomainEventSubscriber;
import com.game.domain.model.event.RoverMovedWithExceptionEvent;

public class RoverMovedWithExceptionEventSubscriber implements DomainEventSubscriber<RoverMovedWithExceptionEvent> {

	@Override
	public void handleEvent(RoverMovedWithExceptionEvent event) {

		// 1. remove the persistent rover from the game
		GameContext.getInstance().getRoverService().removeRover(event.getRoverId());

		// 2. set the last rover position as free on the Plateau
		GameContext.getInstance().getPlateauService().updatePlateauWithFreeLocation(
				event.getPlateauUuid(), event.getRoverPreviousPosition());

	}

	@Override
	public Class<RoverMovedWithExceptionEvent> subscribedToEventType() {
		return RoverMovedWithExceptionEvent.class;
	}

}

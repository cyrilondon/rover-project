package com.game.domain.model.event.subscriber.rover;

import java.util.Objects;

import com.game.domain.application.context.GameContext;
import com.game.domain.model.event.AbstractDomainEventSubscriber;
import com.game.domain.model.event.rover.RoverMovedWithExceptionEvent;
import com.game.domain.model.exception.GameException;

public class RoverMovedWithExceptionEventSubscriber extends AbstractDomainEventSubscriber<RoverMovedWithExceptionEvent> {

	@Override
	public void handleEvent(RoverMovedWithExceptionEvent event) {

		// 1. remove the persistent rover from the game
		GameContext.getInstance().getRoverService().removeRover(event.getRoverId());

		// 2. set the last rover position as free on the Plateau
		GameContext.getInstance().getPlateauService().updatePlateauWithFreeLocation(
				event.getPlateauUuid(), event.getRoverPreviousPosition());
		
		throw new GameException(event.toString(), event.getException());

	}

	@Override
	public Class<RoverMovedWithExceptionEvent> subscribedToEventType() {
		return RoverMovedWithExceptionEvent.class;
	}
	
	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}

		if (obj instanceof RoverMovedWithExceptionEventSubscriber) {
			RoverMovedWithExceptionEventSubscriber other = (RoverMovedWithExceptionEventSubscriber) obj;
			return Objects.equals(id, other.getId());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

}

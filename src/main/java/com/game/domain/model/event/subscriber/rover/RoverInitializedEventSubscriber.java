package com.game.domain.model.event.subscriber.rover;

import java.util.Objects;

import com.game.domain.application.context.GameContext;
import com.game.domain.model.entity.rover.Rover;
import com.game.domain.model.event.AbstractDomainEventSubscriber;
import com.game.domain.model.event.rover.RoverInitializedEvent;

public class RoverInitializedEventSubscriber extends AbstractDomainEventSubscriber<RoverInitializedEvent> {

	@Override
	public void handleEvent(RoverInitializedEvent event) {

		// 1. loads the Plateau
		GameContext.getInstance().getPlateauService().loadPlateau(event.getRoverId().getPlateauId());

		GameContext.getInstance().getRoverService().getRoverRepository()
				.add(new Rover(event.getRoverId(), event.getPosition(),
						event.getOrientation()));

	}

	@Override
	public Class<RoverInitializedEvent> subscribedToEventType() {
		return RoverInitializedEvent.class;
	}
	
	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}

		if (obj instanceof RoverInitializedEventSubscriber) {
			RoverInitializedEventSubscriber other = (RoverInitializedEventSubscriber) obj;
			return Objects.equals(getId(), other.getId());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

}

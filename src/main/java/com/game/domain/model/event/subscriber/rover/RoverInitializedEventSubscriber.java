package com.game.domain.model.event.subscriber.rover;

import com.game.domain.application.context.GameContext;
import com.game.domain.model.entity.rover.Rover;
import com.game.domain.model.event.DomainEventSubscriber;
import com.game.domain.model.event.rover.RoverInitializedEvent;

public class RoverInitializedEventSubscriber implements DomainEventSubscriber<RoverInitializedEvent> {

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

}

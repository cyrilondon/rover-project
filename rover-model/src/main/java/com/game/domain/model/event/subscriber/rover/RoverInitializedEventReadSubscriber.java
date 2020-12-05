package com.game.domain.model.event.subscriber.rover;

import com.game.domain.application.context.GameContext;
import com.game.domain.model.event.AbstractDomainEventSubscriber;
import com.game.domain.model.event.rover.RoverInitializedEvent;

public class RoverInitializedEventReadSubscriber extends AbstractDomainEventSubscriber<RoverInitializedEvent> {

	@Override
	public void handleEvent(RoverInitializedEvent event) {
		GameContext.getInstance().getRoverService().getReadRoverRepository().add(event.getRoverId());
	}

	@Override
	public Class<RoverInitializedEvent> subscribedToEventType() {
		return RoverInitializedEvent.class;
	}

}

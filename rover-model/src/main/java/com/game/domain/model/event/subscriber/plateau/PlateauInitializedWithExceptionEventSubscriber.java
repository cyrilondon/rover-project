package com.game.domain.model.event.subscriber.plateau;

import java.util.Objects;

import com.game.domain.model.event.AbstractDomainEventSubscriber;
import com.game.domain.model.event.plateau.PlateauInitializedWithExceptionEvent;
import com.game.domain.model.event.subscriber.rover.RoverInitializedWithExceptionEventSubscriber;
import com.game.domain.model.exception.GameException;
import com.game.domain.model.exception.PlateauInitializationException;

public class PlateauInitializedWithExceptionEventSubscriber  extends AbstractDomainEventSubscriber<PlateauInitializedWithExceptionEvent> {

	@Override
	public void handleEvent(PlateauInitializedWithExceptionEvent event) {
		throw new PlateauInitializationException(((GameException)event.getException()).getOriginalMessage());
	}

	@Override
	public Class<PlateauInitializedWithExceptionEvent> subscribedToEventType() {
		return PlateauInitializedWithExceptionEvent.class;
	}
	
	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}

		if (obj instanceof RoverInitializedWithExceptionEventSubscriber) {
			PlateauInitializedWithExceptionEventSubscriber other = (PlateauInitializedWithExceptionEventSubscriber) obj;
			return Objects.equals(id, other.getId());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

}

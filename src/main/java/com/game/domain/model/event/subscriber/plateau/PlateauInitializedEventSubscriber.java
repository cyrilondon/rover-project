package com.game.domain.model.event.subscriber.plateau;

import java.util.Objects;

import com.game.domain.application.context.GameContext;
import com.game.domain.model.entity.plateau.Plateau;
import com.game.domain.model.event.AbstractDomainEventSubscriber;
import com.game.domain.model.event.plateau.PlateauInitializedEvent;

public class PlateauInitializedEventSubscriber extends AbstractDomainEventSubscriber<PlateauInitializedEvent> {

	@Override
	public void handleEvent(PlateauInitializedEvent event) {
		Plateau plateau = new Plateau(event.getPlateauId(), event.getDimensions()).initializeLocations();
		GameContext.getInstance().getPlateauService().addPlateau(plateau);
	}

	@Override
	public Class<PlateauInitializedEvent> subscribedToEventType() {
		return PlateauInitializedEvent.class;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}

		if (obj instanceof PlateauInitializedEventSubscriber) {
			PlateauInitializedEventSubscriber other = (PlateauInitializedEventSubscriber) obj;
			return Objects.equals(getId(), other.getId());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

}

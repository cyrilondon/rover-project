package com.game.domain.model.event.subscriber.plateau;

import java.util.Objects;

import com.game.domain.application.context.GameContext;
import com.game.domain.model.event.AbstractDomainEventSubscriber;
import com.game.domain.model.event.plateau.PlateauSwitchedLocationEvent;

public class PlateauSwitchedLocationEventSubscriber extends AbstractDomainEventSubscriber<PlateauSwitchedLocationEvent> {

	@Override
	public void handleEvent(PlateauSwitchedLocationEvent event) {

		// 1 . update persistent plateau locations
		updatePlateauWithLastLocations(event);

	}

	private void updatePlateauWithLastLocations(PlateauSwitchedLocationEvent event) {
		if (event.getPreviousPosition() != null)
		GameContext.getInstance().getPlateauService().updatePlateauWithFreeLocation(event.getPlateauId(),
				event.getPreviousPosition());
		if (event.getCurrentPosition() != null)
			GameContext.getInstance().getPlateauService().updatePlateauWithOccupiedLocation(event.getPlateauId(),
					event.getCurrentPosition());
	}

	@Override
	public Class<PlateauSwitchedLocationEvent> subscribedToEventType() {
		return PlateauSwitchedLocationEvent.class;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}

		if (obj instanceof PlateauSwitchedLocationEventSubscriber) {
			PlateauSwitchedLocationEventSubscriber other = (PlateauSwitchedLocationEventSubscriber) obj;
			return Objects.equals(getId(), other.getId());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

}

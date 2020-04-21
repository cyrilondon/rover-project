package com.game.domain.model.event.subscriber.plateau;

import com.game.domain.application.context.GameContext;
import com.game.domain.model.event.DomainEventSubscriber;
import com.game.domain.model.event.plateau.PlateauSwitchedLocationEvent;

public class PlateauSwitchedLocationEventSubscriber implements DomainEventSubscriber<PlateauSwitchedLocationEvent> {

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

}

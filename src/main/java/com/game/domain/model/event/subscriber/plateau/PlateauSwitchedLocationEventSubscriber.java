package com.game.domain.model.event.subscriber.plateau;

import com.game.domain.application.GameContext;
import com.game.domain.model.entity.plateau.Plateau;
import com.game.domain.model.event.DomainEventSubscriber;
import com.game.domain.model.event.plateau.PlateauSwitchedLocationEvent;

public class PlateauSwitchedLocationEventSubscriber implements DomainEventSubscriber<PlateauSwitchedLocationEvent> {

	@Override
	public void handleEvent(PlateauSwitchedLocationEvent event) {
		
		Plateau InMemoryPlateau = GameContext.getInstance().getPlateau(event.getPlateauId());

		// 1 . update in memory plateau locations
		InMemoryPlateau.setLocationFree(event.getPreviousPosition());
		InMemoryPlateau.setLocationOccupied(event.getCurrentPosition());

		// 2 . update persistent plateau locations
		updatePlateauWithLastLocations(event);

	}

	private void updatePlateauWithLastLocations(PlateauSwitchedLocationEvent event) {
		GameContext.getInstance().getPlateauService().updatePlateauWithLocations(event.getPlateauId(),
				event.getPreviousPosition(), event.getCurrentPosition());
	}

	@Override
	public Class<PlateauSwitchedLocationEvent> subscribedToEventType() {
		return PlateauSwitchedLocationEvent.class;
	}

}

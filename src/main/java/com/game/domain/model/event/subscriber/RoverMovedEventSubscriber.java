package com.game.domain.model.event.subscriber;
import com.game.domain.application.GameContext;
import com.game.domain.model.event.DomainEventSubscriber;
import com.game.domain.model.event.RoverMovedEvent;

public class RoverMovedEventSubscriber implements DomainEventSubscriber<RoverMovedEvent> {

	@Override
	public void handleEvent(RoverMovedEvent event) {
		
		// 1 . update in memory plateau locations
	    GameContext.getInstance().getPlateau(event.getPlateauUUID()).setLocationFree(event.getPreviousPosition());
	    GameContext.getInstance().getPlateau(event.getPlateauUUID()).setLocationBusy(event.getCurrentPosition());
	    
		// 2. update persistent Rover with last position
		updateRoverWithLastPosition(event);

		// 3 . update persistent plateau locations
		updatePlateauWithLastLocations(event);
		
	}

	@Override
	public Class<RoverMovedEvent> subscribedToEventType() {
		return RoverMovedEvent.class;
	}

	private void updateRoverWithLastPosition(RoverMovedEvent event) {
		GameContext.getInstance().getRoverService().updateRoverWithPosition(event.getRoverId(), event.getCurrentPosition());
	}

	private void updatePlateauWithLastLocations(RoverMovedEvent event) {
		GameContext.getInstance().getPlateauService().updatePlateauWithLocations(event.getRoverId().getPlateauUuid(),
				event.getPreviousPosition(), event.getCurrentPosition());
	}

}

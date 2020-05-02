package com.game.domain.model.entity.rover;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

import com.game.core.validation.ArgumentCheck;
import com.game.domain.application.context.GameContext;
import com.game.domain.model.entity.IdentifiedPublisherDomainEntity;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.event.DomainEvent;
import com.game.domain.model.event.plateau.PlateauSwitchedLocationEvent;
import com.game.domain.model.event.rover.RoverInitializedEvent;
import com.game.domain.model.event.rover.RoverInitializedWithExceptionEvent;
import com.game.domain.model.event.rover.RoverMovedEvent;
import com.game.domain.model.event.rover.RoverMovedEvent.Builder;
import com.game.domain.model.event.rover.RoverMovedWithExceptionEvent;
import com.game.domain.model.event.rover.RoverTurnedEvent;
import com.game.domain.model.exception.GameException;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.exception.OptimisticLockingException;
import com.game.domain.model.validation.EntityDefaultValidationNotificationHandler;
import com.game.domain.model.validation.RoverMovedPositionValidationNotificationHandler;
import com.game.domain.model.validation.ValidationNotificationHandler;

public class Rover extends IdentifiedPublisherDomainEntity<Rover, RoverIdentifier> {

	private Orientation orientation;

	private TwoDimensionalCoordinates position;
	
	/**
	 * Rover step length - configurable in the GameContext Default = 1
	 */
	private int step = GameContext.getInstance().getRoverStepLength();

	public final Function<DomainEvent, DomainEvent> moveRover = event -> {
		this.position = ((RoverMovedEvent) event).getCurrentPosition();
		validate(new RoverMovedPositionValidationNotificationHandler());
		return event;
	};
	
	public final Function<DomainEvent, DomainEvent> initializeRover = event -> {
		validate(new EntityDefaultValidationNotificationHandler());
		return event;
	};

	public final BiFunction<Exception, DomainEvent, DomainEvent> moveRoverWithException = (exception, event) -> {
		return new RoverMovedWithExceptionEvent((RoverMovedEvent) event, exception);
	};
	
	public final BiFunction<Exception, DomainEvent, DomainEvent> initializeRoverWithException = (exception, event) -> {
		return new RoverInitializedWithExceptionEvent((RoverInitializedEvent) event, exception);
	};

	final Function<DomainEvent, DomainEvent> turnRover = event -> {
		this.orientation = ((RoverTurnedEvent) event).getCurrentOrientation();
		return event;
	};

	/**
	 * We add this constructor with a name parameter to keep track of a given Rover,
	 * in the case of sending commands from sources/clients other than a file (
	 * where the explore command follows immediately the initialize command). By
	 * example, the command could ask for Rover_X to turn left or turn right well
	 * after Rover_X has been initialized.
	 * 
	 * @param rover name
	 * @param rover coordinates
	 * @param rover orientation
	 */
	public Rover(RoverIdentifier id, TwoDimensionalCoordinates coordinates, Orientation orientation) {
		this.id = ArgumentCheck.preNotNull(id, GameExceptionLabels.MISSING_ROVER_IDENTIFIER);
		this.position = ArgumentCheck.preNotNull(coordinates, GameExceptionLabels.MISSING_ROVER_POSITION);
		this.orientation = ArgumentCheck.preNotNull(orientation, GameExceptionLabels.MISSING_ROVER_ORIENTATION);
	}

	@Override
	public Rover validate(ValidationNotificationHandler handler) {
		return new RoverValidator(this, handler).validate();
	}

	public Rover validate() {
		return validate(new EntityDefaultValidationNotificationHandler());
	}

	public void move() {
		moveNumberOfTimes(1);
	}

	public void moveNumberOfTimes(int numberOfTimes) {

		IntStream.range(0, numberOfTimes).forEach(i -> {
			moveWithEvent(step);
		});

	}

	private void moveWithEvent(int step) {
		
		TwoDimensionalCoordinates previousPosition = this.position;
		
		TwoDimensionalCoordinates currentPosition = getCoordinates().shiftWithOrientation(this.orientation, step);

		// build event with previous and updated position
		RoverMovedEvent event = buildRoverMovedEvent(previousPosition)
				.withCurrentPosition(currentPosition).build();

		// apply the event to the current in-memory instance
		// and publish the event for persistence purpose (DB instance + event store)
		applyAndPublishEvent(event, moveRover, moveRoverWithException);
		
		PlateauSwitchedLocationEvent plateauEvent = new PlateauSwitchedLocationEvent.Builder().withPlateauId(event.getPlateauUUID()).
				withPreviousPosition(previousPosition).withCurrentPosition(currentPosition).build();
		
		publishEvent(plateauEvent);
	}

	/**
	 * We delegate the turn left command to the orientation object itself
	 */
	public void turnLeft() {

		RoverTurnedEvent event = new RoverTurnedEvent.Builder().withRoverId(new RoverIdentifierDto(getId(), getVersion())).withPreviousOrientation(orientation)
				.withCurrentOrientation(orientation.turnLeft()).build();

		applyAndPublishEvent(event, turnRover);

	}

	/**
	 * We delegate the turn left command to the orientation object itself
	 */
	public void turnRight() {

		RoverTurnedEvent event = new RoverTurnedEvent.Builder().withRoverId(new RoverIdentifierDto(getId(), getVersion())).withPreviousOrientation(orientation)
				.withCurrentOrientation(orientation.turnRight()).build();

		applyAndPublishEvent(event, turnRover);
	}

	private Builder buildRoverMovedEvent(TwoDimensionalCoordinates previousPosition) {
		return new RoverMovedEvent.Builder().withRoverId(new RoverIdentifierDto(getId(), getVersion()))
				.withPreviousPosition(previousPosition);
	}
	
	public void checkAgainstVersion(int currentVersion) {
		if (currentVersion == this.getVersion()) {
			this.setVersion(this.getVersion() + 1);
		} else {
			throw new GameException(new OptimisticLockingException(String.format(GameExceptionLabels.CONCURRENT_MODIFICATION_ERROR_MESSAGE, this)));
		}
	}

	public TwoDimensionalCoordinates getPosition() {
		return position;
	}

	public void setPosition(TwoDimensionalCoordinates position) {
		this.position = position;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	public TwoDimensionalCoordinates getCoordinates() {
		return position;
	}

	public int getXPosition() {
		return getCoordinates().getAbscissa();
	}

	public int getYPosition() {
		return getCoordinates().getOrdinate();
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}

		if (obj instanceof Rover) {
			Rover other = (Rover) obj;
			return Objects.equals(id, other.getId()) && Objects.equals(getCoordinates(), other.getCoordinates())
					&& Objects.equals(getOrientation(), other.getOrientation());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getCoordinates(), getOrientation());
	}

	@Override
	public String toString() {
		return String.format("Rover [%s] attached to Plateau [%s] with [%s] and [%s] and version [%s]", this.getId().getName(),
				this.getId().getPlateauId(), this.getCoordinates(), this.getOrientation(), this.getVersion());
	}

}

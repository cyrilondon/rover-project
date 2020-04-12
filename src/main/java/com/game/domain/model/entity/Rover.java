package com.game.domain.model.entity;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

import com.game.core.validation.ArgumentCheck;
import com.game.domain.application.GameContext;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.event.DomainEvent;
import com.game.domain.model.event.RoverMovedEvent;
import com.game.domain.model.event.RoverMovedEvent.Builder;
import com.game.domain.model.event.RoverMovedWithExceptionEvent;
import com.game.domain.model.event.RoverTurnedEvent;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.validation.EntityDefaultValidationNotificationHandler;
import com.game.domain.model.validation.RoverMovedPositionValidationNotificationHandler;
import com.game.domain.model.validation.ValidationNotificationHandler;

public class Rover extends IdentifiedDomainEntity<Rover, RoverIdentifier> {

	private Orientation orientation;

	private TwoDimensionalCoordinates position;

	/**
	 * Rover step length - configurable in the GameContext Default = 1
	 */
	private int step = GameContext.getInstance().getRoverStepLength();

	final Function<DomainEvent, DomainEvent> moveRover = event -> {
		this.position = ((RoverMovedEvent) event).getCurrentPosition();
		validate(new RoverMovedPositionValidationNotificationHandler());
		return event;
	};

	final Function<DomainEvent, DomainEvent> turnRover = event -> {
		this.orientation = ((RoverTurnedEvent) event).getCurrentOrientation();
		return event;
	};

	final BiFunction<Exception, DomainEvent, DomainEvent> moveRoverException = (exception, event) -> {
		return new RoverMovedWithExceptionEvent((RoverMovedEvent) event, exception);
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
		switch (orientation) {
		case NORTH:
			moveNorthNumberOfTimes(numberOfTimes);
			break;
		case WEST:
			moveWestNumberOfTimes(numberOfTimes);
			break;
		case EAST:
			moveEastNumberOfTimes(numberOfTimes);
			break;
		case SOUTH:
			moveSouthNumberOfTimes(numberOfTimes);
			break;
		default:
			throw new IllegalArgumentException(GameExceptionLabels.ILLEGAL_ORIENTATION_VALUE);
		}
	}

	private void moveNorthNumberOfTimes(int numberOfTimes) {
		IntStream.range(0, numberOfTimes).forEach(i -> {
			moveNorth();
		});
	}

	private void moveWestNumberOfTimes(int numberOfTimes) {
		IntStream.range(0, numberOfTimes).forEach(i -> {
			moveWest();
		});
	}

	private void moveEastNumberOfTimes(int numberOfTimes) {
		IntStream.range(0, numberOfTimes).forEach(i -> {
			moveEast();
		});
	}

	private void moveSouthNumberOfTimes(int numberOfTimes) {
		IntStream.range(0, numberOfTimes).forEach(i -> {
			moveSouth();
		});
	}

	private void moveNorth() {
		moveVertically(step);
	}

	private void moveWest() {
		moveHorizontally(-step);
	}

	private void moveEast() {
		moveHorizontally(step);
	}

	private void moveSouth() {
		moveVertically(-step);
	}

	private void moveHorizontally(int step) {

		// build event with previous and updated position
		RoverMovedEvent event = buildRoverMovedEvent(this.position)
				.withCurrentPosition(getCoordinates().shiftAlongAbscissa(step)).build();

		// apply the event to the current in-memory instance
		// and publish the event for persistence purpose (DB instance + event store)
		applyAndPublishEvent(event, moveRover, moveRoverException);
	}

	private void moveVertically(int step) {

		// build event with the previous and the updated position
		RoverMovedEvent event = buildRoverMovedEvent(this.position)
				.withCurrentPosition(getCoordinates().shiftAlongOrdinate(step)).build();

		// apply the event to the current in-memory instance
		// and publish the event for persistence purpose (DB instance + event store)
		applyAndPublishEvent(event, moveRover, moveRoverException);
	}

	/**
	 * We delegate the turn left command to the orientation object itself
	 */
	public void turnLeft() {

		RoverTurnedEvent event = new RoverTurnedEvent.Builder().withRoverId(id).withPreviousOrientation(orientation)
				.withCurrentOrientation(orientation.turnLeft()).build();

		applyAndPublishEvent(event, turnRover);

	}

	/**
	 * We delegate the turn left command to the orientation object itself
	 */
	public void turnRight() {

		RoverTurnedEvent event = new RoverTurnedEvent.Builder().withRoverId(id).withPreviousOrientation(orientation)
				.withCurrentOrientation(orientation.turnRight()).build();

		applyAndPublishEvent(event, turnRover);
	}

	private Builder buildRoverMovedEvent(TwoDimensionalCoordinates previousPosition) {
		return new RoverMovedEvent.Builder().withRoverId(new RoverIdentifier(id.getPlateauUuid(), id.getName()))
				.withPreviousPosition(previousPosition);
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
		return String.format("Rover [%s] attached to Plateau [%s] with [%s] and [%s]", this.getId().getName(),
				this.getId().getPlateauUuid(), this.getCoordinates(), this.getOrientation());
	}

}

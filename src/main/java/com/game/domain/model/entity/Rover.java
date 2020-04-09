package com.game.domain.model.entity;

import java.util.Objects;
import java.util.stream.IntStream;

import com.game.core.validation.ArgumentCheck;
import com.game.domain.application.GameContext;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.event.DomainEventPublisher;
import com.game.domain.model.event.RoverMovedEvent;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.validation.EntityDefaultValidationNotificationHandler;
import com.game.domain.model.validation.ValidationNotificationHandler;

public class Rover implements Entity<Rover> {
	
	
	private RoverIdentifier id;

	private Orientation orientation;

	private TwoDimensionalCoordinates position;
	

	/**
	 * Rover step length - configurable in the GameContext
	 */
	private int step = GameContext.getInstance().getRoverStepLength();


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

	/**
	 * We delegate the turn left command to the orientation object itself
	 */
	public void turnLeft() {
		this.orientation = orientation.turnLeft();
	}

	/**
	 * We delegate the turn left command to the orientation object itself
	 */
	public void turnRight() {
		this.orientation = orientation.turnRight();
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
			validate();
		});
	}

	private void moveWestNumberOfTimes(int numberOfTimes) {
		IntStream.range(0, numberOfTimes).forEach(i -> {
			moveWest();
			validate();
		});
	}

	private void moveEastNumberOfTimes(int numberOfTimes) {
		IntStream.range(0, numberOfTimes).forEach(i -> {
			moveEast();
			validate();
		});
	}

	private void moveSouthNumberOfTimes(int numberOfTimes) {
		IntStream.range(0, numberOfTimes).forEach(i -> {
			moveSouth();
			validate();
		});
	}

	private void validate() {
		validate(new EntityDefaultValidationNotificationHandler());
	}

	private void moveNorth() {
		this.position = getCoordinates().shiftAlongOrdinate(step);
		DomainEventPublisher.instance().publish(new RoverMovedEvent.Builder().withPlateauUuid(id.getPlateauUuid()).withRoverName(id.getName())
				.withPreviousPosition(getCoordinates()).withCurrentPosition(getCoordinates().shiftAlongOrdinate(step)));
	}

	/**
	 * Essentially we publish a domain event here with rover name, old and current
	 * position
	 */
	private void moveWest() {
		this.position = getCoordinates().shiftAlongAbscissa(-step);
		DomainEventPublisher.instance()
				.publish(new RoverMovedEvent.Builder().withPlateauUuid(id.getPlateauUuid()).withRoverName(id.getName()).withPreviousPosition(getCoordinates())
						.withCurrentPosition(getCoordinates().shiftAlongAbscissa(-step)));
	}

	private void moveEast() {
		this.position = getCoordinates().shiftAlongAbscissa(step);
		DomainEventPublisher.instance()
		.publish(new RoverMovedEvent.Builder().withPlateauUuid(id.getPlateauUuid()).withRoverName(id.getName()).withPreviousPosition(getCoordinates())
				.withCurrentPosition(getCoordinates().shiftAlongAbscissa(step)));
	}

	private void moveSouth() {
		this.position = getCoordinates().shiftAlongOrdinate(-step);
		DomainEventPublisher.instance()
		.publish(new RoverMovedEvent.Builder().withPlateauUuid(id.getPlateauUuid()).withRoverName(id.getName()).withPreviousPosition(getCoordinates())
				.withCurrentPosition(getCoordinates().shiftAlongOrdinate(-step)));
	}

	public TwoDimensionalCoordinates getPosition() {
		return position;
	}

	public void setPosition(TwoDimensionalCoordinates position) {
		this.position = position;
	}

	@Override
	public Rover validate(ValidationNotificationHandler handler) {
		return new RoverValidator(this, handler).validate();
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
	
	public RoverIdentifier getId() {
		return id;
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
		return String.format("Rover [%s] attached to Plateau [%s] with [%s] and [%s]", this.getId().getName(), this.getId().getPlateauUuid(), this.getCoordinates(),
				this.getOrientation());
	}

}

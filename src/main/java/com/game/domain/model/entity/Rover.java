package com.game.domain.model.entity;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.IntStream;

import com.game.core.validation.ArgumentCheck;
import com.game.domain.application.GameContext;
import com.game.domain.model.DomainEventPublisher;
import com.game.domain.model.RoverMovedEvent;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.validation.EntityDefaultValidationNotificationHandler;
import com.game.domain.model.validation.ValidationNotificationHandler;

public class Rover implements Entity<Rover> {

	/**
	 * Not asked by this exercise but added in case of subsequent commands on a
	 * given Rover
	 * {@link Rover#Rover(String, TwoDimensionalCoordinates, Orientation)}
	 */
	private String name;

	private Orientation orientation;

	private TwoDimensionalCoordinates position;
	
	/**
	 * Many-to-one association to a Plateau instance
	 * We keep track of the plateau UUID
	 */
	private UUID plateauUuid;

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
	public Rover(UUID plateauUuid, String name, TwoDimensionalCoordinates coordinates, Orientation orientation) {
		this.plateauUuid = ArgumentCheck.preNotNull(plateauUuid, GameExceptionLabels.MISSING_PLATEAU_UUID);
		this.position = ArgumentCheck.preNotNull(coordinates, GameExceptionLabels.MISSING_ROVER_POSITION);
		this.orientation = ArgumentCheck.preNotNull(orientation, GameExceptionLabels.MISSING_ROVER_ORIENTATION);
		this.name = ArgumentCheck.preNotEmpty(name, GameExceptionLabels.MISSING_ROVER_NAME);
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
		DomainEventPublisher.instance().publish(new RoverMovedEvent.Builder().withPlateauUuid(getPlateauUuid()).withRoverName(name)
				.withPreviousPosition(getCoordinates()).withCurrentPosition(getCoordinates().shiftAlongOrdinate(step)));
	}

	/**
	 * Essentially we publish a domain event here with rover name, old and current
	 * position
	 */
	private void moveWest() {
		this.position = getCoordinates().shiftAlongAbscissa(-step);
		DomainEventPublisher.instance()
				.publish(new RoverMovedEvent.Builder().withPlateauUuid(getPlateauUuid()).withRoverName(name).withPreviousPosition(getCoordinates())
						.withCurrentPosition(getCoordinates().shiftAlongAbscissa(-step)));
	}

	private void moveEast() {
		this.position = getCoordinates().shiftAlongAbscissa(step);
		DomainEventPublisher.instance()
		.publish(new RoverMovedEvent.Builder().withPlateauUuid(getPlateauUuid()).withRoverName(name).withPreviousPosition(getCoordinates())
				.withCurrentPosition(getCoordinates().shiftAlongAbscissa(step)));
	}

	private void moveSouth() {
		this.position = getCoordinates().shiftAlongOrdinate(-step);
		DomainEventPublisher.instance()
		.publish(new RoverMovedEvent.Builder().withPlateauUuid(getPlateauUuid()).withRoverName(name).withPreviousPosition(getCoordinates())
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

	public String getName() {
		return name;
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
	
	public UUID getPlateauUuid() {
		return plateauUuid;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == this) {
			return true;
		}

		if (obj instanceof Rover) {
			Rover other = (Rover) obj;
			return Objects.equals(plateauUuid, other.getPlateauUuid()) && Objects.equals(name, other.getName()) && Objects.equals(getCoordinates(), other.getCoordinates())
					&& Objects.equals(getOrientation(), other.getOrientation());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPlateauUuid(), getName(), getCoordinates(), getOrientation());
	}

	@Override
	public String toString() {
		return String.format("Rover [%s] attached to Plateau [%s] with [%s] and [%s]", this.getName(), this.getPlateauUuid(), this.getCoordinates(),
				this.getOrientation());
	}

}

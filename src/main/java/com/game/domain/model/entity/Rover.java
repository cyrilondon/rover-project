package com.game.domain.model.entity;

import java.util.Objects;

import com.game.core.validation.ArgumentCheck;
import com.game.domain.application.GameContext;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.validation.ValidationNotificationHandler;

public class Rover implements Entity<Rover> {

	public void setOrientation(Orientation orientation) {
		this.orientation = orientation;
	}

	/**
	 * Not asked by this exercise but added in case of subsequent commands on a
	 * given Rover
	 * {@link Rover#Rover(String, TwoDimensionalCoordinates, Orientation)}
	 */
	private String name;

	private Orientation orientation;

	private TwoDimensionalCoordinates position;

	private int step = GameContext.getInstance().getRoverStepLength();

	/**
	 * The normal way the Rover should be instantiated via the File adapter as each
	 * line initializes a anonymous Rover with coordinates and orientation
	 * {@link Rover#Rover(String, TwoDimensionalCoordinates, Orientation)}
	 * 
	 * @param name
	 * @param coordinates
	 * @param orientation
	 */
	public Rover(TwoDimensionalCoordinates coordinates, Orientation orientation) {
		this.position = ArgumentCheck.preNotNull(coordinates, GameExceptionLabels.MISSING_ROVER_POSITION);
		this.orientation = ArgumentCheck.preNotNull(orientation, GameExceptionLabels.MISSING_ROVER_ORIENTATION);
	}

	/**
	 * We add this constructor with a name parameter to keep track of a given Rover, in the case of
	 * sending commands from sources/clients other than a file ( where the explore command
	 * follows immediately the initialize command).
	 * By example, the command could ask for Rover_X
	 * to turn left or turn right well after Rover_X has been initialized.
	 * 
	 * @param rover name
	 * @param rover coordinates
	 * @param rover orientation
	 */
	public Rover(String name, TwoDimensionalCoordinates coordinates, Orientation orientation) {
		this(coordinates, orientation);
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
		switch (orientation) {
		case NORTH:
			moveNorth();
			break;
		case WEST:
			moveWest();
			break;
		case EAST:
			moveEast();
			break;
		case SOUTH:
			moveSouth();
			break;
		default:
			throw new IllegalArgumentException(GameExceptionLabels.ILLEGAL_ORIENTATION_VALUE);
		}
	}

	private void moveNorth() {
		getCoordinates().shiftAlongOrdinate(step);
	}

	private void moveWest() {
		getCoordinates().shiftAlongAbscissa(-1 * step);
	}

	private void moveEast() {
		getCoordinates().shiftAlongAbscissa(step);
	}

	private void moveSouth() {
		getCoordinates().shiftAlongOrdinate(-1 * step);
	}

	public TwoDimensionalCoordinates getPosition() {
		return position;
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
			return Objects.equals(name, other.getName()) && Objects.equals(getCoordinates(), other.getCoordinates())
					&& Objects.equals(getOrientation(), other.getOrientation());
		}

		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getName(), getCoordinates(), getOrientation());
	}

	@Override
	public String toString() {
		return String.format("Rover [%s] with [%s] and [%s]", this.getName(), this.getCoordinates(),
				this.getOrientation());
	}

}

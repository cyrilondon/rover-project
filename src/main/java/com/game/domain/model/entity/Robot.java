package com.game.domain.model.entity;

import java.util.Objects;

public class Robot {

	/**
	 * Not asked by this exercise but added in case of subsequent commands on a
	 * given Robot {@link Robot#Robot(String, Coordinates, Orientation)}
	 */
	private String name;

	private Orientation orientation;

	private Coordinates position;

	/**
	 * The normal way the Robot should be instantiated via the File adapter as each
	 * line initializes a anonymous Robot with coordinates and orientation
	 * 
	 * @param name
	 * @param coordinates
	 * @param orientation
	 */
	public Robot(Coordinates coordinates, Orientation orientation) {
		this.position = coordinates;
		this.orientation = orientation;
	}

	/**
	 * We add this constructor with a name parameter to keep track of a given Robot
	 * if we send commands other than from the file ( where the explore command
	 * follows immediately the initialize command) By example, it could be make the
	 * Robot 2 turn left or turn right well after Robot 2 has been initialized
	 * 
	 * @param name
	 * @param coordinates
	 * @param orientation
	 */
	public Robot(String name, Coordinates coordinates, Orientation orientation) {
		this(coordinates, orientation);
		this.name = name;
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
		}
	}

	private void moveNorth() {
		getCoordinates().shiftPlusOneAlongOrdinate();
	}

	private void moveWest() {
		getCoordinates().shiftMinusOneAlongAbscissa();
	}

	private void moveEast() {
		getCoordinates().shiftPlusOneAlongAbscissa();
	}

	private void moveSouth() {
		getCoordinates().shiftMinusOneAlongOrdinate();
	}

	public String getName() {
		return name;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public Coordinates getCoordinates() {
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

		if (obj instanceof Robot) {
			Robot other = (Robot) obj;
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
		return String.format("Robot [%s] with [%s] and [%s]", this.getName(), this.getCoordinates(),
				this.getOrientation());
	}

}

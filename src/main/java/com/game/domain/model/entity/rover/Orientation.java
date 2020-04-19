package com.game.domain.model.entity.rover;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.game.domain.model.entity.enums.GameEnum;

public enum Orientation implements GameEnum<String> {

	NORTH("N", 1) {

		@Override
		Orientation turnLeft() {
			return WEST;
		}

		@Override
		Orientation turnRight() {
			return EAST;
		}
	},

	EAST("E", 1) {

		@Override
		Orientation turnLeft() {
			return NORTH;
		}

		@Override
		Orientation turnRight() {
			return SOUTH;
		}
	},

	SOUTH("S", -1) {

		@Override
		Orientation turnLeft() {
			return EAST;
		}

		@Override
		Orientation turnRight() {
			return WEST;
		}
	},

	WEST("W", -1) {

		@Override
		Orientation turnLeft() {
			return SOUTH;
		}

		@Override
		Orientation turnRight() {
			return NORTH;
		}
	};

	private String value;
	
	/**
	 * The sign of the move direction
	 * along the corresponding axis
	 */
	private int axisDirection;
		
	private static final Orientation[] HORIZONTAL_ORIENTATIONS = {WEST, EAST};


	Orientation(String value, int axisDirection) {
		this.value = value;
		this.axisDirection = axisDirection;
	}

	public String getValue() {
		return value;
	}
	
	public int getAxisDirection() {
		return axisDirection;
	}

	abstract Orientation turnLeft();

	abstract Orientation turnRight();
	
	public boolean isHorizontal() {
		return isTypeInArray(this, HORIZONTAL_ORIENTATIONS);
	}
	
	private static boolean isTypeInArray(final Orientation orientation, final Orientation[] array) {
		return Arrays.stream(array).anyMatch(orientation::equals);
	}

	private static final Map<String, Orientation> lookup = new HashMap<>();

	static {
		Arrays.stream(Orientation.values()).forEach(item -> lookup.put(item.getValue(), item));
	}

	public static Orientation get(String value) {
		return lookup.get(value);
	}

	public static List<String> allValues() {
		return Stream.of(Orientation.values()).map(orientation -> orientation.getValue()).collect(Collectors.toList());
	}
	
	@Override
	public String toString() {
		return String.format("Orientation [%s]", super.toString());
	}

}

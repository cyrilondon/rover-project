package com.game.domain.model.entity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.game.domain.model.entity.enums.GameEnum;

public enum Orientation implements GameEnum<String> {

	NORTH("N") {

		@Override
		Orientation turnLeft() {
			return WEST;
		}

		@Override
		Orientation turnRight() {
			return EAST;
		}
	},

	EAST("E") {

		@Override
		Orientation turnLeft() {
			return NORTH;
		}

		@Override
		Orientation turnRight() {
			return SOUTH;
		}
	},

	SOUTH("S") {

		@Override
		Orientation turnLeft() {
			return EAST;
		}

		@Override
		Orientation turnRight() {
			return WEST;
		}
	},

	WEST("W") {

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

	Orientation(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	abstract Orientation turnLeft();

	abstract Orientation turnRight();

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

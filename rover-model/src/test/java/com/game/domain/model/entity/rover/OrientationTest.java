package com.game.domain.model.entity.rover;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.game.domain.model.entity.rover.Orientation;

public class OrientationTest {
	
	private Orientation NORTH = Orientation.NORTH;
	
	private Orientation WEST = Orientation.WEST;
	
	private Orientation SOUTH = Orientation.SOUTH;
	
	private Orientation EAST = Orientation.EAST;
	
	@Test
	public void testTurnLeft() {
		assertThat(NORTH.turnLeft()).isEqualTo(WEST);
		assertThat(WEST.turnLeft()).isEqualTo(SOUTH);
		assertThat(SOUTH.turnLeft()).isEqualTo(EAST);
		assertThat(EAST.turnLeft()).isEqualTo(NORTH);
	}
	
	@Test
	public void testTurnRight() {
		assertThat(NORTH.turnRight()).isEqualTo(EAST);
		assertThat(WEST.turnRight()).isEqualTo(NORTH);
		assertThat(SOUTH.turnRight()).isEqualTo(WEST);
		assertThat(EAST.turnRight()).isEqualTo(SOUTH);
	}
	
	@Test
	public void testLookup() {
		assertThat(Orientation.get("N")).isEqualTo(NORTH);
		assertThat(Orientation.get("E")).isEqualTo(EAST);
		assertThat(Orientation.get("S")).isEqualTo(SOUTH);
		assertThat(Orientation.get("W")).isEqualTo(WEST);
	}
	
	@Test
	public void testAllValues() {
		assertThat(Orientation.allValues()).containsExactlyInAnyOrder("N", "E", "W", "S");
	}
	
	@Test
	public void testToString() {
		assertThat(Orientation.NORTH.toString()).isEqualTo("Orientation [NORTH]");
	}

}

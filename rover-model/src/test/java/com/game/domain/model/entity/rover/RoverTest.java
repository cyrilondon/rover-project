package com.game.domain.model.entity.rover;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.assertj.core.api.Condition;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.game.domain.application.context.GameContext;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.event.DomainEvent;
import com.game.domain.model.event.plateau.PlateauSwitchedLocationEvent;
import com.game.domain.model.event.rover.RoverMovedEvent;
import com.game.domain.model.event.rover.RoverMovedWithExceptionEvent;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.exception.IllegalArgumentGameException;
import com.game.domain.model.exception.IllegalRoverMoveException;
import com.game.test.util.BaseUnitTest;

public class RoverTest extends BaseUnitTest {


	private final static int PLATEAU_WIDTH = 6;

	private final static int PLATEAU_HEIGHT = 6;


	@BeforeTest
	public void setup() {
		GameContext.getInstance().reset();
	}

	@BeforeMethod
	public void reset() {
		addPlateau(super.plateauUUID, PLATEAU_WIDTH, PLATEAU_HEIGHT);
		clearAndSubscribe();
		clearAllExpectedEvents();
		clearEventStore();
	}

	@Test
	public void testConstructorWithName() {
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(3, 4);
		UUID plateauUuid = UUID.randomUUID();
		Rover rover = new Rover(new RoverIdentifier(plateauUuid, ROVER_NAME), coordinates, Orientation.SOUTH);
		assertThat(rover.getCoordinates()).isEqualTo(new TwoDimensionalCoordinates(3, 4));
		assertThat(rover.getOrientation()).isEqualTo(Orientation.SOUTH);
		assertThat(rover.getId().getName()).isEqualTo(ROVER_NAME);
		assertThat(rover.getId().getPlateauId()).isEqualTo(plateauUuid);
	}

	@Test
	public void testGetPosition() {
		Rover rover = initializeRover(Orientation.SOUTH);
		assertThat(rover.getXPosition()).isEqualTo(3);
		assertThat(rover.getYPosition()).isEqualTo(4);
	}

	@Test
	public void testSetPosition() {
		Rover rover = initializeRover(Orientation.SOUTH);
		rover.setPosition(new TwoDimensionalCoordinates(7, 2));
		assertThat(rover.getPosition()).isEqualTo(new TwoDimensionalCoordinates(7, 2));
	}

	@Test
	public void testTurnLeft() {
		GameContext context = GameContext.getInstance();
		context.getEventStore().getAllEvents();
		Rover rover = initializeRover(Orientation.SOUTH);
		rover.turnLeft();
		assertThat(rover.getOrientation()).isEqualTo(Orientation.EAST);
	}

	@Test
	public void testTurnRight() {
		Rover rover = initializeRover(Orientation.SOUTH);
		rover.turnRight();
		assertThat(rover.getOrientation()).isEqualTo(Orientation.WEST);
	}

	@Test
	public void testMoveNorthOneTime() {
		Rover rover = initializeRover(Orientation.NORTH);
		rover.move();
		assertThat(rover.getOrientation()).isEqualTo(Orientation.NORTH);
		assertThat(rover.getXPosition()).isEqualTo(3);
		assertThat(rover.getYPosition()).isEqualTo(5);
		assertThat(roverInitializedEvents.size()).isEqualTo(0);
		assertThat(roverMovedEvents.size()).isEqualTo(1);
		List<DomainEvent> eventsList = GameContext.getInstance().getEventStore().getAllEvents();
		assertThat(eventsList.size()).isEqualTo(2);
		DomainEvent eventStored = eventsList.get(0);
		assertThat(eventStored).isInstanceOf(RoverMovedEvent.class);
	}

	@Test
	public void testMoveNorthTwoTimes() {
		Rover rover = initializeRover(Orientation.NORTH);
		rover.moveNumberOfTimes(2);
		assertThat(roverMovedEvents.size()).isEqualTo(2);
		assertThat(rover.getOrientation()).isEqualTo(Orientation.NORTH);
		assertThat(rover.getXPosition()).isEqualTo(3);
		assertThat(rover.getYPosition()).isEqualTo(6);
		List<DomainEvent> eventList = GameContext.getInstance().getEventStore().getAllEvents();
		assertThat(eventList.size()).isEqualTo(4);
		List<DomainEvent> movedEvents = eventList.stream().filter(event -> event instanceof RoverMovedEvent).collect(Collectors.toList());
		assertThat(movedEvents).hasSize(2);
	}

	@Test
	public void testMoveNorthThreeTimesOutOfBoard() {
		Rover rover = initializeRover(Orientation.NORTH);
		Throwable thrown = catchThrowable(() -> rover.moveNumberOfTimes(3));

		assertThat(thrown).isInstanceOf(IllegalRoverMoveException.class);
		assertThat(roverMovedEvents).hasSize(2);
		assertThat(roverMovedWithExceptionEvents.size()).isEqualTo(1);
		RoverMovedWithExceptionEvent event = roverMovedWithExceptionEvents.get(0);
		assertThat(event.getRoverId()).isEqualTo(rover.getId());
		assertThat(event.getException()).isInstanceOf(IllegalRoverMoveException.class);
		List<DomainEvent> eventStoredList = GameContext.getInstance().getEventStore().getAllEvents();
		assertThat(eventStoredList).hasSize(5);
		Condition<DomainEvent> roverMovedEventCondition = new Condition<DomainEvent>() {
			   public boolean matches(DomainEvent value) {
			     return value instanceof RoverMovedEvent;
			   }
		};
		assertThat(eventStoredList).areExactly(2, roverMovedEventCondition);
		Condition<DomainEvent> roverMovedWithExceptionEventCondition = new Condition<DomainEvent>() {
			   public boolean matches(DomainEvent value) {
			     return value instanceof RoverMovedWithExceptionEvent;
			   }
		};
		assertThat(eventStoredList).areExactly(1, roverMovedWithExceptionEventCondition);

		Condition<DomainEvent> plateauSwitchedLocationEventCondition = new Condition<DomainEvent>() {
			   public boolean matches(DomainEvent value) {
			     return value instanceof PlateauSwitchedLocationEvent;
			   }
		};
		assertThat(eventStoredList).areExactly(2, plateauSwitchedLocationEventCondition);

	}

	@Test
	public void testMoveWest() {
		Rover rover = initializeRover(Orientation.WEST);
		rover.move();
		assertThat(roverMovedEvents).hasSize(1);
		assertThat(rover.getOrientation()).isEqualTo(Orientation.WEST);
		assertThat(rover.getXPosition()).isEqualTo(2);
		assertThat(rover.getYPosition()).isEqualTo(4);
	}

	@Test
	public void testMoveSouth() {
		Rover rover = initializeRover(Orientation.SOUTH);
		rover.move();
		assertThat(roverMovedEvents).hasSize(1);
		assertThat(rover.getOrientation()).isEqualTo(Orientation.SOUTH);
		assertThat(rover.getXPosition()).isEqualTo(3);
		assertThat(rover.getYPosition()).isEqualTo(3);
	}

	@Test
	public void testMoveEast() {
		Rover rover = initializeRover(Orientation.EAST);
		rover.move();
		assertThat(rover.getOrientation()).isEqualTo(Orientation.EAST);
		assertThat(rover.getXPosition()).isEqualTo(4);
		assertThat(rover.getYPosition()).isEqualTo(4);
	}

	@Test
	public void testEqualsWithDifferentInstance() {
		Rover rover = initializeDefaultRover();
		assertThat(rover).isNotEqualTo(new TwoDimensionalCoordinates(3, 4));
	}

	@Test
	public void testEqualsWithSameInstance() {
		Rover rover = initializeDefaultRover();
		assertThat(rover).isEqualTo(rover);
	}

	@Test
	public void testEqualsWithName() {
		UUID uuid = UUID.randomUUID();
		Rover rover = initializeDefaultRover(uuid);
		Rover otherrover = initializeDefaultRover(uuid);
		assertThat(rover).isEqualTo(otherrover);
	}

	@Test
	public void testHashCodeWithName() {
		UUID uuid = UUID.randomUUID();
		Rover rover = initializeDefaultRover(uuid);
		Rover otherrover = initializeDefaultRover(uuid);
		assertThat(rover.hashCode()).isEqualTo(otherrover.hashCode());
	}

	@Test
	public void testToString() {
		Rover rover = new Rover(
				new RoverIdentifier(UUID.fromString("53567a5d-a21c-495e-80a3-d12adaf8585c"), ROVER_NAME),
				new TwoDimensionalCoordinates(3, 4), Orientation.SOUTH);
		assertThat(rover.toString()).isEqualTo(
				"Rover [ROVER_TEST] attached to Plateau [53567a5d-a21c-495e-80a3-d12adaf8585c] with [Coordinates [abscissa = 3, ordinate = 4]] and [Orientation [SOUTH]] and version [0]");
	}

	@Test
	public void testWithNullPosition() {
		Throwable thrown = catchThrowable(
				() -> new Rover(new RoverIdentifier(UUID.randomUUID(), ROVER_NAME), null, Orientation.SOUTH));
		assertThat(thrown).isInstanceOf(IllegalArgumentGameException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ILLEGAL_ARGUMENT_CODE,
						String.format(GameExceptionLabels.PRE_CHECK_ERROR_MESSAGE,
								GameExceptionLabels.MISSING_ROVER_POSITION)));
	}

	@Test
	public void testWithNullOrientation() {
		Throwable thrown = catchThrowable(() -> new Rover(new RoverIdentifier(UUID.randomUUID(), ROVER_NAME),
				new TwoDimensionalCoordinates(3, 4), null));
		assertThat(thrown).isInstanceOf(IllegalArgumentGameException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ILLEGAL_ARGUMENT_CODE,
						String.format(GameExceptionLabels.PRE_CHECK_ERROR_MESSAGE,
								GameExceptionLabels.MISSING_ROVER_ORIENTATION)));
	}

	@Test
	public void testWithNullIdentifier() {
		Throwable thrown = catchThrowable(() -> new Rover(null, new TwoDimensionalCoordinates(3, 4), Orientation.EAST));
		assertThat(thrown).isInstanceOf(IllegalArgumentGameException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.ILLEGAL_ARGUMENT_CODE,
						String.format(GameExceptionLabels.PRE_CHECK_ERROR_MESSAGE,
								GameExceptionLabels.MISSING_ROVER_IDENTIFIER)));
	}

}

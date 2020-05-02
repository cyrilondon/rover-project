package com.game.domain.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import java.util.UUID;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.game.domain.application.command.plateau.PlateauInitializeCommand;
import com.game.domain.application.command.rover.RoverInitializeCommand;
import com.game.domain.application.command.rover.RoverMoveCommand;
import com.game.domain.application.context.GameContext;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;
import com.game.domain.model.entity.rover.Orientation;
import com.game.domain.model.entity.rover.Rover;
import com.game.domain.model.entity.rover.RoverIdentifier;
import com.game.domain.model.event.DomainEventPublisherSubscriber;
import com.game.domain.model.event.store.EventStoreImpl;
import com.game.domain.model.event.subscriber.plateau.PlateauInitializedEventSubscriber;
import com.game.domain.model.event.subscriber.plateau.PlateauInitializedWithExceptionEventSubscriber;
import com.game.domain.model.event.subscriber.plateau.PlateauSwitchedLocationEventSubscriber;
import com.game.domain.model.event.subscriber.rover.RoverInitializedEventSubscriber;
import com.game.domain.model.event.subscriber.rover.RoverInitializedWithExceptionEventSubscriber;
import com.game.domain.model.event.subscriber.rover.RoverMovedEventSubscriber;
import com.game.domain.model.event.subscriber.rover.RoverMovedWithExceptionEventSubscriber;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.exception.PlateauLocationAlreadySetException;
import com.game.domain.model.service.locator.ServiceLocator;
import com.game.test.util.BaseUnitTest;

public class GameServiceImplTest extends BaseUnitTest {

	private static final int WIDTH = 5;

	private static final int HEIGHT = 5;

	private static final int X = 3;

	private static final int Y = 4;

	private GameServiceImpl gameService = new GameServiceImpl();
	
	GameContext context = GameContext.getInstance();

	UUID relativisticUUID = UUID.fromString("53567a5d-a21c-495e-80a3-d12adaf8585c");

	
	private void mockServiceLocator() {
		ServiceLocator mockServiceLocator = new ServiceLocator();
		mockServiceLocator.loadDomainService(ServiceLocator.ROVER_SERVICE, new MockRoverServiceImpl());
		mockServiceLocator.loadDomainService(ServiceLocator.PLATEAU_SERVICE, new MockPlateauServiceImpl());
		mockServiceLocator.loadEventStore(ServiceLocator.EVENT_STORE, new EventStoreImpl());
		ServiceLocator.load(mockServiceLocator);
	}

	@BeforeMethod
	public void resetGame() {
		mockServiceLocator();
		roversList.clear();
		plateau = null;
		DomainEventPublisherSubscriber.instance().clear();
	}

	@Test
	public void testInitializePlateau() {
		UUID uuid  = UUID.randomUUID();
		gameService.execute(new PlateauInitializeCommand.Builder().withId(uuid).withWidth(WIDTH)
				.withHeight(HEIGHT).build());
		assertThat(plateau.getWidth()).isEqualTo(WIDTH);
		assertThat(plateau.getHeight()).isEqualTo(HEIGHT);
		assertThat(DomainEventPublisherSubscriber.getSubscribers().get().size()).isEqualTo(2);
		assertThat(DomainEventPublisherSubscriber.getSubscribers().get().get(0)).isInstanceOf(PlateauInitializedEventSubscriber.class);
		assertThat(DomainEventPublisherSubscriber.getSubscribers().get().get(1)).isInstanceOf(PlateauInitializedWithExceptionEventSubscriber.class);
	}


	@Test
	public void testInitializeRover() {
		UUID uuid = UUID.randomUUID();
		TwoDimensionalCoordinates coordinates = new TwoDimensionalCoordinates(X, Y);
		RoverInitializeCommand initializeCommand = new RoverInitializeCommand.Builder().withPlateauUuid(uuid)
				.withName(GameContext.ROVER_NAME_PREFIX + 1).withAbscissa(coordinates.getAbscissa())
				.withOrdinate(coordinates.getOrdinate()).withOrientation('S').build();
		gameService.execute(initializeCommand);
		assertThat(roversList.contains(new Rover(new RoverIdentifier(uuid, GameContext.ROVER_NAME_PREFIX + 1),
				coordinates, Orientation.SOUTH))).isTrue();
		assertThat(DomainEventPublisherSubscriber.getSubscribers().get().size()).isEqualTo(3);
		assertThat(DomainEventPublisherSubscriber.getSubscribers().get().get(0)).isInstanceOf(RoverInitializedEventSubscriber.class);
		assertThat(DomainEventPublisherSubscriber.getSubscribers().get().get(1)).isInstanceOf(RoverInitializedWithExceptionEventSubscriber.class);
		assertThat(DomainEventPublisherSubscriber.getSubscribers().get().get(2)).isInstanceOf(PlateauSwitchedLocationEventSubscriber.class);
	}

	@Test
	public void testMoveRoverWithOrientation() {
		UUID uuid = UUID.randomUUID();
		String roverName = GameContext.ROVER_NAME_PREFIX + 3;
		gameService.execute(new RoverMoveCommand(new RoverIdentifier(uuid, roverName), 1));
		assertThat(roversList).contains(
				new Rover(new RoverIdentifier(uuid, roverName), new TwoDimensionalCoordinates(2, 3), Orientation.WEST));
		assertThat(DomainEventPublisherSubscriber.getSubscribers().get().size()).isEqualTo(3);
		assertThat(DomainEventPublisherSubscriber.getSubscribers().get().get(0)).isInstanceOf(RoverMovedEventSubscriber.class);
		assertThat(DomainEventPublisherSubscriber.getSubscribers().get().get(1)).isInstanceOf(RoverMovedWithExceptionEventSubscriber.class);
		assertThat(DomainEventPublisherSubscriber.getSubscribers().get().get(2)).isInstanceOf(PlateauSwitchedLocationEventSubscriber.class);
	}

	/**
	 * Here we test that no exception is caught in GameServiceImpl method
	 */
	@Test
	public void testMoveRoverWithOrientationOutOfTheBoard() {
		String roverName = GameContext.ROVER_NAME_PREFIX + 5;
		Throwable thrown = catchThrowable(
				() -> gameService.execute(new RoverMoveCommand(new RoverIdentifier(UUID.randomUUID(), roverName), 1)));
		assertThat(thrown).isInstanceOf(PlateauLocationAlreadySetException.class)
				.hasMessage(String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN,
						GameExceptionLabels.PLATEAU_LOCATION_ERROR_CODE, "Error"));

	}

}

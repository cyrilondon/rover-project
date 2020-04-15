# ROVER Exercise
A squad of robotic rovers are to be landed by NASA on a plateau on Mars. This plateau, which is curiously rectangular, must be navigated by the rovers so that their on-board cameras can get a complete view of the surrounding terrain to send back to Earth.

## Context

The program expects input as follows:

```
5 5
1 2 N
LMLMLMLMM
3 3 E
MMRMMRMRRM
```

The first line of input is the upper-right coordinates of the plateau, the lower-left coordinates are assumed to be 0,0. 

The following input is information pertaining to the rovers that have been deployed, which gives the rover's position, which is made up of two integers and a letter separated by spaces, corresponding to the x and y co-ordinates and the rover's orientation. 

The second line is a series of instructions telling the rover how to explore the plateau and it expects an array of characters, where each character is a command for the robot. 'L' means "turn left", 'R' means "turn right" and 'M' means "move one step towards your direction".


## Objectives

In this exercise, we would like to present a step-by-step process driven by the three following software practices: `Domain and Event Driven Design`, `Hexagonal Architecture` and `Test Driven Design`.

### Domain Driven Design 

Our primary concern, before focusing on the input file parsing, should be to identify the main components of our domain.

<img src="src/main/resources/domain_diagram.png" />




The Domain Driven Design usually recommands to define the following elements:
 
##### Application Services

 An `Application Service` is inside the domain boundary and acts as a facade between the client and the domain. Its responsibilities, among others are:
  - to map the [ApplicationCommand](src/main/java/com/game/domain/application/command/ApplicationCommand.java) objects, i.e. the instructions received from the outside world to the objects understood by the model and to start the execution of the domain logic by calling the `Domain Services`.
  - to acts as a service orchestration by integrating two or more `Domain Services`.
  - to register the required `Event Subscribers` (in case of event-driven applications).
  - to provide security and transaction management (not covered here).
  
 In our case the [GameServiceImpl](src/main/java/com/game/domain/application/GameServiceImpl.java) represents our single `Application Service` and represents the bridge between the File Adapter and the domain.
 
Let us consider below an extract of the GameServiceImpl's method to initialize a rover:
 
 - it takes a [InitializeRoverCommand](src/main/java/com/game/domain/application/command/InitializeRoverCommand.java) and will map this command object to meaningful services arguments, like the rover identifier and the number of moves.
 
 - it registers an `Event Subscriber` to handle a [Domain Event](src/main/java/com/game/domain/model/event/DomainEvent.java) of type [RoverMovedEvent](src/main/java/com/game/domain/model/event/RoverMovedEvent.java)
 
 - it registers another `Event Subscriber` to handle a [Domain Event](src/main/java/com/game/domain/model/event/DomainEvent.java)  of type [RoverMovedWithExceptionEvent](src/main/java/com/game/domain/model/event/RoverMovedWithExceptionEvent.java)
 
 - it finally delegates the action to move the rover a certain number of times to the [RoverServiceImpl](src/main/java/com/game/domain/model/RoverServiceImpl.java).
 
 
 ```java
 public void execute(MoveRoverCommand command) {

		GameContext gameContext = GameContext.getInstance();

		// register the subscriber for the given type of event = RoverMovedEvent
		DomainEventPublisher.instance().subscribe(new RoverMovedEventSubscriber());

		// register the subscriber in case of something went wrong during Rover moves
		DomainEventPublisher.instance().subscribe(new RoverMovedWithExceptionEventSubscriber());

		// delegates to the rover service
		gameContext.getRoverService().moveRoverNumberOfTimes(command.getRoverId(), command.getNumberOfMoves());

	}
 
 ```

##### Domain Services

In contrary to `Application Services`, the `Domain Services` hold domain logic on top of `Domain Entities` and `Value Objects`.

Our domain model includes two `Application Services`

- the [RoverServiceImpl](src/main/java/com/game/domain/model/service/RoverServiceImpl.java) (which implements the interface [RoverService](src/main/java/com/game/domain/model/service/RoverService.java)) dedicated to Rover's operations

- the [PlateauServiceImpl](src/main/java/com/game/domain/model/service/PlateauServiceImpl.java) (which implements the interface [PlateauService](src/main/java/com/game/domain/model/service/PlateauService.java)) dedicated to Plateau entity's operations.

Those services are `stateless` components and are needed every time we need to group various entity methods in a same meaningful business process.

For example, [RoverServiceImpl](src/main/java/com/game/domain/model/service/RoverServiceImpl.java) implements the methods *updateRoverWithPosition* and *updateRoverWithOrientation*, each of them loading, updating and finally saving the Rover. 

Those three distinct Rover's operations together represent an unique operation from a business perspective and thus are exposed as a `Domain Service` method to the `Application Service`.

 ```java
@Override
	
	@Override
	public void updateRoverWithPosition(RoverIdentifier id, TwoDimensionalCoordinates position) {
		Rover rover = this.getRover(id);
		rover.setPosition(position);
		this.updateRover(rover);
	}
	
	@Override
	public void updateRoverWithOrientation(RoverIdentifier id, Orientation orientation) {
		Rover rover = this.getRover(id);
		rover.setOrientation(orientation);
		this.updateRover(rover);
	}
 ```
 
##### Domain Entities

In `Domain Driven Architecture`, we design a domain concept as an `Entity` when we care about its **individuality**, when distinguishing it from all other objects in a system is a mandatory constraint.

An `Entity` is a unique thing and is capable of being changed continuously over a long period of time.
 
Evidently, we can immediately identify a [Rover](src/main/java/com/game/domain/model/entity/Rover.java) as a `Domain Entity` in our application. We do not want to confuse a Rover with another one and we want to keep track of all its moves over the time.

Concerning the Plateau, things become a little bit more interesting. If we had stuck to the requirements, then only one Plateau would have been necessary and thus we would not necessarily model it as an Entity. However, as we have decided that moving rovers over multiple Plateau at the same time was allowed, we have no choice but to model our [Plateau](src/main/java/com/game/domain/model/entity/Rover.java) as entity as well..

### Hexagonal Architecture

<img src="src/main/resources/Rover_hexagonal.png" />

The principles of the `Hexagonal Architecture` have been presented by its author Alistair CockBurn in his original
paper, [https://alistair.cockburn.us/hexagonal-architecture/](https://alistair.cockburn.us/hexagonal-architecture/).

The main goal of this architecture is to isolate, as much as possible, the `Domain Model` we have just built. Technically the model is isolated from the outside world by the so-called `Ports` and `Adapters`.

On the left side of the hexagon, you can find the `Primary Adapters` which are used by the external clients who want to interact with the application. These adapters should not depend directly on the model various implementations but rather on a `Port`, some kind of **facade interface** which hides the model details from the clients.

In our case, the `In Adapter` (another way to design a `Primary Adapter`) is represented by the file adapter
[GameFileAdapter](src/main/java/com/game/adapter/file/GameFileAdapter.java) (pink box) which interacts with the application through its port interface [GameService](src/main/java/com/game/domain/application/GameService.java) (green circle) by sending some [ApplicationCommand](src/main/java/com/game/domain/application/command/ApplicationCommand.java) instructions.


```java

/**
 * Primary adapter as defined by Hexagonal Architecture
 * Clients using a File to start a game should use this adapter as unique contact point to
 * the rest of the application.
 * The bridge with the Domain is done via the  application service interface {@link GameService}
 */
public class GameFileAdapter {

	GameService gameService = GameContext.getInstance().getGameService();

	public void executeGame(File file) {
		gameService.execute(getCommandsFromFile(file));
	}
	...
}
```

You can notice the very light dependency between the [File Adapter](src/main/java/com/game/adapter/file/GameFileAdapter.java) and the [Application Service](src/main/java/com/game/domain/application/GameService.java) (so at the end with the entire model) as it is limited only to the [DomainCommand](src/main/java/com/game/domain/application/command/DomainCommand.java) interface.

```java

/**
 * "Primary" port interface as described by Alistair CockBurn in his original
 * paper, i.e. port on the right side of the hexagon.
 * https://alistair.cockburn.us/hexagonal-architecture/ 
 * The application client will interact with this interface only
 *
 */
public interface GameService extends ApplicationService {
	
	void execute(List<DomainCommand> commands);

}

```

On the right side of the hexagon, the `Secondary Ports` and `Secondary Adapters` define the way the application itself communicates with the outside world. It could be for example how the application is sending events to a middleware or how it is storing its data in a persistent repository.

In this context, the application should NOT depend on those external systems. Instead, it should expose `Ports` or interfaces that need to be implemented by the `Out Adapters`.

In our case, at the end, the rover application will store its <code>Rover</code> and <code>Plateau</code> entities respectively via the [InMemoryRoverRepositoryImpl](src/main/java/com/game/infrastructure/persistence/impl/InMemoryRoverRepositoryImpl.java) (top right pink box on the diagram) and the [InMemoryPlateauRepositoryImpl.java](src/main/java/com/game/infrastructure/persistence/impl/InMemoryPlateauRepositoryImpl.java) (bottom right pink box) but does NOT depend on them directly.

 On the other hand, the application exposes two interfaces or ports [RoverRepository](src/main/java/com/game/domain/model/repository/RoverRepository.java) and [PlateauRepository](src/main/java/com/game/domain/model/repository/PlateauRepository.java)  which are to be implemented by the adapters.
 
```java

/**
 * Domain service dedicated to handle {@link Rover} entity
 *
 */
public class RoverServiceImpl implements RoverService {

	private RoverRepository roverRepository;

	public RoverServiceImpl(RoverRepository roverRepository) {
		this.roverRepository = roverRepository;
	}

	@Override
	public void initializeRover(RoverIdentifier id, TwoDimensionalCoordinates coordinates, Orientation orientation) {
		Rover rover = new Rover(id, coordinates, orientation);
		roverRepository.add(rover.validate());
	}
	
	@Override
	public void turnLeft(RoverIdentifier id) {
		roverRepository.load(id).turnLeft();
	}
	
	...
	
}
```
 
 On the diagram, it is important to notice that **all the arrows are pointing into the direction of the hexagon**  and that none is pointing out from the hexagon, which would reflect an undesired dependency from the application to the external world.
 
 Finally, please note that the `Out Adapters` [InMemoryRoverRepositoryImpl](src/main/java/com/game/infrastructure/persistence/impl/InMemoryRoverRepositoryImpl.java) and [InMemoryPlateauRepositoryImpl.java](src/main/java/com/game/infrastructure/persistence/impl/InMemoryPlateauRepositoryImpl.java) do not belong to the domain and reside in the **infrastructure** package.




### Test driven

From a testing perspective, our goal before to start to code the application was very clear:

- We did not want to use any framework, i.e. no framework for dependency injection and not even a framework for mock testing. The only pre-built framework we have on-boarded is purely 100% dedicated to unit testing assertions: [Test NG](https://testng.org/doc/)


- We expected however a high unit testing coverage rate, at least at 90% of the application should have been tested.



### Exception Handling

The base class of our Exception hierarchy is the <code>[GameException>](src/main/java/com/game/domain/model/exception/GameException.java)</code> which:
- is a of type **RuntimeException** as we don't expect any retry or action from the end user


- takes an **Error code** as constructor argument along the error message for better understanding/readability from the end user

```java
public class GameException extends RuntimeException {
	
	private final String errorCode;
	
	public GameException(String message, String errorCode) {
		this(message, errorCode, null);
	}
	
```
All the error codes and error messages labels are grouped together in a single class <code>[GameExceptionLabels](src/main/java/com/game/domain/model/exception/GameExceptionLabels.java)</code> for better lisibility and overview.

We consider two types of validation:

- A **Technical validation** which checks the nullity or emptiness of arguments. 

   This is handled by the <code>[ArgumentCheck](src/main/java/com/game/core/validation/ArgumentCheck.java)</code> class which throws a <code>[IllegalArgumentGameException](src/main/java/com/game/domain/model/exception/IllegalArgumentGameException.java)</code> with a specific error code <code>[ERR-000]</code> when a required argument is not present or empty.
   
 If you initialize a Rover without giving any position (second argument of the constructor is null), which is clearly wrong,
   
 ```java 
   new Rover(new RoverIdentifier(UUID.randomUUID(),   GameContext.ROVER_NAME_PREFIX), null, Orientation.SOUTH);
   
 ```
 this would be the corresponding stacktrace:

 ```java  
Exception in thread "main" com.game.domain.model.exception.IllegalArgumentGameException: [ERR-000] Broken precondition: Missing Rover position
	at com.game.core.validation.ArgumentCheck.requiresNotNull(ArgumentCheck.java:21)
	at com.game.core.validation.ArgumentCheck.preNotNull(ArgumentCheck.java:17)
	at com.game.domain.model.entity.Rover.<init>(Rover.java:47)
   
 ```
 
 Equivalently if you try to initialize a Rover with only whitespace characters, the <code>ArgumentCheck.requiresNotEmpty</code> check will prevent this action
 
 ```java
 new Rover(new RoverIdentifier(UUID.randomUUID(), "  "), new TwoDimensionalCoordinates(2, 3), Orientation.SOUTH);
 ```
 
 and will throw the following exception
 
  ```java 
Exception in thread "main" com.game.domain.model.exception.IllegalArgumentGameException: [ERR-000] Broken precondition: Missing Rover name
	at com.game.core.validation.ArgumentCheck.requiresNotEmpty(ArgumentCheck.java:30)
	at com.game.core.validation.ArgumentCheck.preNotEmpty(ArgumentCheck.java:26)
	at com.game.domain.model.entity.RoverIdentifier.<init>(RoverIdentifier.java:29)
 ```

- A **Business validation** process which ensures the enforcement of the business rules (by example moving the Rover should not let it go out of the plateau).

The base class <code>[EntityValidator](/src/main/java/com/game/domain/model/validation/EntityValidator.java)</code> takes this responsibility. 

The interesting thing to note here is that this validator class depends on <code>[ValidationNotificationHandler](src/main/java/com/game/domain/model/validation/ValidationNotificationHandler.java)</code> interface by constructor injection.

This delegation to a generic error notification handler - [Strategy pattern](https://en.wikipedia.org/wiki/Strategy_pattern) - is of great interest as we will see further down to ensure distinct validation processes under different contexts.

 ```java
public abstract class EntityValidator<T> {

	private T entity;

	private ValidationNotificationHandler notificationHandler;

	public EntityValidator(T entity, ValidationNotificationHandler handler) {
		super();
		this.notificationHandler = handler;
		this.entity = entity;
	}

	public final T validate() {
		doValidate();
		notificationHandler.checkValidationResult();
		afterValidate();
		return entity();
	}
	

 ```
 
The notification handler interface defines two methods to be implemented:
 
  ```java
 public interface ValidationNotificationHandler {
	
	public void handleError(String errorMessage) ;
	
	public void checkValidationResult();

}

  ```
Let's consider for example the validator class <code>[RoverValidator](src/main/java/com/game/domain/model/entity/RoverValidator.java)</code> dedicated to check that everything is OK after the creation or any action on a Rover.
 
We have a few things to check: the Rover's position X and Y should be both positive, the position X and Y should be on the Plateau to which the Rover belongs and finally no other Rover should be already on this position.
 
   ```java
   public class RoverValidator extends EntityValidator<Rover> {

	public RoverValidator(Rover rover, ValidationNotificationHandler handler) {
		super(rover, handler);
	}

	@Override
	protected void doValidate() {

		if (isXPositionNegative())
			this.notificationHandler()
					.handleError(String.format(GameExceptionLabels.ROVER_NEGATIVE_X, entity().getXPosition()));

		if (isYPositionNegative())
			this.notificationHandler()
					.handleError(String.format(GameExceptionLabels.ROVER_NEGATIVE_Y, entity().getYPosition()));

		if (isXPositionOutOfBoard())
			this.notificationHandler().handleError(String.format(GameExceptionLabels.ROVER_X_OUT_OF_PLATEAU,
					entity().getXPosition(), GameContext.getInstance().getPlateau().getWidth()));

		if (isYPositionOutOfBoard())
			this.notificationHandler().handleError(String.format(GameExceptionLabels.ROVER_Y_OUT_OF_PLATEAU,
					entity().getYPosition(), GameContext.getInstance().getPlateau().getHeight()));

		if (areBothCoordinatesPositive() && areBothCoordinatesInsideTheBoard() && positionAlreadyBusy())
			this.notificationHandler().handleError(String.format(GameExceptionLabels.PLATEAU_LOCATION_ALREADY_SET,
					entity().getXPosition(), entity().getYPosition()));

	}
   
   ```
   These validations should be made both at the time of initialization time and later whenever the Rover is asked to move.
   
   In the first case, it would be a good idea to send ALL the error messages to the end user, so that he can re-send the initialization command successfully next time.
   
   This exactly what the class <code>[EntityDefaultValidationNotificationHandler](src/main/java/com/game/domain/model/validation/EntityDefaultValidationNotificationHandler.java)</code> does:
      
   ```java
   public class EntityDefaultValidationNotificationHandler implements ValidationNotificationHandler {

	protected ValidationResult validationResult = new ValidationResult();

	@Override
	public void handleError(String errorMessage) {
		validationResult.addErrorMessage(errorMessage);
	}

	@Override
	public void checkValidationResult() {
		if (validationResult.isInError()) {
			throw new EntityInitialisationException(validationResult.getAllErrorMessages());
		}
	}
}
  ```
  
 For example, if you try to initialize a Rover with coordinates x=-3 and y=8 attached to a Plateau with the dimensions width = 6 and height = 8, that would be the stacktrace of the exception.
 
 Please not that the error message contains the information of both invalid coordinates. *[ERR-001] Rover X-position [-3] should be strictly positive, Rover with Y-position [8] is out of the Plateau with height [7]*
 
   ```java
 Exception in thread "main" com.game.domain.model.exception.EntityInitialisationException: [ERR-001] Rover X-position [-3] should be strictly positive, Rover with Y-position [8] is out of the Plateau with height [7]
	at com.game.domain.model.validation.EntityDefaultValidationNotificationHandler.checkValidationResult(EntityDefaultValidationNotificationHandler.java:25)
	at com.game.domain.model.validation.EntityValidator.validate(EntityValidator.java:31)
	at com.game.domain.model.entity.Rover.validate(Rover.java:53)
	at com.game.domain.model.entity.Rover.validate(Rover.java:57)
	at com.game.domain.model.service.RoverServiceImpl.initializeRover(RoverServiceImpl.java:28)
	at com.game.domain.application.GameServiceImpl.execute(GameServiceImpl.java:57)
```
   
But let's suppose now that the Rover has been successfully initialized and has to take some moves. Evidently, at each move its position has to be validated by the very same checks but in this new context, we do NOT want to throw an initialization error with the error code [ERR-001].

We would like to throw a different exception, which could be eventually caught by the service layer to take some actions: by example in our case to remove the Rover from the Plateau and to mark its last position on the Plateau as free.

This is very easy thanks to our <code>[ValidationNotificationHandler](src/main/java/com/game/domain/model/validation/ValidationNotificationHandler.java)</code> interface: we just need to inject another implementation <code>[RoverMovedPositionValidationNotificationHandler](src/main/java/com/game/domain/model/validation/RoverMovedPositionValidationNotificationHandler.java)</code>, which in case of a wrong move will throw this time a <code>[IllegalRoverMoveException](src/main/java/com/game/domain/model/exception/IllegalRoverMoveException.java)</code> exception, with error code <code>[ERR-004]</code>.

Below is the exception stacktrace when a Rover asked to move few steps will end up going out of the Plateau. We notice that the error message is exactly the same as above (concerning the Y-position out of the plateau), but the type of exception as well as the error code have changed: *com.game.domain.model.exception.IllegalRoverMoveException: [ERR-004] Rover with Y-position [7] is out of the Plateau with height [6]*

  ```java
com.game.domain.model.exception.IllegalRoverMoveException: [ERR-004] Rover with Y-position [7] is out of the Plateau with height [6]
	at com.game.domain.model.validation.RoverMovedPositionValidationNotificationHandler.checkValidationResult(RoverMovedPositionValidationNotificationHandler.java:16)
	at com.game.domain.model.validation.EntityValidator.validate(EntityValidator.java:31)
	at com.game.domain.model.entity.Rover.validate(Rover.java:53)
	at com.game.domain.model.entity.Rover.lambda$new$0(Rover.java:30)
	at com.game.domain.model.entity.IdentifiedDomainEntity.applyAndPublishEvent(IdentifiedDomainEntity.java:19)
	at com.game.domain.model.entity.Rover.moveVertically(Rover.java:142)
	at com.game.domain.model.entity.Rover.moveNorth(Rover.java:108)
	at com.game.domain.model.entity.Rover.lambda$moveNorthNumberOfTimes$2(Rover.java:85)
	at java.base/java.util.stream.Streams$RangeIntSpliterator.forEachRemaining(Streams.java:104)
	at java.base/java.util.stream.IntPipeline$Head.forEach(IntPipeline.java:593)
	at com.game.domain.model.entity.Rover.moveNorthNumberOfTimes(Rover.java:84)
	at com.game.domain.model.entity.Rover.moveNumberOfTimes(Rover.java:67)
	
```

## Quick start

1. Download and install [maven](http://maven.apache.org/install.html).
2. Go to the root of the project and type `mvn clean install`. This will build the project.
3. In order to run the project, not yet ready....;-)

Enjoy! :smiley:  (currently rather :mask:)


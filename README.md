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

The following input is information pertaining to the rovers that have been deployed, wich gives the rover's position, wich is made up of two integers and a letter separated by spaces, corresponding to the x and y co-ordinates and the rover's orientation. 

The second line is a series of instructions telling the rover how to explore the plateau and it expects an array of characters, where each character is a command for the robot. 'L' means "turn left", 'R' means "turn right" and 'M' means "move one step towards your direction".


## Objectives

In this exercise, we would like to present a step-by-step process driven by the three following software practices

### Domain Driven Design 
We start by focusing on our domain (entities) and services, which both represent the very core of our application.
If you go through our commits one after the other, you will notice that designing and properly testing our entities is our very first concern.

### Hexagonal Architecture

<img src="src/main/resources/Rover_hexagonal.png" />

The principles of the Hexagonal Architecture have been presented by its author Alistair CockBurn in his original
paper, [https://alistair.cockburn.us/hexagonal-architecture/](https://alistair.cockburn.us/hexagonal-architecture/).

The main goal of this architecture is to isolate as much as possible the domain model we have just built. Technically the model is isolated from the outside world by the so called **ports** and **adapters**.

On the left side of the hexagon, you can find the **primary adapters** which are used by the external clients who want to interact with the application. These adapters should not depend directly on the model details but only to a **port**, some kind of **facade interface** which hides the model implementation details to the clients.

In our case, the **in-adapter** (another way to design a primary adapter) is represented by the file adapter
[GameFileAdapter](src/main/java/com/game/adapter/file/GameFileAdapter.java) which interacts with the rover application by its port interface [GameService](src/main/java/com/game/domain/application/GameService.java).

On the right side of the hexagon, the **secondary ports** and **secondary adapters** define the way the application itself communicates with the outside world. It could be by example how the application is sending events to a middleware or how it is storing its data in a persistent repository.

In this context, the application should NOT depend on those external systems but in contrast exposes some ports or interfaces (the secondary or out ports) to be implemented by the adapters.

In our case, the rover application will store its <code>Rover</code> and <code>Plateau</code> entities respectively via the [InMemoryRoverRepositoryImpl](src/main/java/com/game/infrastructure/persistence/impl/InMemoryRoverRepositoryImpl.java) and the [InMemoryPlateauRepositoryImpl.java](src/main/java/com/game/infrastructure/persistence/impl/InMemoryPlateauRepositoryImpl.java) but does NOT depend on them directly.

 On the other hand, the application exposes two interfaces or ports [RoverRepository](src/main/java/com/game/domain/model/repository/RoverRepository.java) and [PlateauRepository](src/main/java/com/game/domain/model/repository/PlateauRepository.java)  which are to be implemented by the adapters.
 
 On the diagram, it is important that **all the arrows are pointing into the direction of the hexagon**  and that none is pointing out from the hexagon, which would mean an undesired dependency from the application to the external world.
 
 Finally, please note that the out-adapters <code>InMemoryRoverRepositoryImpl</code> and <code>InMemoryPlateauRepositoryImpl</code> do not belong to the domain and reside in the **infrastructure** package.




### Test driven
Our goal is to propose a final project covered at least at 90% by unit testing.

### No framework
We intentionally don't use any framework in this work, i.e. no framework for dependency injection and not even a framework for mock testing.

### Exception Handling

The base class of our Exception hierarchy is the <code>[GameException>](src/main/java/com/game/domain/model/exception/GameException.java)</code> which:
- is a of type **RuntimeException** as we don't expect any retry or action from the end user
- takes an **error code** as constructor argument along the error message for better understanding/lisibility from the end user

```
public class GameException extends RuntimeException {
	
	private final String errorCode;
	
	public GameException(String message, String errorCode) {
		this(message, errorCode, null);
	}
	
```
All the error codes and error messages labels are grouped together in a single class <code>[GameExceptionLabels](src/main/java/com/game/domain/model/exception/GameExceptionLabels.java)</code> for better lisibility and overview.

We consider two types of validation:

- A **Technical validation** which checks the nullity or emptiness of arguments. 

   This is handled by the <code>[ArgumentCheck](src/main/java/com/game/core/validation/ArgumentCheck.java)</code> class which throws a <code>[IllegalArgumentGameException](src/main/java/com/game/domain/model/exception/IllegalArgumentGameException.java)</code> with a specific error code <code>[ERR-000]</code> in case of a non present required argument.
   
   Say we try to initialize a Rover without giving any position (second argument of the constructor is null), which is clearly wrong
   
 ```  
   new Rover(new RoverIdentifier(UUID.randomUUID(),   GameContext.ROVER_NAME_PREFIX), null, Orientation.SOUTH);
   
 ```
 This would be the corresponding stacktrace:

 ```  
Exception in thread "main" com.game.domain.model.exception.IllegalArgumentGameException: [ERR-000] Broken precondition: Missing Rover position
	at com.game.core.validation.ArgumentCheck.requiresNotNull(ArgumentCheck.java:21)
	at com.game.core.validation.ArgumentCheck.preNotNull(ArgumentCheck.java:17)
	at com.game.domain.model.entity.Rover.<init>(Rover.java:47)
   
 ```
 
 Equivalently if you try to initialize a Rover with only whitespace characters, the <code>ArgumentCheck.requiresNotEmpty</code> check will prevent this action
 
 ``` 
 new Rover(new RoverIdentifier(UUID.randomUUID(), "  "), new TwoDimensionalCoordinates(2, 3), Orientation.SOUTH);
 ```
 
 and will throw the following exception
 
  ``` 
Exception in thread "main" com.game.domain.model.exception.IllegalArgumentGameException: [ERR-000] Broken precondition: Missing Rover name
	at com.game.core.validation.ArgumentCheck.requiresNotEmpty(ArgumentCheck.java:30)
	at com.game.core.validation.ArgumentCheck.preNotEmpty(ArgumentCheck.java:26)
	at com.game.domain.model.entity.RoverIdentifier.<init>(RoverIdentifier.java:29)
 ```

- A **Business validation** process which ensures the enforcement of the business rules (by example moving the Rover should not let it go out of the plateau).

The base class <code>[EntityValidator](/src/main/java/com/game/domain/model/validation/EntityValidator.java)</code> takes this responsibility. 

The interesting thing to note here is that this validator class depends on <code>[ValidationNotificationHandler](src/main/java/com/game/domain/model/validation/ValidationNotificationHandler.java)</code> interface by constructor injection.

This delegation to a generic error notification handler - [Strategy pattern](https://en.wikipedia.org/wiki/Strategy_pattern) - is of great interest as we will see further down to ensure distinct validation processes under different contexts.

 ```
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
 
  ```
 public interface ValidationNotificationHandler {
	
	public void handleError(String errorMessage) ;
	
	public void checkValidationResult();

}

  ```
  
 Let's consider by example the validator class <code>[RoverValidator](src/main/java/com/game/domain/model/entity/RoverValidator.java)</code> dedicated to check that everything is OK after the creation or any action on a Rover.
 
 We have few things to check: the Rover's position X and Y should be both positive, the position X and Y should be inside the Plateau to which the Rover belongs and finally no other Rover should be already on this position.
 
   ```
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
   This validation should happen both at initialization time and later on when the Rover is asked to move.
   
   In the first case, it would be a good idea to send ALL the error messages to the end user, so that he can re-send the initialization command successfully next time.
   
   This exactly what the class <code>[EntityDefaultValidationNotificationHandler](src/main/java/com/game/domain/model/validation/EntityDefaultValidationNotificationHandler.java)</code> does:
      
   ```
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
 
   ```
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

  ```
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


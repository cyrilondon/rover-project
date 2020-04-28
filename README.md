# ROVER Exercise
A squad of robotic rovers are to be landed by NASA on a plateau on Mars. 

This plateau, which is curiously rectangular, must be navigated by the rovers so that their on-board cameras can get a complete view of the surrounding terrain to send back to Earth.

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

In this exercise, we would like to present a step-by-step process driven by the four following software practices: `Domain-Driven Design`, `Event-Driven Design`, `Hexagonal Architecture` and `Test Driven Design`.

All those practices are implemented manually and do not depend on any built-in framework except for unit testing (you can notice that the only dependencies present in the maven [pom.xml](pom.xml) are [Test NG](https://testng.org/doc/) and [AssertJ](https://assertj.github.io/doc/) for facilitating Unit testing assertions.

Furthermore, in addition to the initial requirements, this implementation offers the following extra-features:

- possibility to play on many plateaus at the same time.
- persistence of current state of all plateaus and attached rovers.
- persistence of all the events which occurred in the model in a dedicated event store.
- finer business exception handling with all the scenarii handled (rover moves out of the plateau, rover collides with another rover, rover wrongly initialized, etc.).
- possibility to send commands from any client (not limited to file parsing).
- possibility to create a relativistic plateau, i.e. whose dimensions obey Einstein's special relativity rules (more accurate for observers/NASA engineers moving close to speed of light).
- possibility to send commands in parallel, even for the same rover (concurrency handled by optimistic locking)
- possibility for the plateau to make the rover move with a step greater than one (default value)


## Quick start

1. Download and install [maven](http://maven.apache.org/install.html).
2. Go to the root of the project and type `mvn clean package`. This will build the project, run the Unit Tests along with the [GameIntegration](src/main/java/com/game/adapter/file/GameIntegration.java) `Main` method.

You should see something similar to below extract:

Not only are we printing out the final position of both rover with their respective version number ( the number of actions that each rover has undergone)

```java
Persistent Rover: Rover [ROVER_1] attached to Plateau [911986fc-5e18-4cb9-9dfc-3679a13fda19] with [Coordinates [abscissa = 1, ordinate = 3]] and [Orientation [NORTH]] and version [9]
Persistent Rover: Rover [ROVER_2] attached to Plateau [911986fc-5e18-4cb9-9dfc-3679a13fda19] with [Coordinates [abscissa = 5, ordinate = 1]] and [Orientation [EAST]] and version [10]
```

but we are showing the state of the persistent plateau as well:

```java
Persistent Plateau [911986fc-5e18-4cb9-9dfc-3679a13fda19] occupied at coordinates [Coordinates [abscissa = 1, ordinate = 3]]? [true]
Persistent Plateau [911986fc-5e18-4cb9-9dfc-3679a13fda19] occupied at coordinates [Coordinates [abscissa = 5, ordinate = 1]]? [true]
```
and more importantly all the stored `Domain Events` which captured any occurrence of something that happened in the `Model`.

```java
PlateauInitializedEvent published at [2020-04-27T14:49:36.336] with plateau id [911986fc-5e18-4cb9-9dfc-3679a13fda19], dimensions [Dimensions width [5] and height [5]]
RoverInitializedEvent published at [2020-04-27T14:49:36.368] with rover id [Name [ROVER_1] - Plateau UUID [911986fc-5e18-4cb9-9dfc-3679a13fda19]], position [Coordinates [abscissa = 1, ordinate = 2]], orientation [Orientation [NORTH]]
PlateauSwitchedLocationEvent published at [2020-04-27T14:49:36.377] with plateau id [911986fc-5e18-4cb9-9dfc-3679a13fda19], position released [null], position occupied [Coordinates [abscissa = 1, ordinate = 2]]
RoverTurnedEvent published at [2020-04-27T14:49:36.384] with rover id [Rover [ROVER_1] attached to Plateau [911986fc-5e18-4cb9-9dfc-3679a13fda19] with version [0]], previous orientation [Orientation [NORTH]], current orientation [Orientation [WEST]]
RoverMovedEvent published at [2020-04-27T14:49:36.394] with rover id [Rover [ROVER_1] attached to Plateau [911986fc-5e18-4cb9-9dfc-3679a13fda19] with version [1]], previous position [Coordinates [abscissa = 1, ordinate = 2]], current position [Coordinates [abscissa = 0, ordinate = 2]]
PlateauSwitchedLocationEvent published at [2020-04-27T14:49:36.398] with plateau id [911986fc-5e18-4cb9-9dfc-3679a13fda19], position released [Coordinates [abscissa = 1, ordinate = 2]], position occupied [Coordinates [abscissa = 0, ordinate = 2]]
RoverTurnedEvent published at [2020-04-27T14:49:36.398] with rover id [Rover [ROVER_1] attached to Plateau [911986fc-5e18-4cb9-9dfc-3679a13fda19] with version [2]], previous orientation [Orientation [WEST]], current orientation [Orientation [SOUTH]]
RoverMovedEvent published at [2020-04-27T14:49:36.398] with rover id [Rover [ROVER_1] attached to Plateau [911986fc-5e18-4cb9-9dfc-3679a13fda19] with version [3]], previous position [Coordinates [abscissa = 0, ordinate = 2]], current position [Coordinates [abscissa = 0, ordinate = 1]]
PlateauSwitchedLocationEvent published at [2020-04-27T14:49:36.399] with plateau id [911986fc-5e18-4cb9-9dfc-3679a13fda19], position released [Coordinates [abscissa = 0, ordinate = 2]], position occupied [Coordinates [abscissa = 0, ordinate = 1]]
RoverTurnedEvent published at [2020-04-27T14:49:36.399] with rover id [Rover [ROVER_1] attached to Plateau [911986fc-5e18-4cb9-9dfc-3679a13fda19] with version [4]], previous orientation [Orientation [SOUTH]], current orientation [Orientation [EAST]]
RoverMovedEvent published at [2020-04-27T14:49:36.399] with rover id [Rover [ROVER_1] attached to Plateau [911986fc-5e18-4cb9-9dfc-3679a13fda19] with version [5]], previous position [Coordinates [abscissa = 0, ordinate = 1]], current position [Coordinates [abscissa = 1, ordinate = 1]]
PlateauSwitchedLocationEvent published at [2020-04-27T14:49:36.399] with plateau id [911986fc-5e18-4cb9-9dfc-3679a13fda19], position released [Coordinates [abscissa = 0, ordinate = 1]], position occupied [Coordinates [abscissa = 1, ordinate = 1]]
RoverTurnedEvent published at [2020-04-27T14:49:36.399] with rover id [Rover [ROVER_1] attached to Plateau [911986fc-5e18-4cb9-9dfc-3679a13fda19] with version [6]], previous orientation [Orientation [EAST]], current orientation [Orientation [NORTH]]
RoverMovedEvent published at [2020-04-27T14:49:36.399] with rover id [Rover [ROVER_1] attached to Plateau [911986fc-5e18-4cb9-9dfc-3679a13fda19] with version [7]], previous position [Coordinates [abscissa = 1, ordinate = 1]], current position [Coordinates [abscissa = 1, ordinate = 2]]
PlateauSwitchedLocationEvent published at [2020-04-27T14:49:36.399] with plateau id [911986fc-5e18-4cb9-9dfc-3679a13fda19], position released [Coordinates [abscissa = 1, ordinate = 1]], position occupied [Coordinates [abscissa = 1, ordinate = 2]]
RoverMovedEvent published at [2020-04-27T14:49:36.399] with rover id [Rover [ROVER_1] attached to Plateau [911986fc-5e18-4cb9-9dfc-3679a13fda19] with version [8]], previous position [Coordinates [abscissa = 1, ordinate = 2]], current position [Coordinates [abscissa = 1, ordinate = 3]]
PlateauSwitchedLocationEvent published at [2020-04-27T14:49:36.399] with plateau id [911986fc-5e18-4cb9-9dfc-3679a13fda19], position released [Coordinates [abscissa = 1, ordinate = 2]], position occupied [Coordinates [abscissa = 1, ordinate = 3]]
RoverInitializedEvent published at [2020-04-27T14:49:36.399] with rover id [Name [ROVER_2] - Plateau UUID [911986fc-5e18-4cb9-9dfc-3679a13fda19]], position [Coordinates [abscissa = 3, ordinate = 3]], orientation [Orientation [EAST]]
PlateauSwitchedLocationEvent published at [2020-04-27T14:49:36.400] with plateau id [911986fc-5e18-4cb9-9dfc-3679a13fda19], position released [null], position occupied [Coordinates [abscissa = 3, ordinate = 3]]
RoverMovedEvent published at [2020-04-27T14:49:36.400] with rover id [Rover [ROVER_2] attached to Plateau [911986fc-5e18-4cb9-9dfc-3679a13fda19] with version [0]], previous position [Coordinates [abscissa = 3, ordinate = 3]], current position [Coordinates [abscissa = 4, ordinate = 3]]
PlateauSwitchedLocationEvent published at [2020-04-27T14:49:36.400] with plateau id [911986fc-5e18-4cb9-9dfc-3679a13fda19], position released [Coordinates [abscissa = 3, ordinate = 3]], position occupied [Coordinates [abscissa = 4, ordinate = 3]]
RoverMovedEvent published at [2020-04-27T14:49:36.400] with rover id [Rover [ROVER_2] attached to Plateau [911986fc-5e18-4cb9-9dfc-3679a13fda19] with version [1]], previous position [Coordinates [abscissa = 4, ordinate = 3]], current position [Coordinates [abscissa = 5, ordinate = 3]]
PlateauSwitchedLocationEvent published at [2020-04-27T14:49:36.400] with plateau id [911986fc-5e18-4cb9-9dfc-3679a13fda19], position released [Coordinates [abscissa = 4, ordinate = 3]], position occupied [Coordinates [abscissa = 5, ordinate = 3]]
RoverTurnedEvent published at [2020-04-27T14:49:36.400] with rover id [Rover [ROVER_2] attached to Plateau [911986fc-5e18-4cb9-9dfc-3679a13fda19] with version [2]], previous orientation [Orientation [EAST]], current orientation [Orientation [SOUTH]]
RoverMovedEvent published at [2020-04-27T14:49:36.400] with rover id [Rover [ROVER_2] attached to Plateau [911986fc-5e18-4cb9-9dfc-3679a13fda19] with version [3]], previous position [Coordinates [abscissa = 5, ordinate = 3]], current position [Coordinates [abscissa = 5, ordinate = 2]]
PlateauSwitchedLocationEvent published at [2020-04-27T14:49:36.400] with plateau id [911986fc-5e18-4cb9-9dfc-3679a13fda19], position released [Coordinates [abscissa = 5, ordinate = 3]], position occupied [Coordinates [abscissa = 5, ordinate = 2]]
RoverMovedEvent published at [2020-04-27T14:49:36.400] with rover id [Rover [ROVER_2] attached to Plateau [911986fc-5e18-4cb9-9dfc-3679a13fda19] with version [4]], previous position [Coordinates [abscissa = 5, ordinate = 2]], current position [Coordinates [abscissa = 5, ordinate = 1]]
PlateauSwitchedLocationEvent published at [2020-04-27T14:49:36.400] with plateau id [911986fc-5e18-4cb9-9dfc-3679a13fda19], position released [Coordinates [abscissa = 5, ordinate = 2]], position occupied [Coordinates [abscissa = 5, ordinate = 1]]
RoverTurnedEvent published at [2020-04-27T14:49:36.400] with rover id [Rover [ROVER_2] attached to Plateau [911986fc-5e18-4cb9-9dfc-3679a13fda19] with version [5]], previous orientation [Orientation [SOUTH]], current orientation [Orientation [WEST]]
RoverMovedEvent published at [2020-04-27T14:49:36.400] with rover id [Rover [ROVER_2] attached to Plateau [911986fc-5e18-4cb9-9dfc-3679a13fda19] with version [6]], previous position [Coordinates [abscissa = 5, ordinate = 1]], current position [Coordinates [abscissa = 4, ordinate = 1]]
PlateauSwitchedLocationEvent published at [2020-04-27T14:49:36.401] with plateau id [911986fc-5e18-4cb9-9dfc-3679a13fda19], position released [Coordinates [abscissa = 5, ordinate = 1]], position occupied [Coordinates [abscissa = 4, ordinate = 1]]
RoverTurnedEvent published at [2020-04-27T14:49:36.401] with rover id [Rover [ROVER_2] attached to Plateau [911986fc-5e18-4cb9-9dfc-3679a13fda19] with version [7]], previous orientation [Orientation [WEST]], current orientation [Orientation [NORTH]]
RoverTurnedEvent published at [2020-04-27T14:49:36.401] with rover id [Rover [ROVER_2] attached to Plateau [911986fc-5e18-4cb9-9dfc-3679a13fda19] with version [8]], previous orientation [Orientation [NORTH]], current orientation [Orientation [EAST]]
RoverMovedEvent published at [2020-04-27T14:49:36.401] with rover id [Rover [ROVER_2] attached to Plateau [911986fc-5e18-4cb9-9dfc-3679a13fda19] with version [9]], previous position [Coordinates [abscissa = 4, ordinate = 1]], current position [Coordinates [abscissa = 5, ordinate = 1]]
PlateauSwitchedLocationEvent published at [2020-04-27T14:49:36.401] with plateau id [911986fc-5e18-4cb9-9dfc-3679a13fda19], position released [Coordinates [abscissa = 4, ordinate = 1]], position occupied [Coordinates [abscissa = 5, ordinate = 1]]

```

As well all know as programmers, edge cases are as much as important as the normal flow in a program.

That's why you can test as well a few exception scenarii, when for example `Rovers` collide or move out of the `Plateau` by running the different methods of [GameIntegrationExceptionTest](src/test/java/com/game/integration/GameIntegrationExceptionTest.java).


For example, when running the *simulateRoverMovesOutPlateau* method, you should get a [GameException](src/main/java/com/game/domain/model/exception/GameException.java), showing:
- the details of the [RoverMovedWithExceptionEvent](src/main/java/com/game/domain/model/event/rover/RoverMovedWithExceptionEvent.java) during which the exception occured
- along with the root exception [IllegalRoverMoveException](src/main/java/com/game/domain/model/exception/IllegalRoverMoveException.java), and its error message `[ERR-004] Rover with Y-position [3] is out of the Plateau with height [2]]`

```java
com.game.domain.model.exception.GameException: 
RoverMovedWithExceptionEvent published at [2020-04-26T09:15:51.654] 
with Rover Moved Event [RoverMovedEvent published at [2020-04-26T09:15:51.650] 
with rover id [Rover [ROVER_1] attached to Plateau [b2740710-c027-4834-9858-6c4518b0b8be] with version [1]],
 previous position [Coordinates [abscissa = 1, ordinate = 2]], 
 current position [Coordinates [abscissa = 1, ordinate = 3]]], 
 exception [com.game.domain.model.exception.IllegalRoverMoveException: [ERR-004] Rover with Y-position [3] is out of the Plateau with height [2]]
```

Another possible exception to simulate would be to send commands to a `Rover` concurrently by two end users, which would lead to a [GameException](src/main/java/com/game/domain/model/exception/GameException.java) again, caused by [OptimisticLockingException](src/main/java/com/game/domain/model/exception/OptimisticLockingException.java) specified by an error code `[ERR-005]`.

```java
Exception in thread "pool-1-thread-1" com.game.domain.model.exception.GameException:
 [ERR-005] Someone is trying to update the Rover [Rover [ROVER_1] attached to Plateau [41b6214d-da46-461a-8eae-dbc4c726f09a] 
 with [Coordinates [abscissa = 1, ordinate = 4]] and [Orientation [EAST]] and version [3]] 
 at the same time. Please try again.
 ..
 ..
 Caused by: com.game.domain.model.exception.OptimisticLockingException: [ERR-005] Someone is trying to update the Rover [Rover [ROVER_1] attached to Plateau [41b6214d-da46-461a-8eae-dbc4c726f09a] with [Coordinates [abscissa = 1, ordinate = 4]] and [Orientation [EAST]] and version [3]] at the same time. Please try again.
 ```
 
Finally, if you want to have a better understanding of the application, you can go through the following documentation as well, focusing on those different topics:

- Domain-Driven Design

- Hexagonal Architecture

- Event-Driven Architecture

- Test Driven Design

- Validation and Exception Handling

- Concurrency and Optimistic Locking

- Design Patterns


### Domain Driven Design 

> Domain-driven design (DDD) is an approach to software development placing the project's primary focus on the core domain and domain logic, which aims at connecting the implementation to an evolving model. The term was coined by Eric Evans in his book of the same title.

Our primary concern, before focusing on the input file parsing, should be to identify the main components of our domain.

<img src="src/main/resources/domain_diagram.png" />




The Domain Driven Design usually recommends to define the following elements:
 
##### Application Services

 An `Application Service` is inside the domain boundary and acts as a facade between the client and the domain. Its responsibilities, among others are:
  - to map the [ApplicationCommand](src/main/java/com/game/domain/application/command/ApplicationCommand.java) objects, i.e. the instructions received from the outside world to the objects understood by the model and to start the execution of the domain logic by calling the `Domain Services`.
  - to acts as a service orchestration by integrating two or more `Domain Services`.
  - to register the required `Event Subscribers` (in case of event-driven applications).
  - to provide security and transaction management (not covered here).
  
 In our case the [GameServiceImpl](src/main/java/com/game/domain/application/service/GameServiceImpl.java) represents our single `Application Service` and represents the bridge between the File Adapter and the domain.
 
Let us consider below an extract of the GameServiceImpl's method *execute(RoverMoveCommand command)* to move a rover:
 
 - it takes a [RoverMoveCommand](src/main/java/com/game/domain/application/command/rover/RoverMoveCommand.java) and will map this command object to meaningful services arguments, like the rover identifier and the number of moves.
 
 - it registers a few `Domain Event` subscribers (more on this in the section `Event-Driven Architecture`).
 
 - it finally delegates the action to move the rover a certain number of times to the [RoverServiceImpl](src/main/java/com/game/domain/model/service/rover/RoverServiceImpl.java) via the method *moveRoverNumberOfTimes(roverId, numberOfMoves)*
 
 
 ```java

	void execute(RoverMoveCommand command) {

		// register the subscriber for the given type of event = RoverMovedEvent
		DomainEventPublisher.instance().subscribe(new RoverMovedEventSubscriber());

		// register the subscriber in case of something went wrong during Rover moves
		DomainEventPublisher.instance().subscribe(new RoverMovedWithExceptionEventSubscriber());
		
		// register the subscriber for the plateau
		DomainEventPublisher.instance().subscribe(new PlateauSwitchedLocationEventSubscriber());

		// delegates to the rover service
		GameContext.getInstance().getRoverService().moveRoverNumberOfTimes(command.getRoverId(), command.getNumberOfMoves());

	}
 
 ```

##### Domain Services

In contrary to `Application Services`, the `Domain Services` hold domain logic on top of `Domain Entities` and `Value Objects`.

Our domain model includes two `Application Services`

- the [RoverServiceImpl](src/main/java/com/game/domain/model/service/rover/RoverServiceImpl.java) (which implements the interface [RoverService](src/main/java/com/game/domain/model/service/rover/RoverService.java)) dedicated to Rover's operations

- the [PlateauServiceImpl](src/main/java/com/game/domain/model/service/plateau/PlateauServiceImpl.java) (which implements the interface [PlateauService](src/main/java/com/game/domain/model/service/plateau/PlateauService.java)) dedicated to Plateau entity's operations.

Those services are `stateless` components and are needed every time we need to group various entity methods in a same meaningful business process.

For example, [RoverServiceImpl](src/main/java/com/game/domain/model/service/rover/RoverServiceImpl.java) implements the methods *updateRoverWithPosition* and *updateRoverWithOrientation*, each of them loading, checking its version, updating its position or its orientation and finally saving the Rover. 

Those four distinct Rover's operations together represent an unique operation from a business perspective and thus are exposed as a `Domain Service` method to the `Application Service`.

 ```java
@Override
	public void updateRoverWithPosition(RoverIdentifierDto roverId, TwoDimensionalCoordinates position) {
		Rover rover = loadAndCheckRover(roverId);
		rover.setPosition(position);
		this.updateRover(rover);
	}

	@Override
	public void updateRoverWithOrientation(RoverIdentifierDto roverId, Orientation orientation) {
		Rover rover = loadAndCheckRover(roverId);
		rover.setOrientation(orientation);
		this.updateRover(rover);
	}
	
	private Rover loadAndCheckRover(RoverIdentifierDto roverId) {
		Rover rover = this.getRover(roverId.getId());
		rover.checkAgainstVersion(roverId.getVersion());
		return rover;
	}

 ```
##### Service Locator

The `Application Service` [GameServiceImpl](src/main/java/com/game/domain/application/service/GameServiceImpl.java) has access to `Domain Services` via a [Service Locator](src/main/java/com/game/domain/model/service/locator/ServiceLocator.java).

To get a very good insight into the `Service Locator` pattern you can read this article from the guru Martin Fowler [Inversion of Control Containers and the Dependency Injection pattern](https://martinfowler.com/articles/injection.html).

As we don't want to use any external framework, it is much easier to use the `Service Locator` compared to the `Dependency Injection` for wiring the `Domain Services` dependencies into the `Application Service`.

 ```java
 
 public class ServiceLocator {
 
 
	private Map<String, DomainService> domainServices = new HashMap<>();

	private Map<String, ApplicationService> applicationServices = new HashMap<>();
	
	private Map<String, EventStore> eventStore = new HashMap<>();

	private static ServiceLocator soleInstance = new ServiceLocator();
	
	public static GameService getGameService() {
		return (GameService) soleInstance.applicationServices.get(GAME_SERVICE);
	}

	public static RoverService getRoverService() {
		return (RoverService) soleInstance.domainServices.get(ROVER_SERVICE);
	}

	public static PlateauService getPlateauService() {
		return (PlateauService) soleInstance.domainServices.get(PLATEAU_SERVICE);
	}
	
	public static EventStore getEventStore() {
		return (EventStore) soleInstance.eventStore.get(EVENT_STORE);
	}

	public static void load(ServiceLocator arg) {
		soleInstance = arg;
	} 
	
	...
```

More precisely, the `Service Locator` is used by a [GameContext](src/main/java/com/game/domain/application/context/GameContext.java) which emulates an `Application Context` as provided by Spring for example.

The `Service Locator` provides a very useful *load* method which is of first interest for testing purpose as expained in Martin Fowler's article and as we are detailing in the section dedicated to Unit Testing.

 ```java
 
public class GameContext {

	private GameContext() {
		configure();
	}

	/**
	 * Configure the game with the on-demand implementations
	 */
	private void configure() {
		ServiceLocator locator = new ServiceLocator();
		locator.loadApplicationService(ServiceLocator.GAME_SERVICE, new GameServiceImpl());
		PlateauService plateauService = new PlateauServiceImpl(new InMemoryPlateauRepositoryImpl());
		locator.loadDomainService(ServiceLocator.PLATEAU_SERVICE, plateauService);
		locator.loadDomainService(ServiceLocator.ROVER_SERVICE, new RoverServiceImpl(plateauService , new InMemoryRoverRepositoryImpl()));
		locator.loadEventStore(ServiceLocator.EVENT_STORE, new EventStoreImpl());
		ServiceLocator.load(locator);
	}
	
  ...

 ```
 
##### Domain Entities


<img src="src/main/resources/entity_diagram_new.png" />

In `Domain Driven Architecture`, we design a domain concept as an `Entity` when we care about its **individuality**, when distinguishing it from all other objects in a system is a mandatory constraint.

An `Entity` is a **unique thing** and is capable of being changed continuously over a long period of time.

Evidently, we can immediately identify a [Rover](src/main/java/com/game/domain/model/entity/rover/Rover.java) as a `Domain Entity` in our application. We do not want to confuse a Rover with another one and we want to keep track of all its moves over the time.

Concerning the Plateau, things become a little bit more interesting. If we had stuck to the requirements, then only one Plateau would have been necessary and thus we would not have necessarily modeled it as an `Entity`. However, as we have decided that moving rovers over multiple Plateaus at the same time was allowed, we have no choice but to model our [Plateau](src/main/java/com/game/domain/model/entity/plateau/Plateau.java) as an `Entity` as well.

As identifiable `Entities`, both [Rover](src/main/java/com/game/domain/model/entity/rover/Rover.java) and  [Plateau](src/main/java/com/game/domain/model/entity/plateau/Plateau.java) inherits from [IdentifiedPublisherDomainEntity](src/main/java/com/game/domain/model/entity/IdentifiedPublisherDomainEntity.java), which itself implements the interface [Entity](src/main/java/com/game/domain/model/entity/Entity.java).

Actually, it inherits indirectly from `Entity` via  [ConcurrencySafeEntity](src/main/java/com/game/domain/model/entity/ConcurrencySafeEntity.java) interface but will dig into this subject later on in the section dedicated to concurrency and optimistic locking. 


 ```java
public abstract class IdentifiedPublisherDomainEntity<T, U> extends BaseDomainEventPublisher implements ConcurrencySafeEntity<T, U> {

	protected U id;
	
	protected int version;

	@Override
	public int getVersion() {
		return version;
	}
	
	@Override
	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public U getId() {
		return this.id;
	}

}

 ```

The interface [Entity](src/main/java/com/game/domain/model/entity/Entity.java) exposes the *getId()* method to enforce each entity class to define and expose its identity, and an `Entity` is also responsible to **validate itself** via the *validate* method (more on this on the `Exception Handling` section).


 ```java
public interface Entity<T, U> {
	
	/**
	 * Returns the id
	 * @return
	 */
	 U getId();
	
	/**T
	 * Validates the entity with runtime validation handler
	 * @param handler
	 * @return
	 */
	T validate(ValidationNotificationHandler handler);

 ```
 
 **Remark**: as we rely on an `Event-Driven Architecture`, the `Entity` has to **apply to itself events and then publish those events** to the rest of the Domain (more on this in the next section dedicated to the `Event Driven Architecture`. This is the reason why we are not implementing directly the [Entity](src/main/java/com/game/domain/model/entity/Entity.java) interface but we are extending the `IdentifiedPublisherDomainEntity` instead.

Once we know each `Entity` has to be assigned an Identity, we have to define more precisely the nature of its identifier.

We have decided to identify a Plateau by a `Universally Unique Identifier (UUID)` , a 128-bit unique value, which is provided since Java 1.5 by the class [UUID](https://docs.oracle.com/javase/8/docs/api/java/util/class-use/UUID.html). This implementation supports four different generator algorithms based on *the Leach-Salz variant* and is relatively fast to generate. Even if NASA has to drive rovers on new different zones every second, the `UUID` generator can keep this pace easily.

The application can easily generates a new random `UUID` as needed:

```java
	UUID uuid  = UUID.randomUUID();

 ```
 This generated `UUID` is assigned to a `Plateau` via its constructor

 ```java
public class Plateau extends IdentifiedDomainEntity<Plateau, UUID> implements TwoDimensionalSpace {

	private TwoDimensionalSpace dimensions;

	/**
	 * Matrix to keep track of the occupied locations
	 */
	boolean[][] locations;

	public Plateau(UUID uuid, TwoDimensionalSpace dimensions) {
		this.id = ArgumentCheck.preNotNull(uuid, GameExceptionLabels.MISSING_PLATEAU_UUID);
		this.dimensions = ArgumentCheck.preNotNull(dimensions, GameExceptionLabels.MISSING_PLATEAU_DIMENSIONS);
	}
```
Concerning the [Rover](src/main/java/com/game/domain/model/entity/rover/Rover.java) entity, each `Rover` belongs to a particular `Plateau`. Hence, the Rover has a `Many-To-One` relationship with the `Plateau` entity and should keep a reference to the `Plateau` it belongs to via the property `plateauUuid`.

The `Rover` is therefore identified by the unique combination (`name` + `plateauUuid`) and those two properties are encapsulated together in the [RoverIdentifier](src/main/java/com/game/domain/model/entity/rover/RoverIdentifier.java) class.

 ```java
/**
 * Rover identifier which includes Plateau UUID + Rover name
 * This combination identifies the Rover with absolute uniqueness
 * Remark: two Rovers can have the same name assuming they each belong to a distinct Plateau
 */
public class RoverIdentifier implements Serializable {
	
	/**
	 * Many-to-one association to a Plateau instance
	 * We keep track of the plateau UUID
	 */
	private UUID plateauUuid;
	
	private String name;
	
	public RoverIdentifier(UUID plateauUuid, String name) {
		this.plateauUuid = ArgumentCheck.preNotNull(plateauUuid, GameExceptionLabels.MISSING_PLATEAU_UUID);
		this.name = ArgumentCheck.preNotEmpty(name, GameExceptionLabels.MISSING_ROVER_NAME);
	}
```

We are done with our domain Entities.

##### Value Objects


<img src="src/main/resources/value_objects.png" />



`Value Objects` have **no identity**. They are purely for describing domain-relevant **attributes of entities**. As mentioned previously, if entities are fundamentally about identity, focusing on the "who", the `Value Objects` focus on the "what" and they are known only by their characteristics.

Something important to note, because they are defined by their attributes, `Value Objects` are treated as **immutable**; that is, once constructed, they can never alter their state.

The first `Value Object` which can be easily designed in our case is the object representing some two-dimensional coordinates. Those can be used to set up the `Plateau` dimensions as well as to determine the `Rover`'s position.

It is a perfect example of `Value Object` as it is always associated with one of those two `Entity` instances and it has real meaning only within the context of being attached either to the `Plateau` or the `Rover`.

These 2D-coordinates are defined by the immutable class [TwoDimensionalCoordinates](src/main/java/com/game/domain/model/entity/dimensions/TwoDimensionalCoordinates.java)

 ```java
 public class TwoDimensionalCoordinates {
s
	private int abscissa, ordinate;

	public TwoDimensionalCoordinates(int x, int y) {
		this.abscissa = x;
		this.ordinate = y;
	}
	
	public int getAbscissa() {
		return abscissa;
	}

	public int getOrdinate() {
		return ordinate;
	}
 
  ```

Every time the `Rover` has to update its position when asked to moves, it will receive **new** [TwoDimensionalCoordinates](src/main/java/com/game/domain/model/entity/dimensions/TwoDimensionalCoordinates.java) attributes (because of its immutability)

 ```java

/**
	 * We don't mutate the state
	 * @param step
	 * @return new coordinates
	 */
	public TwoDimensionalCoordinates shiftAlongAbscissa(int step) {
		return new TwoDimensionalCoordinates(abscissa + step, ordinate);
	}

	/**
	 * We don't mutate the state
	 * @param step
	 * @return new coordinates
	 */
	public TwoDimensionalCoordinates shiftAlongOrdinate(int step) {
		return new TwoDimensionalCoordinates(abscissa, ordinate + step);
	}
 ```
We need another `Value Object` close to the [TwoDimensionalCoordinates](src/main/java/com/game/domain/model/entity/dimensions/TwoDimensionalCoordinates.java). A [Plateau](src/main/java/com/game/domain/model/entity/plateau/Plateau.java) does not have coordinates but dimensions. 

Although distinct these two concepts are close from each other as:

- they share the same number of space dimensions (2D in our case)

- dimensions can be specified by coordinates

We thus create the `Value Object` [TwoDimensions](src/main/java/com/game/domain/model/entity/dimensions/TwoDimensions.java) which encapsulates the [TwoDimensionalCoordinates](src/main/java/com/game/domain/model/entity/dimensions/TwoDimensionalCoordinates.java) and exposes the method *getWidth()* and *getHeight()* instead of *getAbscissa()* and *getOrdinate()*


 ```java
/**
 * Plateau dimensions are linked to the coordinates by the number of dimensions (x,y as per now
 * but we could imagine a 3D game.
 * However, we want to hide some methods like {@link TwoDimensionalCoordinates#shiftXXX} which have
 * no sense for the plateau so {@link TwoDimensions} class encapsulates {@link TwoDimensionalCoordinates}
 *
 */
public class TwoDimensions implements TwoDimensionalSpace {
	
	private TwoDimensionalCoordinates coordinates;
	
	public TwoDimensions(TwoDimensionalCoordinates coordinates) {
		this.coordinates = coordinates;
	}
	
	public int getWidth() {
		return coordinates.getAbscissa();
	}
	
	public int getHeight() {
		return coordinates.getOrdinate();
	}

}
 ```
 
Just for fun and because we are into physics, we have also built a relativistic Dimensions object, called [RelativisticTwoDimensions](src/main/java/com/game/domain/model/entity/dimensions/RelativisticTwoDimensions.java), which calculate the `Plateau`'s dimensions according to the more accurate Einstein's Special Relativity. After all, nothing is said whether the commands are sent from Earth, or from a Nasa's space module orbiting with high speed around Mars. 

In this case, the `width` and `length` would be contracted by a factor called the `Lorentz Factor`, calculated by the method *calculateLorentzFactor*

 ```java

/**
 * Plateau dimensions according to more accurate Einstein's special relativity
 * theory. What would  the {@link TwoDimensionalCoordinates#getWidth()} and
 * {@link TwoDimensionalCoordinates#getHeight()} be as measured from a observer
 * traveling at a speed close to the speed of light ;-) 
 * In case of an observer's high speed, the lengths are contracted in the direction of the movement by
 * the Lorentz factor (usually referred as to the Greek letter gamma). 
 * Example of Gang of Four <b>Decorator pattern</b>, which  allows us to extend or alter the functionality of objects at run-time 
 * by wrapping them in an object of a decorator class.
 * In our case, at {@link Plateau} building time, we will chose a relativistic or classical algorithm.
 * {@see https://en.wikipedia.org/wiki/Special_relativity#Length_contraction}
 *
 */
public class RelativisticTwoDimensions implements TwoDimensionalSpace {

	private TwoDimensions dimensions;

	public static final int SPEED_OF_LIGHT = Math.multiplyExact(3, (int) Math.pow(10, 8));

	/**
	 * speed in m/s
	 */
	private double observerSpeed;

	private double lorentzFactor;

	public RelativisticTwoDimensions(int speed, TwoDimensions dimensions) {
		this(dimensions);
		this.observerSpeed = speed;
		this.lorentzFactor = calculateLorentzFactor(observerSpeed);
	}

	private RelativisticTwoDimensions(TwoDimensions dimensions) {
		this.dimensions = dimensions;
	}

	/**
	 * Calculate the Lorentz factor based on the observer's speed
	 * 
	 * @param observerSpeed2
	 * @return Lorentz factor
	 */
	private double calculateLorentzFactor(double observerSpeed) {
		MathContext precision = new MathContext(2); 
		return Math.sqrt(new BigDecimal(1)
				.subtract(
						new BigDecimal(Math.pow(observerSpeed, 2), precision).divide(new BigDecimal(Math.pow(SPEED_OF_LIGHT, 2)), precision))
				.doubleValue());
	}

	@Override
	public int getWidth() {
		return (int)(lorentzFactor * dimensions.getWidth());
	}

	@Override
	public int getHeight() {
		return (int)(lorentzFactor * dimensions.getHeight());
	}

}

 ```
 
 Aside from the coordinates and the dimensions, there is clearly another attribute which can be set to the `Rover`as a `Value Object`: its orientation. An orientation is not defined by any identity, but by a its value: either West, East, North or South.

This four predefined values make a perfect choice to design the [Orientation](src/main/java/com/game/domain/model/entity/rover/Orientation.java) as a java Enum.

Now an interesting question comes up: should we assign the responsibility to change the orientation to the `Rover` or to the `Orientation` itself? In general, **keeping entities focused on the responsibility of identity** is important because it prevents them from becoming bloated - an easy trap to fall into when they pull together many related behaviors.

Achieving this focus requires delegating related behavior to `Value Objects` and `Domain Services`.

In the case of [Orientation](src/main/java/com/game/domain/model/entity/rover/Orientation.java), we decide to push the behaviors *turnLeft* and *turnRight* into the `Value Object` itself for enhanced domain clarity.

 ```java

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

	...

	abstract Orientation turnLeft();

	abstract Orientation turnRight();
 ```
 
 So that calling the *turn* method on the `Rover` will just delegate the required behavior to the underlying `Orientation`
 
More precisely, as we will see further down in the section `Event-Driven Architecture` this will be possible by building a `Domain Event` with the new orientation  *orientation.turnXXX* and applying this `Event` to the `Rover`

 ```java

/**
 * We delegate the turn left command to the orientation object itself
 */
   public void turnLeft() {

		RoverTurnedEvent event = new   RoverTurnedEvent.Builder().withRoverId(id).withPreviousOrientation(orientation)
				.withCurrentOrientation(orientation.turnLeft()).build();

		applyAndPublishEvent(event, turnRover);

	}
 ```
 
 Similarly, in the context of a Rover's move, we can push the information of the movement direction in the `Value Object`  [Orientation](src/main/java/com/game/domain/model/entity/rover/Orientation.java) itself, via another property `axisDirection` and method `isHorizontal`
 
```java

public enum Orientation implements GameEnum<String> {

	NORTH("N", 1) {
       ...
	},

	EAST("E", 1) {
       ...
   },
   
   SOUTH("S", -1) {
       ...
	},

	WEST("W", -1) {
       ...
	};
	
	private static final Orientation[] HORIZONTAL_ORIENTATIONS = {WEST, EAST};

	Orientation(String value, int axisDirection) {
		this.value = value;
		this.axisDirection = axisDirection;
	}
	
	public boolean isHorizontal(Orientation orientation) {
		return isTypeInArray(orientation, HORIZONTAL_ORIENTATIONS);
	}
	...
 ```
It is then the responsibility of the [TwoDimensionalCoordinates](src/main/java/com/game/domain/model/entity/dimensions/TwoDimensionalCoordinates.java) to know how to be shifted given an initial `Orientation` and step length (by default set to 1 but can be chosen to be of any value).

```java

	/**
	 * Given an initial orientation, shift the coordinates when asked to move forward
	 * with a given step length 
	 * @param orientation
	 * @param stepLength
	 * @return
	 */
	public TwoDimensionalCoordinates shiftWithOrientation(Orientation orientation, int stepLength) {
		if (orientation.isHorizontal()) {
			return shiftAlongAbscissa(stepLength * orientation.getAxisDirection());
		} else {
			return shiftAlongOrdinate(stepLength * orientation.getAxisDirection());
		}
	}
 ```


We have thus **completely removed the coupling** from the `Rover` entity to the `Orientation` four values (we have seen too many times during interviews candidates using the four-conditions switch/case in the Rover class itself). The responsibility on how to move is delegated at the end to the Value Objects `Orientation` and `TwoDimensionalCoordinates` through the method *getCoordinates().shiftWithOrientation(this.orientation, step)*

```java
   
   // Rover move method
	private void moveWithEvent(int step) {

		// build event with previous and updated position
		RoverMovedEvent event = buildRoverMovedEvent(this.position)
		// delegation of the current position derivation to the Value Object Coordinates  
		.withCurrentPosition(getCoordinates().shiftWithOrientation(this.orientation, step)).build();

		// apply the event to the current in-memory instance
		// and publish the event for persistence purpose (DB instance + event store)
		applyAndPublishEvent(event, moveRover, moveRoverException);
	}

 ```

##### Repositories

A repository commonly refers to a storage location, usually considered a place of safety or preservation of the items stored in it.

This principle apply to `Domain Driven Design Repository`. Placing an `Entity` instance in its corresponding `Repository`, and later using that repository to retrieve the same instance, yields the expected whole object.

Generally speaking, there is a one-to-one relationship between an `Entity` and a `Repository`.

A fundamental feature of a pure domain model resides in that it should be **persistent ignorant**; i.e. it should be immune to changes required by the needs of any underlying persistence framework. We will dive deeper into this subject in the below section dedicated to `Hexagonal Architecture`.

### Hexagonal Architecture

> The Hexagonal Architecture, or Ports and Adapters Architecture, is an architectural pattern used in software design. 
  It aims at creating loosely coupled application components that can be easily connected to their software environment by means of ports and adapters.
  This makes components exchangeable at any level and facilitates test automation.

<img src="src/main/resources/Rover_hexagonal.png" />

The principles of the `Hexagonal Architecture` have been presented by its author Alistair CockBurn in his original
paper, [https://alistair.cockburn.us/hexagonal-architecture/](https://alistair.cockburn.us/hexagonal-architecture/).

The main goal of this architecture is to isolate, as much as possible, the `Domain Model` we have just built. Technically the model is isolated from the outside world by the so-called `Ports` and `Adapters`.

On the left side of the hexagon, you can find the `Primary Adapters` which are used by the external clients who want to interact with the application. These adapters should not depend directly on the model various implementations but rather on a `Port`, some kind of **facade interface** which hides the model details from the clients.

In our case, the `In Adapter` (another way to design a `Primary Adapter`) is represented by the file adapter
[GameFileAdapter](src/main/java/com/game/adapter/file/GameFileAdapter.java) (pink box) which interacts with the application through its port interface [GameService](src/main/java/com/game/domain/application/service/GameService.java) (green circle) by sending some [ApplicationCommand](src/main/java/com/game/domain/application/command/ApplicationCommand.java) instructions.


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

You can notice the very light dependency between the [File Adapter](src/main/java/com/game/adapter/file/GameFileAdapter.java) and the [Application Service](src/main/java/com/game/domain/application/service/GameService.java) (so at the end with the entire model) as it is limited only to the [ApplicationCommand](src/main/java/com/game/domain/application/command/ApplicationCommand.java) interface.

```java

/**
 * "Primary" port interface as described by Alistair CockBurn in his original
 * paper, i.e. port on the right side of the hexagon.
 * https://alistair.cockburn.us/hexagonal-architecture/ 
 * The application client will interact with this interface only
 *
 */
public interface GameService extends ApplicationService {
	
	/**
	 * Execute a list of commands
	 * @param commands
	 */
	void execute(List<ApplicationCommand> commands);
	
	/**
	 * Execute a single command
	 * @param command
	 */
	void execute(ApplicationCommand command);

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
 
The `Infrastructure Layer` is logically above all others, making references unidirectional and downward to the `Domain Layer`.


### Event-Driven Architecture

> Event-driven architecture (EDA) is a software architecture promoting the production, detection, consumption of, and reactions to events.

<img src="src/main/resources/event_sequence_diagram.png" />

In the context of `Domain-Driven Architecture`, we use a `Domain Event` to capture an occurrence of something that happened in the domain.

**How do we name an Event?**

The `Event` name states what occurred (past tense) in the `Entity` after the requested operation succeeded; usually as the `Event` is the result of executing a `Command` operation on the `Aggregate` or `Entity`, the name is usually derived from the command that was executed.

Command operation: *RoverMovedCommand*

Event outcome: *RoverMovedEvent*

Command operation: *RoverTurnCommand*

Event outcome: *RoverTurnedEvent*

**How do we model an `Event`?**

An `Event` is usually designed as immutable and includes the identity of the `Entity` instance on which it took place, along with all the parameters that caused this `Event`.

The minimal interface [DomainEvent](src/main/java/com/game/domain/model/event/DomainEvent.java), implemented by all `Events`, ensures support of an *occuredOn()* accessor. It enforces a basic contract for all Events

```java

public interface DomainEvent {
	
	LocalDateTime occuredOn();

}
```
Besides this, we have to determine whatever properties are necessary to represent a meaningful occurrence of what happened. This normally includes the identity of the `Entity` instance on which it took place.

For example, we could  design the [RoverMovedEvent](src/main/java/com/game/domain/model/event/rover/RoverMovedEvent.java) to notify each Rover's move as follows: it would include the `Rover`'s id, as well as its current and previous positions and has a constructor that permits only full state initialization, along with a complement of read accessors for each of its properties.

 
```java

public class RoverMovedEvent implements DomainEvent {

	private RoverIdentifierDto roverId;

	TwoDimensionalCoordinates previousPosition;

	TwoDimensionalCoordinates currentPosition;


	protected RoverMovedEvent(Builder builder) {
		this.roverId = builder.roverId;
		this.previousPosition = builder.previousPosition;
		this.currentPosition = builder.currentPosition;

	}
	
```
The below diagrams shows up all the Events published and handled in our application

<img src="src/main/resources/Event_Diagram.png" />

One of the simplest and most effective ways to publish `Domain Events` without coupling to components outside the domain model is to create a lightweight [Observer](https://en.wikipedia.org/wiki/Observer_pattern), or a `Publish/Subscribe`, which is acknowledged as another name for the same pattern. 

In this example, for the sake of simplicity, all registered subscribers execute in the same process space with the publisher and run on the same thread. When an `Event` is published, each registered subscriber will be notified synchronously, one by one (this also implies that all subscribers are running within the same transaction, probably controlled by the `Application Service` that is the direct client of the domain model).

**Publish/Subscribe**

Below is the `Publish/Subscribe` [DomainEventPublisherSubscriber](src/main/java/com/game/domain/model/event/DomainEventPublisherSubscriber.java) used in our Rover application. Since every incoming request from users of the system is handled on a separated dedicated thread (please remind yourself that we have extended the basic requirements to allow many distinct clients to send commands separately), we divide subscribers per thread. So the two `ThreadLocal` variables, `subscribers` and `publishing`, are allocated per thread. 


```java

/**
 * Observer to publish/subscribe {@link DomainEvent}
 *
 */
public class DomainEventPublisherSubscriber {

	@SuppressWarnings("rawtypes")
	private static final ThreadLocal<List> subscribers = new ThreadLocal<>();

	private static final ThreadLocal<Boolean> publishing = new ThreadLocal<>();

	public static DomainEventPublisherSubscriber instance() {
		return new DomainEventPublisherSubscriber();
	}

	@SuppressWarnings("unchecked")
	public <T extends DomainEvent> void publish(final T domainEvent) {
		if (null !=publishing.get() && publishing.get()) return;

		try {
			publishing.set(Boolean.TRUE);
			List<DomainEventSubscriber<T,String>> registeredSubscribers = subscribers.get();
			if (registeredSubscribers != null) {
				Class<?> eventType = domainEvent.getClass();
				registeredSubscribers.forEach(subscriber -> {
					handleEvent(subscriber, eventType, domainEvent);
				});
			}

		} finally {
			publishing.set(Boolean.FALSE);
		}

	}

	
	@SuppressWarnings("unchecked")
	public <T extends DomainEvent> void subscribe(DomainEventSubscriber<T, String> subscriber) {
		if (null !=publishing.get() && publishing.get()) return;
		
		List<DomainEventSubscriber<T,String>> registeredSubscribers = subscribers.get();
		if (registeredSubscribers == null) {
			registeredSubscribers = new ArrayList<DomainEventSubscriber<T,String>>();
			subscribers.set(registeredSubscribers);
		}
		if (!registeredSubscribers.contains(subscriber))
		registeredSubscribers.add(subscriber);
	}

	private <T extends DomainEvent> void handleEvent(DomainEventSubscriber<T,String> subscriber, Class<?> eventType, T domainEvent) {
		Class<?> subscribedTo = subscriber.subscribedToEventType();
		if (subscribedTo == eventType) {
			subscriber.handleEvent(domainEvent);
		}

	}	
```

**Publishing**

<img src="src/main/resources/publisherDiagram.png" />

The most common use of `Domain Events` is when an `Entity` creates an `Event` and publishes it. 

The publisher resides in a module of the `Domain Model` (in our case in the package *com.game.domain.model.event)*, but it does not model some aspect of the domain. 

Rather, it provides a simple service to `Entities` that need to notify subscribers of `Events`.

The base class that each entity needs to extend to get all the plumber to publish events is [EntityBaseDomainEventPublisher](src/main/java/com/game/domain/model/entity/EntityBaseDomainEventPublisher.java).

In two words, it provides the method *applyAndPublishEvent* which takes as arguments the Event, a java `Function<DomainEvent, DomainEvent>` that will be applied to the current `Entity` instance itself and finally another argument of type `BiFunction<Exception, DomainEvent, DomainEvent>` to be called in case of anything should going wrong during the former function processing.


```java

public class EntityBaseDomainEventPublisher extends BaseDomainEventPublisher {

	public void applyAndPublishEvent(DomainEvent event, Function<DomainEvent, DomainEvent> function,
			BiFunction<Exception, DomainEvent, DomainEvent> exceptionFunction) {
		try {
			function.andThen(publishAndStore).apply(event);
		} catch (Exception exception) {
			DomainEvent exceptionEvent = null;
			try {
				exceptionEvent = exceptionFunction.apply(exception, event);
				publishEventFunction.apply(exceptionEvent);
				// needed as whatever the exceptionFunction is supposed to do
				// (throwing an exception or not) we want to store the event
			} finally {
				eventStoreFunction.apply(exceptionEvent);
			}
		}
	}
```
For example, when the Rover receives the order to move, the method *moveWithEvent* is called and:

- builds the `Event` with the required level information to process the move

- applies the `moveRover` function (consisting on updating the current instance + doing the validation on the updated state)

- in case of any error during the validation, publish a new `ExceptionEvent` to be handled by a specific subscriber, in our case [RoverMovedWithExceptionEventSubscriber](src/main/java/com/game/domain/model/event/subscriber/rover/RoverMovedWithExceptionEventSubscriber.java).

```java
	private void moveWithEvent(int step) {
		
		...

		// build event with previous and updated position
		RoverMovedEvent event = buildRoverMovedEvent(previousPosition)
				.withCurrentPosition(currentPosition).build();

		// apply the event to the current in-memory instance
		// and publish the event for persistence purpose (DB instance + event store)
		applyAndPublishEvent(event, moveRover, moveRoverWithException);
		
		...
	}
	
	public final Function<DomainEvent, DomainEvent> moveRover = event -> {
		this.position = ((RoverMovedEvent) event).getCurrentPosition();
		validate(new RoverMovedPositionValidationNotificationHandler());
		return event;
	};
	
	public final BiFunction<Exception, DomainEvent, DomainEvent> moveRoverWithException = (exception, event) -> {
		return new RoverMovedWithExceptionEvent((RoverMovedEvent) event, exception);
	};
```

This is exactly what the diagram sequence at the beginning of this section reflects. However, you may find it difficult to read and as a java developer, the below stacktrace may help you as well to figure out this part of the process:

```java

		RoverMovedEventSubscriber.handleEvent(RoverMovedEvent) line: 13	
			RoverMovedEventSubscriber.handleEvent(Object) line: 1	
			DomainEventPublisherSubscriber.handleEvent(DomainEventSubscriber<T>, Class<?>, T) line: 57	
			DomainEventPublisherSubscriber.lambda$0(Class, DomainEvent, DomainEventSubscriber) line: 31	
			1045941616.accept(Object) line: not available	
			ArrayList<E>.forEach(Consumer<? super E>) line: 1255	
			DomainEventPublisherSubscriber.publish(T) line: 30	
			BaseDomainEventPublisher.lambda$0(DomainEvent) line: 10	
			11003494.apply(Object) line: not available	
			11003494(Function<T,R>).lambda$andThen$1(Function, Object) line: 88	
			817406040.apply(Object) line: not available	
			917819120(Function<T,R>).lambda$andThen$1(Function, Object) line: 88	
			817406040.apply(Object) line: not available	
			Rover(EntityBaseDomainEventPublisher).applyAndPublishEvent(DomainEvent, Function<DomainEvent,DomainEvent>, BiFunction<Exception,DomainEvent,DomainEvent>) line: 14	
			Rover.moveWithEvent(int) line: 111	
			Rover.lambda$5(int) line: 94	
			352359770.accept(int) line: not available	
			Streams$RangeIntSpliterator.forEachRemaining(IntConsumer) line: 110	
			IntPipeline$Head<E_IN>.forEach(IntConsumer) line: 557	
			Rover.moveNumberOfTimes(int) line: 93	
			RoverServiceImpl.moveRoverNumberOfTimes(RoverIdentifier, int) line: 85	
			GameServiceImpl.execute(RoverMoveCommand) line: 93	
			GameServiceCommandVisitor.visit(RoverMoveCommand) line: 25	
			RoverMoveCommand.acceptVisitor(GameServiceCommandVisitor) line: 33	
			GameServiceImpl.lambda$0(GameServiceCommandVisitor, ApplicationCommand) line: 43	
			2042495840.accept(Object) line: not available	
			ArrayList<E>.forEach(Consumer<? super E>) line: 1255	
			GameServiceImpl.execute(List<ApplicationCommand>) line: 43	
			
```

Furthermore, in case of any exception is thrown when applying the first function to the `Entity` (most often during the validation step), the `Event` is then mapped into an `Exception Event` (with the root `Exception` and the `Event` as instance properties) of base type [BaseDomainEventWithException](src/main/java/com/game/domain/model/event/exception/BaseDomainEventWithException.java), which is in turn published to be handled by a dedicated `Exception Subscriber`.

This is shown in the below extract where the Exception Subscriber is represented by a `MockRoverMovedEventWithExceptionSubscriber` as we are running under unit testing (more on this on the next section).

```java

	BaseUnitTest$MockRoverMovedEventWithExceptionSubscriber.handleEvent(RoverMovedWithExceptionEvent) line: 188	
			BaseUnitTest$MockRoverMovedEventWithExceptionSubscriber.handleEvent(Object) line: 1	
			DomainEventPublisherSubscriber.handleEvent(DomainEventSubscriber<T>, Class<?>, T) line: 57	
			DomainEventPublisherSubscriber.lambda$0(Class, DomainEvent, DomainEventSubscriber) line: 31	
			1661210650.accept(Object) line: not available	
			ArrayList<E>.forEach(Consumer<? super E>) line: 1255	
			DomainEventPublisherSubscriber.publish(T) line: 30	
			BaseDomainEventPublisher.lambda$0(DomainEvent) line: 10	
			1277933280.apply(Object) line: not available	
			Rover(EntityBaseDomainEventPublisher).applyAndPublishEvent(DomainEvent, Function<DomainEvent,DomainEvent>, BiFunction<Exception,DomainEvent,DomainEvent>) line: 19	
			Rover.moveWithEvent(int) line: 111	
			Rover.lambda$5(int) line: 94	
			28094269.accept(int) line: not available	
			Streams$RangeIntSpliterator.forEachRemaining(IntConsumer) line: 110	
			IntPipeline$Head<E_IN>.forEach(IntConsumer) line: 557	
			Rover.moveNumberOfTimes(int) line: 93	
```
			
And this is an example of stored event `RoverMovedWithExceptionEvent` in case of a Rover collides with another Rover for a given location

```java
RoverMovedWithExceptionEvent published at [2020-04-24T19:17:12.003] with Rover Moved Event [RoverMovedEvent published at [2020-04-24T19:17:12] with rover id [Name [ROVER_2] - Plateau UUID [fa221732-982e-4e01-bf29-1aafb56adb9c]], previous position [Coordinates [abscissa = 1, ordinate = 2]], current position [Coordinates [abscissa = 0, ordinate = 2]]], exception [com.game.domain.model.exception.IllegalRoverMoveException: [ERR-004] There is already a Rover at position X = [0] and Y = [2]]
```

**Subscribing**


Publishing an `Event` will go through the list of all `Subscribers` registered, and will match a `Subscriber` with the corresponding exact `Event` type if it exists in the `subscribers` list.

Then the selected `Subscriber` will be asked to handle the `Event` via the method *handleEvent*.

To enforce this process, each subscriber should implement the interface [DomainEventSubscriber](src/main/java/com/game/domain/model/event/DomainEventSubscriber.java).

Assigning an Id is important as if the client send several commands in the same thread, we do not want the same subscriber to be registered several times (even if theoretically the command should be idempotent, the version number checking for optimistic locking would break)

```java

public interface DomainEventSubscriber<T,U> {

	public void handleEvent(T event);

	public Class<T> subscribedToEventType();
	
	public U getId();

}
```

The unique ID assignment is done via the abstract class [AbstractDomainEventSubscriber](src/main/java/com/game/domain/model/event/AbstractDomainEventSubscriber.java) which assigns by default the subscriber's class simple name.

```java
public abstract class AbstractDomainEventSubscriber<T> implements DomainEventSubscriber<T, String> {
	
	protected String id = this.getClass().getSimpleName();

	@Override
	public String getId(){
		return id;
	}
}
```

Depending on the context, threads may be pooled and reused request by request. We don't want subscribers registered on the thread for a previous request to remain registered, so when a new request is received by the application, it should use the *clear()* operation to clear any previous subscriber (this could be done by via a servlet filter in a web application).

Since `Application Services` are the direct client of the domain model when using `Hexagonal Architecture`, they are in an ideal position  to register a subscriber with the publisher before they execute the domain services execution.

Below is the code extract from our Application Service's implementation [GameServiceImpl](src/main/java/com/game/domain/application/service/GameServiceImpl.java) which registers three specific subscribers required for a proper`RoverMoveCommand` execution

```java

void execute(RoverMoveCommand command) {

		// register the subscriber for the given type of event = RoverMovedEvent
		DomainEventPublisher.instance().subscribe(new RoverMovedEventSubscriber());

		// register the subscriber in case of something went wrong during Rover moves
		DomainEventPublisher.instance().subscribe(new RoverMovedWithExceptionEventSubscriber());
		
		// register the subscriber for the plateau to release and occupy the locations
		DomainEventPublisher.instance().subscribe(new PlateauSwitchedLocationEventSubscriber());

		// delegates to the rover service
		GameContext.getInstance().getRoverService().moveRoverNumberOfTimes(command.getRoverId(), command.getNumberOfMoves());

	}
```

The subscriber [RoverMovedEventSubscriber](src/main/java/com/game/domain/model/event/subscriber/rover/RoverMovedEventSubscriber.java) has the responsibility to handle the [RoverMovedEvent](src/main/java/com/game/domain/model/event/rover/RoverMovedEvent.java) in case of everything went fine, which means:

- persisting the `Rover` with its last location


```java
public class RoverMovedEventSubscriber implements DomainEventSubscriber<RoverMovedEvent> {

	@Override
	public void handleEvent(RoverMovedEvent event) {
	    
		// update persistent Rover with last position
		GameContext.getInstance().getRoverService().updateRoverWithPosition(event.getRoverId(), event.getCurrentPosition());
		
	}
}
```

On his side, the [RoverMovedWithExceptionEventSubscriber](src/main/java/com/game/domain/model/event/subscriber/rover/RoverMovedWithExceptionEventSubscriber.java) handle the Event of type [RoverMovedWithExceptionEvent](src/main/java/com/game/domain/model/event/rover/RoverMovedWithExceptionEvent.java), which is published when an exception occurs during the `Rover`'s move.

In this case, the `RoverMovedWithExceptionEventSubscriber` should:
- remove the persistent `Rover` from the `Plateau`
- set the last `Rover`'s position as free on the `Plateau`
- and finally throw a [IllegalRoverMoveException](src/main/java/com/game/domain/model/exception/IllegalRoverMoveException.java) with specific error code `ERR-004` to the client.

```java
public class RoverMovedWithExceptionEventSubscriber implements DomainEventSubscriber<RoverMovedWithExceptionEvent> {

	@Override
	public void handleEvent(RoverMovedWithExceptionEvent event) {

		// 1. remove the persistent rover from the game
		GameContext.getInstance().getRoverService().removeRover(event.getRoverId());

		// 2. set the last rover position as free on the Plateau
		GameContext.getInstance().getPlateauService().updatePlateauWithFreeLocation(
				event.getPlateauUuid(), event.getRoverPreviousPosition());
		
		// 3. finally throw an exception	
		throw new GameException(event.toString(), event.getException());

	}

```

Finally the last subscriber [PlateauSwitchedLocationEventSubscriber](src/main/java/com/game/domain/model/event/subscriber/plateau/PlateauSwitchedLocationEventSubscriber.java) is in charge of updating the Plateau's location after the Rover's move: freeing up the previous Rover's location and marking the current one as busy.

```java

public class PlateauSwitchedLocationEventSubscriber implements DomainEventSubscriber<PlateauSwitchedLocationEvent> {

	@Override
	public void handleEvent(PlateauSwitchedLocationEvent event) {
	    
	 // update persistent plateau locations
	 		updatePlateauWithLastLocations(event);
		
	}
	
	private void updatePlateauWithLastLocations(PlateauSwitchedLocationEvent event) {
		GameContext.getInstance().getPlateauService().updatePlateauWithLocations(event.getPlateauId(),
				event.getPreviousPosition(), event.getCurrentPosition());
	}
```

We get two distinct subscribers for the `Rover` and the `Plateau` as one thing the subscriber **should not**  do is get another `Entity` instance and execute modifying behavior on it. This would violate the **modify-single-entity-instance-in-single-transaction** rule of thumb. The consistency of all `Entity` instances other than the one used in the single transaction must be enforced by asynchronous means.


We have therefore established a clear segregation of responsibilities:
- the `Application Service` publish `Domain Events` and delegates the action to execute to the `Domain Services` but has no business responsibility
- Each `Event` is clearly assigned a single responsibility in a very clear and delimited context of a particular `Entity`.



### Test driven

From a testing perspective, our goal before to start to code the application was very clear:

- We did not want to use any framework, i.e. no framework for dependency injection and not even a framework for mock testing. The only pre-built framework we have on-boarded is purely 100% dedicated to unit testing assertions: [Test NG](https://testng.org/doc/)


- We expected however a high unit testing coverage rate, at least at 90% of the application should have been tested.

- The components which need to be tested in particular: Entities, Domain Services, Application Service.

**Entities**

In our Event-Driven architecture, an `Entity` is endowed with a very specific feature: it publishes `Domain Events`.

Say we would like for example to test the Rover's *move* method and to write ideally this kind of test:


```java
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
```

That is, we want to test that an `Event` of type [RoverMovedEvent](src/main/java/com/game/domain/model/event/rover/RoverMovedEvent.java) has been sent and handled by whatever `Event Handler` which matches the type `RoverMovedEvent`.

Nothing is simpler: we just create some Mock Event Subscribers which for example just add the `Event` to a `List`. All possible mock subscribers are made available in the Test utility class [BaseUnitTest](src/test/java/com/game/test/util/BaseUnitTest.java).

In this example, the `MockRoverMovedEventSubscriber` stubs a real `Event Subscriber` by just adding the handled event to a `roverMovedEvents` list.

```java

	public class MockRoverMovedEventSubscriber implements DomainEventSubscriber<RoverMovedEvent> {

		@Override
		public void handleEvent(RoverMovedEvent event) {
			roverMovedEvents.add(event);
		}

		@Override
		public Class<RoverMovedEvent> subscribedToEventType() {
			return RoverMovedEvent.class;
		}
	}
	
```

**Domain Services**

The `Services` use two types of components in our application:

- the `Repositories` and in case of RoverService another application service `PlateauService`

- the `Event Domain Publisher` (when initializing an `Entity`)

The first two points are easily addressed as `Repositories` and `Domain Services` are injected by `Dependency Injection` via the constructor.


```java

	private PlateauService plateauService;

	private RoverRepository roverRepository;

	public RoverServiceImpl(PlateauService plateauService, RoverRepository roverRepository) {
		this.plateauService = plateauService;
		this.roverRepository = roverRepository;
	}
	
```
	
so that injecting mock components are just a matter of instantiating them and  passing them in the constructor, see by example how the `RoverService` is instantiated in the [RoverServiceImplTest](src/test/java/com/game/domain/model/service/rover/RoverServiceImplTest.java)

```java

public class RoverServiceImplTest extends BaseUnitTest

{

RoverRepository mockRoverRepository = new MockRoverRepository();

	RoverRepository mockRoverRepository = new MockRoverRepository();

	/**
	 * Inject our own Mock Plateau Service + Rover repository implementation here
	 */
	private RoverServiceImpl roverService = new RoverServiceImpl(new MockPlateauServiceImpl(), mockRoverRepository);

```
Concerning the `Event` publishing, we use the same mock publishers as described in the previous section for the `Entities`.

**Application Services**

Our application service [GameServiceImpl](src/main/java/com/game/domain/application/service/ApplicationService.java) needs to be tested as well. That is where our [Service Locator](src/main/java/com/game/domain/model/service/locator/ServiceLocator.java) is of great benefit.

It exposes the method *load* which allows to load the desired type of Service Locator, real or mock.

```java
public class ServiceLocator {


	private static ServiceLocator soleInstance = new ServiceLocator();
	

	public static void load(ServiceLocator arg) {
		soleInstance = arg;
	}
	
}

```

We therefore use this useful feature to load the mock services in our test [GameServiceImpl](src/main/java/com/game/domain/application/service/ApplicationService.java)

```java

public class GameServiceImplTest {
...

   private void mockServiceLocator() {
		ServiceLocator mockServiceLocator = new ServiceLocator();
		mockServiceLocator.loadDomainService(ServiceLocator.ROVER_SERVICE, new MockRoverServiceImpl());
		mockServiceLocator.loadDomainService(ServiceLocator.PLATEAU_SERVICE, new MockPlateauServiceImpl());
		mockServiceLocator.loadEventStore(ServiceLocator.EVENT_STORE, new EventStoreImpl());
		ServiceLocator.load(mockServiceLocator);
	}
...	
}

```
Consider for example the following method *execute* to move a `Rover`:

```java
void execute(RoverMoveCommand command) {

		// register the subscriber for the given type of event = RoverMovedEvent
		DomainEventPublisherSubscriber.instance().subscribe(new RoverMovedEventSubscriber());

		// register the subscriber in case of something went wrong during Rover moves
		DomainEventPublisherSubscriber.instance().subscribe(new RoverMovedWithExceptionEventSubscriber());

		// register the subscriber for the plateau
		DomainEventPublisherSubscriber.instance().subscribe(new PlateauSwitchedLocationEventSubscriber());

		// delegates to the rover service
		GameContext.getInstance().getRoverService().moveRoverNumberOfTimes(command.getRoverId(),
				command.getNumberOfMoves());

	}

```

We have few things to check here:

- that three subscribers have been registered

- that the `RoverService` has been called via the method *move*

```java
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
```

The first check is quick and easy, as we just need to check the `DomainEventPublisherSubscriber.getSubscribers` instances.

The second check is done via the mock service injected during the test setup, which adds the `Rover` to a List.

```java

public class MockRoverServiceImpl implements RoverService {

		@Override
		public void moveRoverNumberOfTimes(RoverIdentifier id, int numberOfTimes) {
			BaseUnitTest.this.roversList.add(new Rover(new RoverIdentifier(id.getPlateauId(), id.getName()),
					new TwoDimensionalCoordinates(2, 3), Orientation.WEST));
			if (id.getName().equals(GameContext.ROVER_NAME_PREFIX + 5))
				throw new PlateauLocationAlreadySetException("Error");
		}
```

### Validation and Exception Handling

The base class of our Exception hierarchy is the [GameException](src/main/java/com/game/domain/model/exception/GameException.java) which:
- is a of type **RuntimeException** as we don't expect any retry or action from the end user


- takes an **Error code** as constructor argument along the error message for better understanding/readability from the end user

```java
public class GameException extends RuntimeException {
	
	private final String errorCode;
	
	public GameException(String message, String errorCode) {
		this(message, errorCode, null);
	}
	
	@Override
	public String getMessage() {
		String msg = getOriginalMessage();
		if (getErrorCode() != null) {
			msg = String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN, getErrorCode(), msg);
		}
		return msg;
	}

	private String getOriginalMessage() {
		return super.getMessage();
	}
	
```
All the error codes and error messages labels are grouped together in a single class [GameExceptionLabels](src/main/java/com/game/domain/model/exception/GameExceptionLabels.java) for better lisibility and overview.

We consider two types of validation:

- A **Technical validation** which checks the nullity or emptiness of arguments. 

   This is handled by the [ArgumentCheck](src/main/java/com/game/core/validation/ArgumentCheck.java) class which throws a [IllegalArgumentGameException](src/main/java/com/game/domain/model/exception/IllegalArgumentGameException.java) with a specific error code <code>[ERR-000]</code> when a required argument is not present or empty.
   
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

The base class [EntityValidator](src/main/java/com/game/domain/model/validation/EntityValidator.java) takes this responsibility. Actually, in addition to identity, a primary implementation requirement for entities is ensuring they are **self-validating** and **always valid**.

This is similar to the self-validating nature of value objects, although it is usually much more **context dependent** due to entities having a life-cycle.

To make this context dependency effective, we make the entity validator class depends on [ValidationNotificationHandler](src/main/java/com/game/domain/model/validation/ValidationNotificationHandler.java) interface by constructor injection.

This delegation to a generic error notification handler - [Strategy pattern](https://en.wikipedia.org/wiki/Strategy_pattern) - is exactly what we need to ensure distinct validation processes under different contexts. 

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
Let's consider for example the validator class [RoverValidator](src/main/java/com/game/domain/model/entity/rover/RoverValidator.java) dedicated to check that the `Entity` is in a valid state after its creation or any action.
 
We have a few things to check: the `Rover`'s position X and Y should be both positive, the position X and Y should be on the `Plateau` to which the `Rover` belongs and finally no other `Rover` should be already on this position.
 
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
   These validations should be made both at the time of initialization time and later whenever the `Rover` is asked to move.
   
   In the first case, it would be a good idea to send ALL the error messages to the end user, so that he can re-send the initialization command successfully next time.
   
   This exactly what the class [EntityDefaultValidationNotificationHandler](src/main/java/com/game/domain/model/validation/EntityDefaultValidationNotificationHandler.java) does:
      
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
  
 For example, if you try to initialize a Rover with coordinates x=-3 and y=8 attached to a `Plateau` with the dimensions width = 6 and height = 8, that would be the stacktrace of the exception.
 
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
   
But let's suppose now that the `Rover` has been successfully initialized and has to take some moves. Evidently, at each move its position has to be validated by the very same checks but in this new context, we do NOT want to throw an initialization error with the error code `[ERR-001]`.

We would like to throw a different exception, which could be eventually caught by the service layer to take some actions: by example in our case to remove the `Rover` from the `Plateau` and to mark its last position on the `Plateau` as free.

This is very easy thanks to our [ValidationNotificationHandler](src/main/java/com/game/domain/model/validation/ValidationNotificationHandler.java) interface: we just need to inject another implementation [RoverMovedPositionValidationNotificationHandler](src/main/java/com/game/domain/model/validation/RoverMovedPositionValidationNotificationHandler.java), which in case of a wrong move will throw this time a [IllegalRoverMoveException](src/main/java/com/game/domain/model/exception/IllegalRoverMoveException.java) exception, with error code <code>[ERR-004]</code>.

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
But wait there is event more. As we have seen before, each exception thrown during the validation step is caught, transformed into a [BaseDomainEventWithException](src/main/java/com/game/domain/model/event/exception/BaseDomainEventWithException.java) and finally caught by a corresponding Exception Subscriber.

This is the occasion to contextualize again the Exception thrown by an another error code if required.

Let's take the example of a two `Rovers` colliding by occupying the same location. We send exactly the same commands to two distinct `Rovers`, both ending at position X = [0] and Y = [2].
You can find this code in the test class [GameIntegrationTest](src/test/java/com/game/integration/GameIntegrationTest.java)

  ```java
	// rover1 commands
		// Rover 1 initialization (1,2) and Orientation 'N' 
		String rover1Name = GameContext.ROVER_NAME_PREFIX + 1;
		commands.add(new RoverInitializeCommand.Builder().withPlateauUuid(plateauId).withName(rover1Name)
				.withAbscissa(1).withOrdinate(2).withOrientation('N').build());
		RoverIdentifier rover1 = new RoverIdentifier(plateauId, rover1Name);
		// Rover 1 move commands  LMLMLMLMMs
		commands.add(new RoverTurnCommand(rover1, RoverTurnInstruction.LEFT));
		commands.add(new RoverMoveCommand(rover1, 1));
		
		String rover2Name = GameContext.ROVER_NAME_PREFIX + 2;
		commands.add(new RoverInitializeCommand.Builder().withPlateauUuid(plateauId).withName(rover2Name)
				.withAbscissa(1).withOrdinate(2).withOrientation('N').build());
		RoverIdentifier rover2 = new RoverIdentifier(plateauId, rover2Name);
		// Rover 1 move commands  LMLMLMLMMs
		commands.add(new RoverTurnCommand(rover2, RoverTurnInstruction.LEFT));
		commands.add(new RoverMoveCommand(rover2, 1));
 ```
This is the exception thrown `GameException` with error code `ERR-004` along with the game state: the second Rover has been removed from the plateau, and the only the Rover 1 remains.

We get the error code because in the `RoverMovedPositionValidationNotificationHandler` we throw the `IllegalRoverMoveException` backed by a generic `GameException` in the Event Subscriber, which keeps the `Event` details. So you have all the details needed to understand what happened
- which command was executed
- which exception was the root cause of the failure

 ```java
com.game.domain.model.exception.GameException: RoverMovedWithExceptionEvent published at [2020-04-25T08:50:53.971] with Rover Moved Event [RoverMovedEvent published at [2020-04-25T08:50:53.967] with rover id [Name [ROVER_2] - Plateau UUID [f4a749e4-3fea-4856-a39a-870536bf2b98]], previous position [Coordinates [abscissa = 1, ordinate = 2]], current position [Coordinates [abscissa = 0, ordinate = 2]]], 
exception [com.game.domain.model.exception.IllegalRoverMoveException: [ERR-004] There is already a Rover at position X = [0] and Y = [2]]
	at com.game.domain.model.event.subscriber.rover.RoverMovedWithExceptionEventSubscriber.handleEvent(RoverMovedWithExceptionEventSubscriber.java:20)
	at com.game.domain.model.event.subscriber.rover.RoverMovedWithExceptionEventSubscriber.handleEvent(RoverMovedWithExceptionEventSubscriber.java:8)
	at com.game.domain.model.event.DomainEventPublisherSubscriber.handleEvent(DomainEventPublisherSubscriber.java:57)
	at com.game.domain.model.event.DomainEventPublisherSubscriber.lambda$publish$0(DomainEventPublisherSubscriber.java:31)
	at java.util.ArrayList.forEach(ArrayList.java:1255)
	at com.game.domain.model.event.DomainEventPublisherSubscriber.publish(DomainEventPublisherSubscriber.java:30)
	at com.game.domain.model.event.BaseDomainEventPublisher.lambda$new$0(BaseDomainEventPublisher.java:10)
	at com.game.domain.model.entity.EntityBaseDomainEventPublisher.applyAndPublishEvent(EntityBaseDomainEventPublisher.java:19)
	at com.game.domain.model.entity.rover.Rover.moveWithEvent(Rover.java:111)
	at com.game.domain.model.entity.rover.Rover.lambda$moveNumberOfTimes$5(Rover.java:94)
	at java.util.stream.Streams$RangeIntSpliterator.forEachRemaining(Streams.java:110)
	at java.util.stream.IntPipeline$Head.forEach(IntPipeline.java:557)
	at com.game.domain.model.entity.rover.Rover.moveNumberOfTimes(Rover.java:93)
	at com.game.domain.model.service.rover.RoverServiceImpl.moveRoverNumberOfTimes(RoverServiceImpl.java:85)
	at com.game.domain.application.service.GameServiceImpl.execute(GameServiceImpl.java:93)
	at com.game.domain.application.service.GameServiceCommandVisitor.visit(GameServiceCommandVisitor.java:25)
	at com.game.domain.application.command.rover.RoverMoveCommand.acceptVisitor(RoverMoveCommand.java:33)
	at com.game.domain.application.service.GameServiceImpl.lambda$execute$0(GameServiceImpl.java:43)
	at java.util.ArrayList.forEach(ArrayList.java:1255)
	at com.game.domain.application.service.GameServiceImpl.execute(GameServiceImpl.java:43)
	at com.game.integration.GameIntegrationTest.simulateRoversCollision(GameIntegrationTest.java:68)
	at com.game.integration.GameIntegrationTest.main(GameIntegrationTest.java:26)
Caused by: com.game.domain.model.exception.IllegalRoverMoveException: [ERR-004] There is already a Rover at position X = [0] and Y = [2]
	at com.game.domain.model.validation.RoverMovedPositionValidationNotificationHandler.checkValidationResult(RoverMovedPositionValidationNotificationHandler.java:16)
	at com.game.domain.model.validation.EntityValidator.validate(EntityValidator.java:31)
	at com.game.domain.model.entity.rover.Rover.validate(Rover.java:80)
	at com.game.domain.model.entity.rover.Rover.lambda$new$0(Rover.java:38)
	at java.util.function.Function.lambda$andThen$1(Function.java:88)
	at com.game.domain.model.entit
```
### Concurrency and Optimistic locking

With the given API command exposed to the outside world, it is perfectly possible to two end-users send concurrent commands to the same Rover.

Let's consider by example the following example:

- a given `Rover` rover1 is already initialized on a given `Plateau` with coordinates(1,2) and orientation North
- a end-user User1 sends the following commands to the rover1: *turnLeft* and then *move*, so expecting a new rover1's position as coordinates(0,2) and orientation West. 
- exactly at the same time, a end-user User2 asks to same rover1 to *move twice* in a row, so expecting a new rover1's position as coordinate(1,4) and orientation North.

Now, suppose that the technology/adapter chosen by user1 encounters some problems or is by nature slower than the technology used by user2, we face the famous **Lost Update** issue.

You can find the code of this scenario in the test class [GameIntegrationTest](src/test/java/com/game/integration/GameIntegrationTest.java)
 
 ```java
UUID plateauId = UUID.randomUUID();
		// ********* Given **********
		// initialization plateau command (5,5)
		List<ApplicationCommand> commands = new ArrayList<>();
		commands.add(new PlateauInitializeCommand.Builder().withObserverSpeed(0).withId(plateauId).withAbscissa(5)
				.withOrdinate(5).build());

		// rover1 commands
		// Rover 1 initialization (1,2) and Orientation 'N' 
		String rover1Name = GameContext.ROVER_NAME_PREFIX + 1;
		commands.add(new RoverInitializeCommand.Builder().withPlateauUuid(plateauId).withName(rover1Name)
				.withAbscissa(1).withOrdinate(2).withOrientation('N').build());
		RoverIdentifier roverId1 = new RoverIdentifier(plateauId, rover1Name);
		
		gameService.execute(commands);
		
		
		Runnable runnableTask = () -> {
			Rover rover = roverService.getRover(roverId1);
		    try {
		    	GameContext.getInstance().addPlateau(plateauService.loadPlateau(roverId1.getPlateauId()));
		        TimeUnit.MILLISECONDS.sleep(1000);
		       rover.turnLeft();
		       rover.move();
		    } catch (InterruptedException e) {
		        e.printStackTrace();
		    }
		    GameIntegrationTest.printInfos();
		};
		executorService.execute(runnableTask);
		
		gameService.execute(new RoverMoveCommand(roverId1, 2));
 ```
We simulate the long-time transaction of user1 by a Thread sleep of one second.

As expected, the rover's final position is in incoherent position of coordinates(0,4) and orientation West. None of the end-users was expecting such a result.

 ```java
RoverInitializedEvent published at [2020-04-25T10:44:31.782] with rover id [Name [ROVER_1] - Plateau UUID [a85b82a9-cdfd-4388-aa47-376b06920e42]], position [Coordinates [abscissa = 1, ordinate = 2]], orientation [Orientation [NORTH]]
PlateauSwitchedLocationEvent published at [2020-04-25T10:44:31.789] with plateau id [a85b82a9-cdfd-4388-aa47-376b06920e42], position released [null], position occupied [Coordinates [abscissa = 1, ordinate = 2]]
RoverMovedEvent published at [2020-04-25T10:44:31.795] with rover id [Name [ROVER_1] - Plateau UUID [a85b82a9-cdfd-4388-aa47-376b06920e42]], previous position [Coordinates [abscissa = 1, ordinate = 2]], current position [Coordinates [abscissa = 1, ordinate = 3]]
PlateauSwitchedLocationEvent published at [2020-04-25T10:44:31.796] with plateau id [a85b82a9-cdfd-4388-aa47-376b06920e42], position released [Coordinates [abscissa = 1, ordinate = 2]], position occupied [Coordinates [abscissa = 1, ordinate = 3]]
RoverMovedEvent published at [2020-04-25T10:44:31.796] with rover id [Name [ROVER_1] - Plateau UUID [a85b82a9-cdfd-4388-aa47-376b06920e42]], previous position [Coordinates [abscissa = 1, ordinate = 3]], current position [Coordinates [abscissa = 1, ordinate = 4]]
PlateauSwitchedLocationEvent published at [2020-04-25T10:44:31.796] with plateau id [a85b82a9-cdfd-4388-aa47-376b06920e42], position released [Coordinates [abscissa = 1, ordinate = 3]], position occupied [Coordinates [abscissa = 1, ordinate = 4]]
RoverTurnedEvent published at [2020-04-25T10:44:32.795] with rover id [Name [ROVER_1] - Plateau UUID [a85b82a9-cdfd-4388-aa47-376b06920e42]], previous orientation [Orientation [NORTH]], current orientation [Orientation [WEST]]
RoverMovedEvent published at [2020-04-25T10:44:32.795] with rover id [Name [ROVER_1] - Plateau UUID [a85b82a9-cdfd-4388-aa47-376b06920e42]], previous position [Coordinates [abscissa = 1, ordinate = 4]], current position [Coordinates [abscissa = 0, ordinate = 4]]
PlateauSwitchedLocationEvent published at [2020-04-25T10:44:32.795] with plateau id [a85b82a9-cdfd-4388-aa47-376b06920e42], position released [Coordinates [abscissa = 1, ordinate = 4]], position occupied [Coordinates [abscissa = 0, ordinate = 4]]
 ```
 
 To prevent this concurrency issue, we implement the so-called `Optimistic Locking` pattern, which guards against this kind of concurrency problem without any lock, but by checking possible conflict changes.
 
 We set a **version** attribute to each `Entity`, and this version attribute would be incremented every time a *state-altering* command is executed anywhere inside the Application.
 
More precisely, when for example the `Rover` has just received a `Command` and is ready to publish the corresponding `Domain Event`, we set the rover's current version attribute into the `Event` via the object [RoverIdentifierDto](src/main/java/com/game/domain/model/entity/rover/RoverIdentifierDto.java).
 
For example, when the Rover is publishing a `RoverTurnedEvent`, its `roverId` property is populated via the rover identifier along with its current version.

 ```java

	public void turnLeft() {

		RoverTurnedEvent event = new RoverTurnedEvent.Builder().withRoverId(new RoverIdentifierDto(getId(), getVersion())).withPreviousOrientation(orientation)
				.withCurrentOrientation(orientation.turnLeft()).build();

		applyAndPublishEvent(event, turnRover);

	}
	
```
Then when it is time to save the `Rover`, the Rover checks its version attribute against the one persisted in the `Rover Repository` via the method *checkAgainstVersion*. If nobody has changed the `Rover` in the meantime, the version numbers should match.

 ```java
 
 public class RoverServiceImpl implements RoverService {

...
@Override
	public void updateRoverWithOrientation(RoverIdentifierDto roverId, Orientation orientation) {
		Rover rover = this.getRover(roverId.getId());
		rover.checkAgainstVersion(roverId.getVersion());
		rover.setOrientation(orientation);
		this.updateRover(rover);
	}
...
```
If it is not the case, a [OptimisticLockingException](src/main/java/com/game/domain/model/exception/OptimisticLockingException.java) is thrown to the end user with a specific error message and error code.

 ```java

public void checkAgainstVersion(int currentVersion) {
		if (currentVersion == this.getVersion()) {
			this.setVersion(this.getVersion() + 1);
		} else {
			throw new GameException(new OptimisticLockingException(String.format(GameExceptionLabels.CONCURRENT_MODIFICATION_ERROR_MESSAGE, this)));
		}
	}
```

### Design Patterns

We have used a few Gang of Four design patterns along the way. Below is the non-exhaustive list:

**Visitor**: used by [GameServiceImpl](src/main/java/com/game/domain/application/service/GameServiceImpl.java) when executing a `Command` or `List of Commands`. This helpful to call the right *execute* method without any `instanceof` instructions.

 ```java
	@Override
	public void execute(List<ApplicationCommand> commands) {
		GameServiceCommandVisitor commandVisitor = new GameServiceCommandVisitor(this);
		commands.forEach(command -> command.acceptVisitor(commandVisitor));
	}
```

**Observer**: used by [DomainEventPublisherSubscriber](src/main/java/com/game/domain/model/event/DomainEventPublisherSubscriber.java) to register `Event Subscribers` in the `Application Service` and call their *handleEvent* method once the `Event` is published by the Aggregate.

**Template Method**: used classically (before Java 8) in the [EntityValidator](src/main/java/com/game/domain/model/validation/EntityValidator.java) to specify the successive steps of the validation process (which can not be overridden as marked as `final`), but by allowing for a distinct implementation of step, here *doValidate* by the subclasses.

 ```java

/**
	 * Example of Template method pattern.
	 * The overall method process is defined here and can not be overridden as marked as final.
	 * Subclasses can only override the protected {@link doValidate} method.
	 * @see https://en.wikipedia.org/wiki/Template_method_pattern
	 */
	public final T validate() {
		doValidate();
		notificationHandler.checkValidationResult();
		afterValidate();
		return entity();
	}
	
	/**
	 * Method to be overridden by the subclasses
	 */
	protected  abstract void doValidate();
	
	/**
	 * Callback after validation if needed
	 */
	protected  void afterValidate() {
		// do nothing by default
	}
 ```
Used as well in a more modern form by using java 8 functions, by example in [EntityBaseDomainEventPublisher](src/main/java/com/game/domain/model/entity/EntityBaseDomainEventPublisher.java), by specifying the order of the functions taken as parameters, but which have to be defined by the caller at runtime.

```java

public class EntityBaseDomainEventPublisher extends BaseDomainEventPublisher {

	public void applyAndPublishEvent(DomainEvent event, Function<DomainEvent, DomainEvent> function,
			BiFunction<Exception, DomainEvent, DomainEvent> exceptionFunction) {
		try {
			function.andThen(publishAndStore).apply(event);
		} catch (Exception exception) {
			DomainEvent exceptionEvent = null;
			try {
				exceptionEvent = exceptionFunction.apply(exception, event);
				publishEventFunction.apply(exceptionEvent);
				// needed as whatever the exceptionFunction is supposed to do
				// (throwing an exception or not) we want to store the event
			} finally {
				eventStoreFunction.apply(exceptionEvent);
			}
		}
	}
 ```
 **Strategy**: used by the [EntityValidator](src/main/java/com/game/domain/model/validation/EntityValidator.java) which delegates the validation responsibility to a [ValidationNotificationHandler](src/main/java/com/game/domain/model/validation/ValidationNotificationHandler.java) component, which allows to enable the selection of the validation algorithm at runtime.
 
 ```java
 public final T validate() {
		doValidate();
		notificationHandler.checkValidationResult();
		afterValidate();
		return entity();
	}
```
	 
**Decorator**: used by the [RelativisticTwoDimensions](src/main/java/com/game/domain/model/entity/dimensions/RelativisticTwoDimensions.java) Value Object which decorates a classical Dimensions Value Object at runtime and assigns a different algorithm to calculate the object's width and height (in this case multiplying by the `Lorentz factor`)

```java

public RelativisticTwoDimensions(int speed, TwoDimensions dimensions) {
		this(dimensions);
		this.observerSpeed = speed;
		this.lorentzFactor = calculateLorentzFactor(observerSpeed);
	}

	private RelativisticTwoDimensions(TwoDimensions dimensions) {
		this.dimensions = dimensions;
	}
	
	@Override
	public int getWidth() {
		return (int)(lorentzFactor * dimensions.getWidth());
	}

	@Override
	public int getHeight() {
		return (int)(lorentzFactor * dimensions.getHeight());
	}

```

**Factory Method**: used by [PlateauFactory](src/main/java/com/game/domain/model/service/plateau/PlateauFactory.java) to create a classical vs relativistic Plateau

```java
public static Plateau createPlateau(UUID uuid, TwoDimensionalCoordinates dimensions, int speed) {
		
		Plateau plateau = null;
		if (speed < GameContext.MINIMAL_RELATIVISTIC_SPEED) {
			plateau = new Plateau(uuid, new TwoDimensions(
					new TwoDimensionalCoordinates(dimensions.getAbscissa(), dimensions.getOrdinate())));
		} else {
			plateau = new Plateau(uuid, new RelativisticTwoDimensions(speed, new TwoDimensions(
					(new TwoDimensionalCoordinates(dimensions.getAbscissa(), dimensions.getOrdinate())))));
		}
		
		return plateau;
		
	}
```

**Singleton**: used by the [Service Locator](src/main/java/com/game/domain/model/service/locator/ServiceLocator.java) as well as the [GameContext](src/main/java/com/game/domain/application/context/GameContext.java), as only one unique instance is needed for the game. In both cases, eager instantiation has been implemented.

```java

private static GameContext GAME_CONTEXT = new GameContext();


	private GameContext() {
		configure();
	}
	
	public static GameContext getInstance() {
		return GAME_CONTEXT;
	}

	/**
	 * Configure the game with the on-demand implementations
	 */
	private void configure() {
		ServiceLocator locator = new ServiceLocator();
		locator.loadApplicationService(ServiceLocator.GAME_SERVICE, new GameServiceImpl());
		PlateauService plateauService = new PlateauServiceImpl(new InMemoryPlateauRepositoryImpl());
		locator.loadDomainService(ServiceLocator.PLATEAU_SERVICE, plateauService);
		locator.loadDomainService(ServiceLocator.ROVER_SERVICE, new RoverServiceImpl(plateauService , new InMemoryRoverRepositoryImpl()));
		locator.loadEventStore(ServiceLocator.EVENT_STORE, new EventStoreImpl());
		ServiceLocator.load(locator);
	}
	
```

**Builder**: Used in many classes as soon as the constructor parameters are ambiguous or too numerous.


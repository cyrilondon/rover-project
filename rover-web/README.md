# ROVER Web

This project allows you to send web commands to interact with the [Rover Domain Model](../rover-model), by exposing  RESTful services using `Jersey` deployed in a lightweight container [Grizzly](https://javaee.github.io/grizzly/).

Basically, [Eclipse Jersey](https://eclipse-ee4j.github.io/jersey/) is a REST framework that provides a [JAX-RS (JSR-370)](https://jcp.org/en/jsr/detail?id=370) implementation.

In `Hexagonal Architecture`, this is equivalent to add a second adapter, i.e a JAX-RS adapter (top left pink bloc), in addition to the [GameFileAdapter](../rover-model/src/main/java/com/game/adapter/file/GameFileAdapter.java).

Both adapters communicate with the Domain Model via the Port [GameService](../rover-model/src/main/java/com/game/domain/application/service/GameService.java).

<img src="src/main/resources/hexagonal_architecture.PNG" />


## Quick start

1. Download and install [Maven](http://maven.apache.org/install.html).
2. Clone, build and install the [Rover Domain Model](../rover-model).
3. Clone the current git project
4. The `rover-web` directory contains a standard Maven project structure:
 	 
   Project build and management configuration is described in the `pom.xml` located in the project root directory.
 	
   Project sources are located under `src/main/java`.
 	
   Project test sources are located under `src/test/java`.
 	
5. Run the following maven command: `mvn exec:java`

   This will run the [Main](src/main/java/com/game/Main.java) class responsible for bootstrapping the [Grizzly](https://javaee.github.io/grizzly/) container as well as configuring and deploying the project's JAX-RS application to the container.

The application starts and you should soon see the following notification in your console:

```java
mai 02, 2020 11:19:34 AM org.glassfish.grizzly.http.server.NetworkListener start
INFOS: Started listener bound to [localhost:8080]
Jersey app started with WADL available at http://localhost:8080/game/application.wadl
Hit enter to stop it...
mai 02, 2020 11:19:34 AM org.glassfish.grizzly.http.server.HttpServer start
INFOS: [HttpServer] Started.
```

This informs you that the application has been started and it's WADL descriptor is available at http://localhost:8080/game/application.wadl URL.

Actually, Jersey contains support for [Web Application Description Language (WADL)](https://javaee.github.io/wadl/). WADL is a XML description of a deployed RESTful web application.

You can retrieve the WADL content by executing a `curl http://localhost:8080/game/application.wadl` command in your console or by typing the WADL URL into your favorite browser. 

You should get back an XML document in describing your deployed RESTful application in a WADL format. To learn more about working with WADL, check the [Chapter 18, WADL Support](https://eclipse-ee4j.github.io/jersey.github.io/documentation/latest/wadl.html) chapter. 


## JAX-RS Resources

With no surprise, we have implemented two JAX-RS Resources, [PlateauResource](src/main/java/com/game/resource/plateau/PlateauResource.java) and [RoverResource](src/main/java/com/game/resource/rover/RoverResource.java). 

Let focus first on `PlateauResource`

```java

/**
 * Root resource (exposed at "v1/plateau" path)
 */
@Path("v1/plateau")
public class PlateauResource {

	GameService gameService = GameContext.getInstance().getGameService();

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to the
	 * client as "text/plain" media type.
	 *
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() {
		return "Got Plateau Resource!";
	}
```
We first define the `@Path` value as the URL at which the Resource will be accessible.

You can notice here the **v1** prefix to handle the web service versioning. In this example, we use the so-called `URI versioning` (the most straightforward versioning) but other approaches are also possible (as explained in this [article](https://restfulapi.net/versioning/)).

As an instance variable, we inject the `Port interface` [GameService](../rover-model/src/main/java/com/game/domain/application/service/GameService.java) by which we will reach the Rover `Domain Model`.

Our first very method implements a usual ping, which tells us that the `PlateauResource` is available.

To test it, you can do a curl on the given URL `curl -v http://localhost:8080/game/v1/plateau`

```java
C:\Users\cyril>curl -v http://localhost:8080/game/v1/plateau
*   Trying ::1...
* TCP_NODELAY set
*   Trying 127.0.0.1...
* TCP_NODELAY set
* Connected to localhost (127.0.0.1) port 8080 (#0)
> GET /game/v1/plateau HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.55.1
> Accept: */*
>
< HTTP/1.1 200 OK
< Content-Type: text/plain
< Content-Length: 21
<
Got Plateau Resource!* Connection #0 to host localhost left intact
```

If you are lucky, you get a `200 OK` HTTP Response Code as well as the expected text `Got Plateau Resource!`.





		

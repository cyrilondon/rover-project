# ROVER Web

This project allows you to send web commands to interact with the [Rover Domain Model](../rover-model), by exposing  RESTful services using `Jersey` deployed in a lightweight container [Grizzly](https://javaee.github.io/grizzly/).

Basically, [Eclipse Jersey](https://eclipse-ee4j.github.io/jersey/) is a REST framework that provides a [JAX-RS (JSR-370)](https://jcp.org/en/jsr/detail?id=370) implementation.


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

You can retrieve the WADL content by executing a `curl http://localhost:8080/game/application.wadl` command in your console or by typing the WADL URL into your favorite browser. 

You should get back an XML document in describing your deployed RESTful application in a WADL format. To learn more about working with WADL, check the [Chapter 18, WADL Support](https://eclipse-ee4j.github.io/jersey.github.io/documentation/latest/wadl.html) chapter. 



		

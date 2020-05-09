 [![Build Status](https://travis-ci.com/cyrilondon/rover-project.svg?branch=master)](https://travis-ci.com/cyrilondon/rover-project)

# ROVER Project
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

- possibility to send commands from file parsing and web client
- possibility to play on many plateaus at the same time.
- persistence of current state of all plateaus and attached rovers.
- persistence of all the events which occurred in the model in a dedicated event store.
- finer business exception handling with all the scenarii handled (rover moves out of the plateau, rover collides with another rover, rover wrongly initialized, etc.).
- possibility to create a relativistic plateau, i.e. whose dimensions obey Einstein's special relativity rules (more accurate for observers/NASA engineers moving close to speed of light).
- possibility to send commands in parallel, even for the same rover (concurrency handled by optimistic locking)
- possibility for the plateau to make the rover move with a step greater than one (default value)


## Quick start

1. Download and install [maven](http://maven.apache.org/install.html).
2. Clone the project via the command `git clone https://github.com/cyrilondon/rover-project.git`
3. Under `rover-project` folder,  execute `mvn test` to compile and test the sub-modules (optional)
4. Go to the [model project](rover-model/) and try to understanding it first, by cloning and reading the related documentation.
5. Go to the [web project](rover-web), start the server and send commands.

Have fun!

		

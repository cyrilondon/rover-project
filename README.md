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

The first line of input is the upper-right coordinates of the plateau, the lower-left coordinates are assumed to be 0,0. The following input is information pertaining to the rovers that have been deployed, wich gives the rover's position, wich is made up of two integers and a letter separated by spaces, corresponding to the x and y co-ordinates and the rover's orientation. The second line is a series of instructions telling the rover how to explore the plateau and it expects an array of characters, where each character is a command for the robot. 'L' means "turn left", 'R' means "turn right" and 'M' means "move one step towards your direction".



## Objectives

In this exercise, we would like to present a step-by-step process driven by the three following software practices

### Domain Driven Design 
We start by focusing on our domain (entities) and services, which both lie at the core of our application.
If you follow our commits on the flow, you will notice that designing and properly testing our entities is our very first concern.

### Hexagonal Architecture
Once our domain is built, we isolate it properly from the in and out systems by the so-called adapters and ports (interfaces).

### Test driven
Our goal is to propose a final project covered at least at 90% by unit testing.


## Quick start

1. Download and install [maven](http://maven.apache.org/install.html).
2. Go to the root of the project and type `mvn clean install`. This will build the project.
3. In order to run the project, not yet ready....;-)

Enjoy! :smiley:  (currently rather :mask:)


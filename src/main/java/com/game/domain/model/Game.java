package com.game.domain.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.game.domain.model.entity.Board;
import com.game.domain.model.entity.Robot;

public class Game {

	Game() {
	}

    Board board;

    Map<String, Robot> robots = new ConcurrentHashMap<>();

	public Board getBoard() {
		return board;
	}

	public Robot getRobot(String name) {
		return robots.get(name);
	}
	
	public void addRobot(Robot robot) {
		robots.putIfAbsent(robot.getName(), robot);
	}
	
	/**
	 * In case for example of a robot moving out of the board, it will be removed
	 * from the game
	 * 
	 * @param robot
	 */
	public void removeRobot(String robotName) {
		robots.remove(robotName);
	}

	public int getNumberOfRobots() {
		return robots.size();
	}

	
}

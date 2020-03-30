package com.game.domain.model.entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {

	private Game() {
	}
	
	/**
	 * @Todo To add in Robot entity or leave here?
	 */
	public static final String ROBOT_NAME_PREFIX = "ROBOT_";
	
	private AtomicInteger counter = new AtomicInteger(0);

	private static Game soleInstance = new Game();

	private Board board = new Board();

	private Map<String, Robot> robots = new ConcurrentHashMap<>();

	public static Game getInstance() {
		return soleInstance;
	}

	public Board getBoard() {
		return board;
	}

	public Robot getRobot(String name) {
		return robots.get(name);
	}
	
	public void addRobot(Robot robot) {
		int robotNumber = counter.addAndGet(1);
		robots.putIfAbsent(ROBOT_NAME_PREFIX+ robotNumber, robot);
	}
	
	/**
	 * In case for example of a robot moving out of the board, it will be removed from the game
	 * @param robot
	 */
	public void removeRobot(String  robotName) {
		robots.remove(robotName);
	}
	
	public int getNumberOfRobots() {
		return robots.size();
	}
	
	public void reset() {
		board = new Board();
		robots.clear();
		counter.set(0);
	}
}

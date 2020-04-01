package com.game.domain.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.game.domain.model.entity.Board;
import com.game.domain.model.entity.Rover;

public class Game {

	Game() {
	}

	Board board;

	Map<String, Rover> rovers = new ConcurrentHashMap<>();

	public Board getBoard() {
		return board;
	}

	public Rover getRover(String name) {
		return rovers.get(name);
	}

	public void addRover(Rover rover) {
		rovers.putIfAbsent(rover.getName(), rover);
	}

	/**
	 * In case for example of a rover moving out of the board, it will be removed
	 * from the game
	 * 
	 * @param roverName
	 */
	public void removeRover(String roverName) {
		rovers.remove(roverName);
	}

	public int getNumberOfRovers() {
		return rovers.size();
	}

}

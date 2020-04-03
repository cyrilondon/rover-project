package com.game.domain.model.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

import com.game.domain.model.entity.Board;
import com.game.domain.model.entity.dimensions.TwoDimensionalCoordinates;

public class BoardServiceImplTest {

	private final static int WIDTH = 5;

	private final static int HEIGHT = 5;

	private static final int OBSERVER_SPEED = Math.multiplyExact(2, (int) Math.pow(10, 7));

	BoardServiceImpl boardService = new BoardServiceImpl();

	@Test
	public void testInitializeBoard() {
		Board board = boardService.initializeBoard(new TwoDimensionalCoordinates(WIDTH, HEIGHT));
		assertThat(board.getWidth()).isEqualTo(WIDTH);
		assertThat(board.getWidth()).isEqualTo(HEIGHT);
	}

	@Test
	public void testInitializeRelativisticBoard() {
		Board board = boardService.initializeRelativisticBoard(OBSERVER_SPEED,
				(new TwoDimensionalCoordinates(WIDTH, HEIGHT)));
		assertThat(board.getWidth()).isEqualTo(WIDTH - 1);
		assertThat(board.getWidth()).isEqualTo(HEIGHT - 1);
	}

}

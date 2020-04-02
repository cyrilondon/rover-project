package com.game.domain.model.entity;

import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.validation.EntityValidator;
import com.game.domain.model.validation.ValidationNotificationHandler;

public class BoardValidator extends EntityValidator<Board> {

	public BoardValidator(Board board, ValidationNotificationHandler handler) {
		super(board, handler);
	}

	@Override
	public void doValidate() {
		if (entity().getWidth() < 0)
			this.notificationHandler()
					.handleError(String.format(GameExceptionLabels.BOARD_NEGATIVE_WIDTH, entity().getWidth()));
		
		if (entity().getHeight() < 0)
			this.notificationHandler()
					.handleError(String.format(GameExceptionLabels.BOARD_NEGATIVE_HEIGHT, entity().getHeight()));
		
	}

}

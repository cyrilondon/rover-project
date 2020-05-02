package com.game.domain.application.command;

import com.game.domain.application.service.GameServiceCommandVisitor;

public interface ApplicationCommand<T> {
	
	T acceptVisitor(GameServiceCommandVisitor visitor);

}

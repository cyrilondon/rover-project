package com.game.domain.application.command;

import com.game.domain.application.service.GameServiceCommandVisitor;

public interface ApplicationCommand {
	
	void acceptVisitor(GameServiceCommandVisitor visitor);

}

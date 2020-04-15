package com.game.domain.application.command;

import com.game.domain.application.CommandVisitor;

public interface ApplicationCommand {
	
	void acceptVisitor(CommandVisitor visitor);

}

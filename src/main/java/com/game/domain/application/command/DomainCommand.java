package com.game.domain.application.command;

import com.game.domain.application.CommandVisitor;

public interface DomainCommand {
	
	void acceptVisitor(CommandVisitor visitor);

}

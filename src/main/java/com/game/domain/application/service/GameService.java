package com.game.domain.application.service;

import java.util.List;

import com.game.domain.application.command.ApplicationCommand;

/**
 * "Primary" port interface as described by Alistair CockBurn in his original
 * paper, i.e. port on the right side of the hexagon.
 * https://alistair.cockburn.us/hexagonal-architecture/ 
 * The application client will interact with this interface only
 *
 */
public interface GameService extends ApplicationService {
	
	/**
	 * Execute a list of commands
	 * @param commands
	 */
	void execute(List<ApplicationCommand> commands);
	
	/**
	 * Execute a single command
	 * @param command
	 */
	void execute(ApplicationCommand command);

}

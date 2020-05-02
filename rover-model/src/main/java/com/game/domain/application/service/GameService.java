package com.game.domain.application.service;

import java.util.List;

import com.game.domain.application.command.ReturnApplicationCommand;
import com.game.domain.application.command.VoidApplicationCommand;

/**
 * "Primary" port interface as described by Alistair CockBurn in his original
 * paper, i.e. port on the right side of the hexagon.
 * https://alistair.cockburn.us/hexagonal-architecture/ 
 * The application client will interact with this interface only
 *
 */
public interface GameService extends ApplicationService {
	
	/**
	 * Execute a list of void commands (insert/update/delete)
	 * @param commands
	 */
	void execute(List<VoidApplicationCommand> commands);
	
	/**
	 * Execute a single void command (insert/update/delete)
	 * @param command
	 */
	void execute(VoidApplicationCommand command);
	
	/**
	 * Execute a single return command
	 * @param command
	 */
	 <T> T execute(ReturnApplicationCommand<T> command);

}

package com.game.domain.application;

import java.util.List;

import com.game.domain.application.command.DomainCommand;

/**
 * "Primary" port interface as described by Alistair CockBurn in his original
 * paper, i.e. port on the right side of the hexagon.
 * https://alistair.cockburn.us/hexagonal-architecture/ 
 * The application client will interact with this interface only
 *
 */
public interface GameService extends ApplicationService {
	
	void execute(List<DomainCommand> commands);

}

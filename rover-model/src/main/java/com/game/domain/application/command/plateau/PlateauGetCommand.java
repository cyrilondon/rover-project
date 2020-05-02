package com.game.domain.application.command.plateau;

import java.util.UUID;

import com.game.domain.application.command.ReturnApplicationCommand;
import com.game.domain.application.service.GameServiceCommandVisitor;
import com.game.domain.model.entity.plateau.Plateau;

public class PlateauGetCommand implements ReturnApplicationCommand<Plateau> {
	
	private UUID id;
	
	public PlateauGetCommand(UUID uuid) {
		this.id = uuid;
	}

	public UUID getId() {
		return id;
	}

	@Override
	public Plateau acceptVisitor(GameServiceCommandVisitor visitor) {
		return visitor.visit(this);
	}


}

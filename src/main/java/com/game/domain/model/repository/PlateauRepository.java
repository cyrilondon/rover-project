package com.game.domain.model.repository;

import com.game.domain.model.entity.Plateau;

public interface PlateauRepository {
	
	public Plateau getPlateau();
	
	public void addPlateau(Plateau plateau);

}

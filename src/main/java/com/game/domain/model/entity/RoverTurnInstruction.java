package com.game.domain.model.entity;

import com.game.domain.model.entity.enums.GameEnum;

public enum RoverTurnInstruction implements GameEnum<String>{
	
	LEFT("L"), RIGHT("R");
	
	private String value;
	
	RoverTurnInstruction(String value){
		this.value= value;
	}
	
	public String getValue() {
		return value;
	}


}

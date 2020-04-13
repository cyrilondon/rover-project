package com.game.domain.model.entity;

import com.game.domain.model.entity.enums.GameEnum;

public enum RoverInstruction implements GameEnum<String>{
	
	LEFT("L"), RIGHT("R"), MOVE("M");
	
	private String value;
	
	RoverInstruction(String value){
		this.value= value;
	}
	
	public String getValue() {
		return value;
	}


}

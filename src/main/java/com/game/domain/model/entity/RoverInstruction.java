package com.game.domain.model.entity;

public enum RoverInstruction {
	
	LEFT("L"), RIGHT("R"), MOVE("M");
	
	private String value;
	
	RoverInstruction(String value){
		this.value= value;
	}
	
	public String getValue() {
		return value;
	}


}

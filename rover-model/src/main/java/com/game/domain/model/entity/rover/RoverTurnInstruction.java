package com.game.domain.model.entity.rover;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
	
	private static final Map<String, RoverTurnInstruction> lookup = new HashMap<>();

	static {
		Arrays.stream(RoverTurnInstruction.values()).forEach(item -> lookup.put(item.getValue(), item));
	}

	public static RoverTurnInstruction get(String value) {
		return lookup.get(value);
	}


}

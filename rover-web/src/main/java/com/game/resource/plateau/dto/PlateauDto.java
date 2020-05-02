package com.game.resource.plateau.dto;

public class PlateauDto {

	String uuid;

	int width;

	int height;
	
	public PlateauDto() {};
	
	public PlateauDto(String uuid, int width, int height) {
		this.uuid = uuid;
		this.width = width;
		this.height = height;
	}

	public String getUuid() {
		return uuid;
	}

	public int getWidth() {
		return width;
	}


	public int getHeight() {
		return height;
	}


}

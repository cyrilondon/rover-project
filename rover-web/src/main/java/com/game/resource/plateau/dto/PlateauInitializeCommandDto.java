package com.game.resource.plateau.dto;

public class PlateauInitializeCommandDto {
	
	String uuid;
	
	int width;
	
	int height;

	public PlateauInitializeCommandDto() {}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	};
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}

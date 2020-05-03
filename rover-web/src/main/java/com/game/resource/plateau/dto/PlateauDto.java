package com.game.resource.plateau.dto;

/**
 * Plateau DTO sent back to the user.
 * We use a DTO as we do NOT want the Model to 'leak' to the outside world
 *
 */
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

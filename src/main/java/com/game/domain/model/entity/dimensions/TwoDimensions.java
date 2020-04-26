package com.game.domain.model.entity.dimensions;

/**
 * Plateau dimensions are linked to the coordinates by the number of dimensions (x,y as per now
 * but we could imagine a 3D game.
 * However, we want to hide some methods like {@link TwoDimensionalCoordinates#shiftXXX} which have
 * no sense for the plateau so {@link TwoDimensions} class encapsulates {@link TwoDimensionalCoordinates}
 *
 */
public class TwoDimensions implements TwoDimensionalSpace {
	
	private TwoDimensionalCoordinates coordinates;
	
	public TwoDimensions(TwoDimensionalCoordinates coordinates) {
		this.coordinates = coordinates;
	}
	
	public int getWidth() {
		return coordinates.getAbscissa();
	}
	
	public int getHeight() {
		return coordinates.getOrdinate();
	}
	
	@Override
	public String toString() {
		return String.format("Dimensions width [%d] and height [%d]", getWidth(), getHeight());
	}

}

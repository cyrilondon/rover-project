package com.game.domain.model.entity.dimensions;

import java.math.BigDecimal;
import java.math.MathContext;

import com.game.domain.model.entity.Plateau;

/**
 * Plateau dimensions according to more accurate Einstein's special relativity
 * theory. What would  the {@link TwoDimensionalCoordinates#getWidth()} and
 * {@link TwoDimensionalCoordinates#getHeight()} be as measured from a observer
 * traveling at a speed close to the speed of light ;-) 
 * In case of an observer's high speed, the lengths are contracted in the direction of the movement by
 * the Lorentz factor (usually referred as to the Greek letter gamma). 
 * Example of Gang of Four <b>Decorator pattern</b>, which  allows us to extend or alter the functionality of objects at run-time 
 * by wrapping them in an object of a decorator class.
 * In our case, at {@link Plateau} building time, we will chose a relativistic or classical algorithm.
 * {@see https://en.wikipedia.org/wiki/Special_relativity#Length_contraction}
 *
 */
public class RelativisticTwoDimensions implements TwoDimensionalSpace {

	private TwoDimensions dimensions;

	private static final int SPEED_OF_LIGHT = Math.multiplyExact(3, (int) Math.pow(10, 8));

	/**
	 * speed in m/s
	 */
	private double observerSpeed;

	private double lorentzFactor;

	public RelativisticTwoDimensions(int speed, TwoDimensions dimensions) {
		this(dimensions);
		this.observerSpeed = speed;
		this.lorentzFactor = calculateLorentzFactor(observerSpeed);
	}

	private RelativisticTwoDimensions(TwoDimensions dimensions) {
		this.dimensions = dimensions;
	}

	/**
	 * Calculate the Lorentz factor based on the observer's speed
	 * 
	 * @param observerSpeed2
	 * @return Lorentz factor
	 */
	private double calculateLorentzFactor(double observerSpeed) {
		MathContext precision = new MathContext(2); 
		return Math.sqrt(new BigDecimal(1)
				.subtract(
						new BigDecimal(Math.pow(observerSpeed, 2), precision).divide(new BigDecimal(Math.pow(SPEED_OF_LIGHT, 2)), precision))
				.doubleValue());
	}

	@Override
	public int getWidth() {
		return (int)(lorentzFactor * dimensions.getWidth());
	}

	@Override
	public int getHeight() {
		return (int)(lorentzFactor * dimensions.getHeight());
	}

}

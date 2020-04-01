package com.game.domain.model.entity.dimensions;

import java.math.BigDecimal;
import java.math.MathContext;

import com.game.domain.model.entity.Board;

/**
 * Board dimensions according to more accurate Einstein's special relativity
 * theory. What would be the {@link TwoDimensionalCoordinates#getWidth()} and
 * {@link TwoDimensionalCoordinates#getHeight()} as measured from a observer
 * traveling at a speed close to the speed of light ;-) In case of an observer's
 * high speed, the lengths are contracted in the direction of the movement by
 * the Lorentz factor (usually referred as to the Greek letter gamma). Example of Gang of Four Strategy pattern, which
 * allows us to change the behavior of an algorithm at runtime. In our case, at
 * {@link Board} building time, we will chose a relativistic or classical algorithm.
 * {@see https://en.wikipedia.org/wiki/Special_relativity#Length_contraction}
 *
 */
public class RelativisticTwoDimensions implements TwoDimensionalSpace {

	private TwoDimensionalCoordinates coordinates;

	private static final int SPEED_OF_LIGHT = Math.multiplyExact(3, (int) Math.pow(10, 8));

	/**
	 * speed in m/s
	 */
	private double observerSpeed;

	private double lorentzFactor;

	public RelativisticTwoDimensions(int speed, TwoDimensionalCoordinates coordinates) {
		this(coordinates);
		this.observerSpeed = speed;
		this.lorentzFactor = calculateLorentzFactor(observerSpeed);
	}

	private RelativisticTwoDimensions(TwoDimensionalCoordinates coordinates) {
		this.coordinates = coordinates;
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
		return (int)(lorentzFactor * coordinates.getWidth());
	}

	@Override
	public int getHeight() {
		return (int)(lorentzFactor * coordinates.getHeight());
	}

}

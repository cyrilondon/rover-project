package com.game.domain.model.exception;

/**
 * We externalize all error messages and code in this class to have a better
 * visibility of all possible error scenarios in the game.
 *
 */
public class GameExceptionLabels {

	/**
	 * Final error message example: [ERR-000] Broken precondition: Missing Rover
	 * name
	 */
	public static final String ERROR_CODE_AND_MESSAGE_PATTERN = "[%s] %s";

	public static final String ERROR_MESSAGE_SEPARATION_PATTERN = "%s - %s";

	public static final String ILLEGAL_ARGUMENT_CODE = "ERR-000";

	public static String PRE_CHECK_ERROR_MESSAGE = "Broken precondition: %s";

	public static final String MISSING_ROVER_POSITION = "Missing Rover position";

	public static final String MISSING_ROVER_ORIENTATION = "Missing Rover orientation";

	public static final String MISSING_ROVER_NAME = "Missing Rover name";

	public static final String MISSING_BOARD_DIMENSIONS = "Missing Board dimensions";

	public static final String ILLEGAL_ORIENTATION_VALUE = "Orientation value not allowed";

	public static final String MISSING_BOARD_CONFIGURATION = "Missing Board configuration";

	public static final Object NOT_ALLOWED_ADDING_ROVER_ERROR = "It is not allowed to add a Rover";

	public static final String ENTITY_VALIDATION_ERROR_CODE = "ERR-001";

	public static final String BOARD_NEGATIVE_WIDTH = "Board width [%d] should be strictly positive";

	public static final String BOARD_NEGATIVE_HEIGHT = "Board height [%d] should strictly positive";

}

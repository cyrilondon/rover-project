package com.game.domain.model.exception;

/**
 * We externalize all error messages and code in this class
 * to have a better visibility of all possible error scenarios in the game.
 *
 */
public class GameExceptionLabels {
	
	/**
	 * Final error message example:  [ERR-000] Broken precondition: Missing Robot name
	 */
	public static final String ERROR_CODE_AND_MESSAGE_PATTERN = "[%s] %s";
	
	public static final String ILLEGAL_ARGUMENT_CODE = "ERR-000";

	public static String PRE_CHECK_ERROR_MESSAGE = "Broken precondition: %s";
	
	public static final String MISSING_ROBOT_POSITION = "Missing Robot position";

	public static final String MISSING_ROBOT_ORIENTATION = "Missing Robot orientation";

	public static final String MISSING_ROBOT_NAME = "Missing Robot name";

	public static final String MISSING_BOARD_DIMENSIONS = "Missing Board dimensions";

	public static final String ILLEGAL_ORIENTATION_VALUE = "Orientation value not allowed";
	

}

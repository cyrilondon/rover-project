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

	public static final String PRE_CHECK_ERROR_MESSAGE = "Broken precondition: %s";
	
	public static final String MISSING_ROVER_IDENTIFIER = "Missing Rover identifier";

	public static final String MISSING_ROVER_POSITION = "Missing Rover position";

	public static final String MISSING_ROVER_ORIENTATION = "Missing Rover orientation";
	
	public static final String MISSING_PLATEAU_UUID = "Missing Plateau identifiant";

	public static final String MISSING_ROVER_NAME = "Missing Rover name";

	public static final String MISSING_PLATEAU_DIMENSIONS = "Missing Plateau dimensions";

	public static final String ILLEGAL_ORIENTATION_VALUE = "Orientation value not allowed";

	public static final String MISSING_PLATEAU_CONFIGURATION = "Missing Plateau configuration";

	public static final String INITIALIZE_ROVER_NOT_ALLOWED = "It is not allowed to initialize a Rover. Please initialize the Plateau first.";

	public static final String ENTITY_VALIDATION_ERROR_CODE = "ERR-001";

	public static final String PLATEAU_NEGATIVE_WIDTH = "Plateau width [%d] should be strictly positive";

	public static final String PLATEAU_NEGATIVE_HEIGHT = "Plateau height [%d] should strictly positive";
	
	public static final String ROVER_NEGATIVE_X = "Rover X-position [%d] should be strictly positive";
	
	public static final String ROVER_NEGATIVE_Y = "Rover Y-position [%d] should be strictly positive";

	public static final String ROVER_X_OUT_OF_PLATEAU = "Rover with X-position [%d] is out of the Plateau with width [%d]";
	
	public static final String ROVER_Y_OUT_OF_PLATEAU = "Rover with Y-position [%d] is out of the Plateau with height [%d]";
	
	public static final String ENTITY_NOT_FOUND_ERROR_CODE = "ERR-002";
	
	public static final String ENTITY_NOT_FOUND = "Entity [%s] with Id [%s] not found in the Application Repository";
	
	public static final String PLATEAU_LOCATION_ERROR_CODE = "ERR-003";
	
	public static final String PLATEAU_LOCATION_ALREADY_SET_START = "There is already a Rover";

	public static final String PLATEAU_LOCATION_ALREADY_SET = PLATEAU_LOCATION_ALREADY_SET_START + " at position X = [%d] and Y = [%d]";
	
	public static final String ROVER_ILLEGAL_POSITION_ERROR_CODE = "ERR-004";

	



}

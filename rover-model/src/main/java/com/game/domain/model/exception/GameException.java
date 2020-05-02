package com.game.domain.model.exception;

/**
 * In case of something goes wrong during the Game, we want the process to exit thus the choice for {@link RuntimeException}
 * We also want each exception to be identified by a unique code for better incident resolution, debugging, etc.
 * 
 */
public class GameException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private final String errorCode;
	
	public GameException(String message) {
		this(message, null, null);
	}
	
	public GameException(String message, String errorCode) {
		this(message, errorCode, null);
	}
	
	public GameException(String message, String errorCode, Exception cause) {
		super(message, cause);
		this.errorCode = errorCode;
	}
	
	public GameException(String message, Exception cause) {
		super(message, cause);
		this.errorCode = null;
	}
	
	public GameException(Exception cause) {
		this(cause.getMessage(), null, cause);
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	
	
	@Override
	public String getMessage() {
		String msg = getOriginalMessage();
		if (getErrorCode() != null) {
			msg = String.format(GameExceptionLabels.ERROR_CODE_AND_MESSAGE_PATTERN, getErrorCode(), msg);
		}
		return msg;
	}

	public String getOriginalMessage() {
		return super.getMessage();
	}
	
	

}

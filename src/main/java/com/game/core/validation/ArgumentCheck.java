package com.game.core.validation;

import com.game.core.util.StringUtils;
import com.game.domain.model.exception.GameExceptionLabels;
import com.game.domain.model.exception.IllegalArgumentGameException;


public class ArgumentCheck {
	
	public static <T> T preNotNull(final T object, final String message) {
		return requiresNotNull(object, String.format(GameExceptionLabels.PRE_CHECK_ERROR_MESSAGE, message));
	}
	
	private static <T> T requiresNotNull(final T object, final String message) {
		if (object == null) throw new IllegalArgumentGameException(message);
		return object;
	}
	
	public static String preNotEmpty(final String string, final String message) {
		return requiresNotEmpty(string, String.format(GameExceptionLabels.PRE_CHECK_ERROR_MESSAGE, message));
	}
	
	private static String requiresNotEmpty(final String string, final String message) {
		if (!StringUtils.hasText(string)) throw new IllegalArgumentGameException(message);
		return string;
	}

}

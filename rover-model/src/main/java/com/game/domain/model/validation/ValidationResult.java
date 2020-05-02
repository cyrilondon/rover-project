package com.game.domain.model.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ValidationResult {

	private List<String> errorMessages = new ArrayList<>();

	public void addErrorMessage(String message) {
		errorMessages.add(message);
	}

	public String getAllErrorMessages() {
		return errorMessages.stream().collect(Collectors.joining(", "));
	}
	
	public boolean isInError() {
		return errorMessages.size() > 0;
	}

}

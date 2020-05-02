package com.game.domain.model.validation;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

public class ValidationResultTest {
	
	private String ERROR_MSG_1 = "Error Message 1";
	
	private String ERROR_MSG_2 = "Error Message 2";
	
	@Test
	public void testIsInError() {
		ValidationResult result = new ValidationResult();
		result.addErrorMessage(ERROR_MSG_1);
		assertThat(result.isInError()).isTrue();		
	}
	
	@Test
	public void testGetAllErrorMessages() {
		ValidationResult result = new ValidationResult();
		result.addErrorMessage(ERROR_MSG_1);
		result.addErrorMessage(ERROR_MSG_2);
		assertThat(result.getAllErrorMessages()).isEqualTo(String.format("%s, %s", ERROR_MSG_1, ERROR_MSG_2));
	}

}

package com.game.core.util;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

import com.game.core.util.StringUtils;

public class StringUtilsTest {
	
	@Test
	public void hasLengthTest() {
		String stringTest = "test";
		assertTrue(StringUtils.hasLength(stringTest));
	}
	
	@Test
	public void hasLengthTestKOForNull() {
		assertFalse(StringUtils.hasLength(null));
	}
	
	@Test
	public void hasLengthTestKOForEmpty() {
		assertFalse(StringUtils.hasLength(""));
	}
	
	@Test
	public void containsTextTrue() {
		assertTrue(StringUtils.containsText(" tex t  "));
	}
	
	@Test
	public void containsText() {
		assertFalse(StringUtils.containsText("   "));
	}
	
	@Test
	public void hasNoTextBlank() {
		assertFalse(StringUtils.hasText("   "));
	}
	
	@Test
	public void hasNoTextNull() {
		assertFalse(StringUtils.hasText(null));
	}
	
	@Test
	public void hasText() {
		assertTrue(StringUtils.hasText("   z"));
	}


}

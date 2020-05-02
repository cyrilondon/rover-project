package com.game.domain.model.event;

import java.time.LocalDateTime;

import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class BaseDomainEventTest {
	
	@Test
	public void test() {
		
		LocalDateTime now = LocalDateTime.now();
		BaseDomainEvent event = new BaseDomainEvent();
		LocalDateTime occuredOn = event.occuredOn();
		assertThat(now.getDayOfMonth()).isEqualTo(occuredOn.getDayOfMonth());
		assertThat(now.getHour()).isEqualTo(occuredOn.getHour());
		assertThat(now.getSecond()).isEqualTo(occuredOn.getSecond());
	}

}

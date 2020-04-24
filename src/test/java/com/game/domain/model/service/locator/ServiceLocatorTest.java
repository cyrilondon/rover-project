package com.game.domain.model.service.locator;

import com.game.domain.model.event.store.EventStoreImpl;
import com.game.test.util.BaseUnitTest;

import static org.assertj.core.api.Assertions.assertThat;

import org.testng.annotations.Test;

public class ServiceLocatorTest extends BaseUnitTest {
	
	@Test
	public void testConfigure() {
			ServiceLocator mockServiceLocator = new ServiceLocator();
			mockServiceLocator.loadDomainService(ServiceLocator.ROVER_SERVICE, new MockRoverServiceImpl());
			mockServiceLocator.loadDomainService(ServiceLocator.PLATEAU_SERVICE, new MockPlateauServiceImpl());
			mockServiceLocator.loadEventStore(ServiceLocator.EVENT_STORE, new EventStoreImpl());
			ServiceLocator.load(mockServiceLocator);
			
			assertThat(ServiceLocator.getRoverService()).isInstanceOf(MockRoverServiceImpl.class);
			assertThat(ServiceLocator.getPlateauService()).isInstanceOf(MockPlateauServiceImpl.class);
			assertThat(ServiceLocator.getEventStore()).isInstanceOf(EventStoreImpl.class);
		}
	}


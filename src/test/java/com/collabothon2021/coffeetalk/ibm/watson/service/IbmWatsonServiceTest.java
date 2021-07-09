package com.collabothon2021.coffeetalk.ibm.watson.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class IbmWatsonServiceTest {
	@Autowired
	private IbmWatsonService testee;

	@Test
	public void shouldAutowire() {
		assertNotNull(testee);
	}
	
	@Test
	public void test() {
		// given
		InputStream in = Thread.currentThread().getContextClassLoader()
			    .getResourceAsStream("h.mp3");
		
		// when
		String text = testee.mp3ToText(in);
		
		// then
		assertTrue(text.contains("Houston"));
	}
}

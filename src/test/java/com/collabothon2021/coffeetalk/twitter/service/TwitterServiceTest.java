package com.collabothon2021.coffeetalk.twitter.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import twitter4j.HashtagEntity;
import twitter4j.Status;

@SpringBootTest
public class TwitterServiceTest {
	@Autowired
	private TwitterService testee;

	@Test
	public void shouldAutowire() {
		assertNotNull(testee);
	}
	
	@Test
	public void shouldTweet() {
		// given
		String message = RandomStringUtils.randomAlphabetic(50);
		String hashtag1 = "database";
		String hashtag2 = "security";
		List<String> hashtags = Arrays.asList(
				hashtag1,
				hashtag2);
		
		// when
		Status status = testee.tweet(message, hashtags);
		
		// then
		assertNotNull(status);
		HashtagEntity[] hashtagEntities = status.getHashtagEntities();
		assertEquals(5, hashtagEntities.length); // 2 + 3 hard coded
	}
}

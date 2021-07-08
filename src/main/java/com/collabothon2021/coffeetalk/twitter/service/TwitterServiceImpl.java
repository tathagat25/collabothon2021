package com.collabothon2021.coffeetalk.twitter.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

@Component
public class TwitterServiceImpl implements TwitterService {
	
	private static final Logger log = LoggerFactory.getLogger(TwitterServiceImpl.class);

	@Override
	public Status tweet(String message, List<String> hashtags) {
		Twitter twitter = TwitterFactory.getSingleton();
		StringBuilder sb = new StringBuilder("#aloha #collabothon2021 #coffeetalk ");
		for (String hashtag : hashtags) {
			sb.append("#"+hashtag + " ");
		}
		String status = message + " " + sb.toString();
		
		try {
			log.info(String.format("Tweeting %s", status));
			return twitter.updateStatus(status);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}

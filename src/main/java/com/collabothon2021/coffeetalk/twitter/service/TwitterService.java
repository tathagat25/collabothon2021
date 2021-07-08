package com.collabothon2021.coffeetalk.twitter.service;

import java.util.List;

import twitter4j.Status;

public interface TwitterService {
	public Status tweet(String message, List<String> hashtags);
}

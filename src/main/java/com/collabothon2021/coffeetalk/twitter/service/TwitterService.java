package com.collabothon2021.coffeetalk.twitter.service;

import java.util.List;

public interface TwitterService {
	public void tweet(String message, List<String> hashtags);
}

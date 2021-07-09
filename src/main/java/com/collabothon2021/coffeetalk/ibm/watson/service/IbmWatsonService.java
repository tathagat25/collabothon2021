package com.collabothon2021.coffeetalk.ibm.watson.service;

import java.io.InputStream;

public interface IbmWatsonService {
	String mp3ToText(InputStream in);
}

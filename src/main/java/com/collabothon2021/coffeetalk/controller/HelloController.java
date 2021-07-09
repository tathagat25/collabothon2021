package com.collabothon2021.coffeetalk.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.collabothon2021.coffeetalk.controller.dto.AudioFileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.collabothon2021.coffeetalk.jira.model.search.Fields;
import com.collabothon2021.coffeetalk.jira.model.search.Issue;
import com.collabothon2021.coffeetalk.keyword.KeywordService;
import com.collabothon2021.coffeetalk.twitter.service.TwitterService;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class HelloController {

	@Autowired
	private KeywordService keywordService;

	@Autowired
	private TwitterService twitterService;

	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot!";
	}

	/**
	 * curl -X POST --header "Content-Type: application/text" --data 'database task 93 + security' http://localhost:8080/tweet
	 * @param message
	 * @return
	 */
	@PostMapping("/tweet")
	@CrossOrigin
	public List<String> tweet(@RequestBody String message) {
		Issue issue = new Issue();
		issue.fields = new Fields();
		issue.fields.summary = message;
		Map<Issue, List<String>> filteredIssues = keywordService.filterIssues(Arrays.asList(issue));

		// create tweet
		for (Issue key : filteredIssues.keySet()) {
			List<String> hashtags = filteredIssues.get(key);
			twitterService.tweet(key.fields.summary, hashtags);

			return hashtags;
		}

		return Collections.emptyList();
	}

	@CrossOrigin
	@PostMapping(value = "/speech/to/text", consumes = "audio/mp3")
	public String convert(@RequestBody byte[] blob) throws IOException {
		FileOutputStream fos = new FileOutputStream("C:\\Users\\benja\\Downloads\\test.mp3");
		fos.write(blob);
		fos.close();
		return "Hello World";
	}

}

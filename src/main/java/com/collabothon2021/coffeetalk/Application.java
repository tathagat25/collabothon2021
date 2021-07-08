package com.collabothon2021.coffeetalk;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.collabothon2021.coffeetalk.jira.model.search.Issue;
import com.collabothon2021.coffeetalk.jira.model.search.SearchResultRoot;
import com.collabothon2021.coffeetalk.jira.service.JiraService;
import com.collabothon2021.coffeetalk.keyword.KeywordService;

/**
 * main class
 * 
 * @author tathagat
 *
 */
@SpringBootApplication
@EnableScheduling
public class Application {
	
	@Autowired
	private JiraService jiraService;
	
	@Autowired
	private KeywordService keywordService;
	
	@Value("${refreshRate}")
	private long refreshRate;
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	@Scheduled(fixedRateString = "${refreshRate}")
	public void chcekForNewStories() {
		log.debug("BEGIN");
		
		// get stories created since we checked last time
		LocalDateTime localDateTime = LocalDateTime.now();
		localDateTime = localDateTime.minusSeconds(refreshRate/1000); // minus the refresh rate so we compare with last check time
		SearchResultRoot found = jiraService.findCreatedAfter(localDateTime);
		
		if (found.issues.isEmpty()) {
			return;
		}
		
		// filter with keywords
		Map<Issue, List<String>> filteredIssues = keywordService.filterIssues(found.issues);
		
		if (filteredIssues.isEmpty()) {
			return;
		}
		
		// create tweet
		
		// TODO?
		
		log.debug("END");
	}
	
}

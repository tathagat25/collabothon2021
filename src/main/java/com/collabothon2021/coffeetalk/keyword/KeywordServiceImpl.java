package com.collabothon2021.coffeetalk.keyword;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.collabothon2021.coffeetalk.jira.model.search.Issue;

@Component
public class KeywordServiceImpl implements KeywordService {
	private static final Logger log = LoggerFactory.getLogger(KeywordServiceImpl.class);
	private final List<String> keywords = Arrays.asList("database", "security", "interface", "description", "test", "framework", "coffee");

	@Override
	public Map<Issue, List<String>> filterIssues(List<Issue> issues) {
		
		log.info(String.format("Looking for issues having keywords %s", keywords));	
//		return issues.stream()
//				.filter(i -> keywords.stream().anyMatch(i.fields.summary::contains))
////				.collect(Collectors.toList());

		Map<Issue, List<String>> result = new HashMap<Issue, List<String>>();
		
		for (Issue issue : issues) {
			String summary = issue.fields.summary;
			List<String> hashtags = new LinkedList<String>();
			for (String keyword : keywords) {
				if (summary.contains(keyword) ) {
					hashtags.add(keyword);
				}
			}
			if (!hashtags.isEmpty()) {
				result.put(issue, hashtags);
			}
		}
		
		return result;
	}

}

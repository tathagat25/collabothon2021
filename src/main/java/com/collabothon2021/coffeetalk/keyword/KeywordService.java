package com.collabothon2021.coffeetalk.keyword;

import java.util.List;
import java.util.Map;

import com.collabothon2021.coffeetalk.jira.model.search.Issue;

/**
 * Handle keywords
 * 
 * @author tathagat
 *
 */
public interface KeywordService {
	/**
	 * Filter the issues that have the stored keywords in the summary.
	 * @param issues
	 * @return found issues with their hashtags
	 */
	Map<Issue, List<String>> filterIssues(List<Issue> issues);
}

package com.collabothon2021.coffeetalk.keyword;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.collabothon2021.coffeetalk.jira.model.search.Fields;
import com.collabothon2021.coffeetalk.jira.model.search.Issue;

@SpringBootTest
public class KeywordServiceTest {
	@Autowired
	private KeywordService testee;

	@Test
	public void shouldAutowire() {
		assertNotNull(testee);
	}

	@Test
	public void shouldFind() {
		// given
		Issue issue = new Issue();
		issue.id = "XXX";
		issue.fields = new Fields();
		issue.fields.summary = "security feature 1";
		List<Issue> issues = Arrays.asList(issue);

		// when
		Map<Issue, List<String>> filteredIssues = testee.filterIssues(issues);

		// then
		assertTrue(!filteredIssues.isEmpty());
		Issue filteredIssue = filteredIssues.keySet()
				.stream()
				.filter(i -> i.id.equals("XXX")).findFirst().get();
		assertEquals("security feature 1", filteredIssue.fields.summary);
		assertTrue(filteredIssues.get(filteredIssue).contains("security"));
	}
	
	@Test
	public void shouldFindEtended() {
		// given
		Issue issue = new Issue();
		issue.id = "XXX";
		issue.fields = new Fields();
		issue.fields.summary = "security database feature 2";
		List<Issue> issues = Arrays.asList(issue);

		// when
		Map<Issue, List<String>> filteredIssues = testee.filterIssues(issues);

		// then
		assertTrue(!filteredIssues.isEmpty());
		Issue filteredIssue = filteredIssues.keySet()
				.stream()
				.filter(i -> i.id.equals("XXX")).findFirst().get();
		assertEquals("security database feature 2", filteredIssue.fields.summary);
		assertTrue(filteredIssues.get(filteredIssue).containsAll(Arrays.asList("security", "database")));
	}

	@Test
	public void shouldNotFind() {
		// given
		Issue issue = new Issue();
		issue.id = "XXX";
		issue.fields = new Fields();
		issue.fields.summary = "no one cares feature 1";
		List<Issue> issues = Arrays.asList(issue);

		// when
		Map<Issue, List<String>> filteredIssues = testee.filterIssues(issues);

		// then
		assertTrue(filteredIssues.isEmpty());
	}
}

package com.collabothon2021.coffeetalk.jira.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.collabothon2021.coffeetalk.jira.model.id.Root;
import com.collabothon2021.coffeetalk.jira.model.search.Issue;
import com.collabothon2021.coffeetalk.jira.model.search.SearchResultRoot;

/**
 * Test for {@link JiraService}
 * 
 * @author tathagat
 *
 */
@SpringBootTest
public class JiraServiceTest {
	@Autowired
	private JiraService testee;
	
	@Test
	public void shouldAutowire() {
		assertNotNull(testee);
	}
	
	@Test
	public void shouldGetById() {
		// given
		String id = "CB-1";
		
		// when
		Root found = testee.getById(id);
		
		// then
		assertNotNull(found);
		assertEquals("Tathagat", found.fields.creator.displayName);
		assertEquals("mail@tathagat.com", found.fields.creator.emailAddress);
		assertEquals("Create the database", found.fields.summary);
	}
	
	@Test
	public void shouldNotGetByWrongId() {
		// given
		String id = "XXX";
		
		// when
		Root found = testee.getById(id);
		
		// then
		assertNull(found);
	}
	
	@Test
	public void shouldFindCreatedAfter() {
		// given
		LocalDateTime time = LocalDateTime.of(2021, 6, 7, 0, 0);
		// when
		SearchResultRoot found = testee.findCreatedAfter(time);
		
		// then
		assertNotNull(found);
		Issue issue = found.issues.stream().filter(i -> i.key.equals("CB-1")).findFirst().get();
		assertNotNull(issue);
		assertEquals("Tathagat", issue.fields.creator.displayName);
		assertEquals("mail@tathagat.com", issue.fields.creator.emailAddress);
		assertEquals("Create the database", issue.fields.summary);
	}
	
	@Test
	public void shouldNotFindCreatedAfter() {
		// given
		LocalDateTime time = LocalDateTime.now();
		// when
		SearchResultRoot found = testee.findCreatedAfter(time);
		
		// then
		assertTrue(found.issues.isEmpty());
	}
}

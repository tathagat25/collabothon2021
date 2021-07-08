package com.collabothon2021.coffeetalk.jira.service;

import java.time.LocalDateTime;

import com.collabothon2021.coffeetalk.jira.model.id.Root;
import com.collabothon2021.coffeetalk.jira.model.search.SearchResultRoot;

/**
 * our life line to Jira
 * 
 * @author tathagat
 *
 */
public interface JiraService {
	Root getById(String id);
	
	SearchResultRoot findCreatedAfter(LocalDateTime localDateTime);
}

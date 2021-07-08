package com.collabothon2021.coffeetalk.jira.model.search; 
import java.util.List; 
public class SearchResultRoot{
    public String expand;
    public int startAt;
    public int maxResults;
    public int total;
    public List<Issue> issues;
}

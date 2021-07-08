package com.collabothon2021.coffeetalk.jira.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.collabothon2021.coffeetalk.jira.model.id.Root;
import com.collabothon2021.coffeetalk.jira.model.search.SearchResultRoot;

/**
 * Our life line to Jira
 * 
 * @author tathagat
 *
 */
@Component
public class JiraServiceImpl implements JiraService {
//	private static final Logger log = LoggerFactory.getLogger(JiraServiceImpl.class);

	private RestTemplate restTemplate = new RestTemplate();
	
//	@Value("${jira.poller.refreshRate}")
//	private long refreshRate;
	
	@Value("${jira.poller.baseUrl}")
	private String baseUrl;
	
	@Value("${jira.poller.plainCreds}")
	private String plainCreds;
	
//	@Scheduled(fixedRateString = "${jira.poller.refreshRate}")
//	public void chcekForNewStories() {
//		log.debug("BEGIN");
//		
//		LocalDateTime localDateTime = LocalDateTime.now();
//		localDateTime = localDateTime.minusHours(2); // time offset to JIRA server
////		localDateTime = localDateTime.minusSeconds(refreshRate/1000); // minus the refresh rate so we compare with last check time
//		String changedSince = localDateTime.format(dateFormat);
//		
//		HttpEntity<String> request = getSecureRequest();
//		log.info(String.format("Checking for all stories changed since %s", changedSince));
//		ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.GET, request, String.class, changedSince);
//		
//		log.debug(response.getStatusCode().toString());
//		String body = response.getBody();
//		log.debug(body);
//		
////		jsonparser
//		
//		log.debug("END");
//	}
	
	private HttpEntity<String> getSecureRequest() {
		byte[] plainCredsBytes = plainCreds.getBytes();
		byte[] base64CredsBytes = Base64.getEncoder().encode(plainCredsBytes);
		String base64Creds = new String(base64CredsBytes);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);
		
		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		return request;
	}

	@Override
	public Root getById(String id) {
		HttpEntity<String> request = getSecureRequest();
		String url = baseUrl + "issue/" + id;
		
		ResponseEntity<Root> response = restTemplate.exchange(url, HttpMethod.GET, request, Root.class);
		
//		Root root = new Gson().fromJson(response.getBody(), Root.class);
		
		return response.getBody();
	}

	@Override
	public SearchResultRoot findCreatedAfter(LocalDateTime localDateTime) {
		HttpEntity<String> request = getSecureRequest();
		localDateTime = localDateTime.minusHours(2); // time offset to JIRA server
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm");
		String createdSince = localDateTime.format(dateFormat);
		String url = baseUrl + "search?jql=created>'{datetime}'";
		
		ResponseEntity<SearchResultRoot> response = restTemplate.exchange(url, HttpMethod.GET, request, SearchResultRoot.class, createdSince);
		
		return response.getBody();
	}
}

package com.spring.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class SleepApiRepository {
	@Autowired
	private RestTemplate restTemplate;

	public String sleep(long msSec) {
		String url = String.format("http://localhost/sleep/%d", msSec);
		return restTemplate.getForObject(url, String.class);
	}
}

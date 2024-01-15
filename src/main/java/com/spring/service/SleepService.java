package com.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.repository.SleepApiRepository;

@Service
public class SleepService {
	@Autowired
	private SleepApiRepository sleepApiRepository;

	public String timeout() {
		return sleepApiRepository.sleep(5000);
	}
}

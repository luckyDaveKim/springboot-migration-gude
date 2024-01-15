package com.spring.service;

import java.time.ZoneId;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.spring.model.GreetingType;
import com.spring.repository.ZoneRepository;

@Service
public class ZoneService {
	private static final ZoneId SEOUL_ZONE_ID = ZoneId.of("Asia/Seoul");

	@Autowired
	private ZoneRepository zoneRepository;

	@Cacheable(value = "zones")
	public Set<String> getAllZones() {
		return zoneRepository.getAllZones();
	}

	@Cacheable(value = "time", key = "#zoneIdText")
	public String getTimeByZone(String zoneIdText) {
		ZoneId zoneId = parseZoneId(zoneIdText);

		GreetingType greetingType;
		if (Objects.equals(SEOUL_ZONE_ID, zoneId)) {
			greetingType = GreetingType.KOREAN;
		} else {
			greetingType = GreetingType.ENGLISH;
		}

		String timeText = zoneRepository.getTimeText(zoneId);

		return String.format(greetingType.getGreetingFormat(), zoneId, timeText);
	}

	public ZoneId parseZoneId(String zoneIdText) {
		try {
			return ZoneId.of(zoneIdText);
		} catch (Exception e) {
			// 예외 발생 시, system default zone 사용
			return ZoneId.systemDefault();
		}
	}
}

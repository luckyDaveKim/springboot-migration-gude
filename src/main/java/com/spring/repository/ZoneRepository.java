package com.spring.repository;

import static java.time.format.DateTimeFormatter.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Set;

import org.springframework.stereotype.Repository;

@Repository
public class ZoneRepository {
	public Set<String> getAllZones() {
		return ZoneId.getAvailableZoneIds();
	}

	public String getTimeText(ZoneId zoneId) {
		return LocalDateTime.now(zoneId).format(ISO_DATE_TIME);
	}
}

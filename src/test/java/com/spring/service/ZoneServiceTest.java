package com.spring.service;

import static java.time.ZoneId.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.ZoneId;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.spring.repository.ZoneRepository;

class ZoneServiceTest {
	@Mock
	private ZoneRepository zoneRepository;

	@InjectMocks
	private ZoneService zoneService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void shouldGetZones() {
		// given
		Set<String> zones = ZoneId.getAvailableZoneIds();
		given(zoneRepository.getAllZones()).willReturn(zones);

		// when
		Set<String> foundZones = zoneService.getAllZones();

		// then
		then(zoneRepository).should().getAllZones();
		assertEquals(zones.size(), foundZones.size());
	}

	@Test
	void shouldGetEnglishTextTimeByZone() {
		// given
		given(zoneRepository.getTimeText(any())).willReturn("1991-03-26T00:12:34.56");
		String zoneIdText = "America/Los_Angeles";

		// when
		String time = zoneService.getTimeByZone(zoneIdText);

		// then
		then(zoneRepository).should().getTimeText(any());
		assertEquals("Hello. The time of zone: 'America/Los_Angeles' is '1991-03-26T00:12:34.56'!", time);
	}

	@Test
	void shouldGetKoreanTextTimeByZone() {
		// given
		given(zoneRepository.getTimeText(any())).willReturn("1991-03-26T12:34:56.78");
		String zoneIdText = "Asia/Seoul";

		// when
		String time = zoneService.getTimeByZone(zoneIdText);

		// then
		then(zoneRepository).should().getTimeText(any());
		assertEquals("안녕하세요. zone: 'Asia/Seoul' 의 시간은... '1991-03-26T12:34:56.78' 입니다!", time);
	}

	@Test
	void shouldParseZoneId() {
		// given
		String zoneIdText = "Cuba";

		// when
		ZoneId zoneId = zoneService.parseZoneId(zoneIdText);

		// then
		assertEquals(ZoneId.of(zoneIdText), zoneId);
	}

	@Test
	void shouldSystemDefaultZone_whenZoneIdParseException() {
		// given
		String zoneIdText = "IS NOT ZONE ID";

		// when
		ZoneId zoneId = zoneService.parseZoneId(zoneIdText);

		// then
		assertEquals(systemDefault(), zoneId);
	}
}

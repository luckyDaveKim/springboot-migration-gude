package com.spring.controller;

import static org.springframework.web.bind.annotation.RequestMethod.*;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.service.ZoneService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/zone")
@Tag(name = "zone 기반 시간 조회 API")
public class ZoneController {
	@Autowired
	private ZoneService zoneService;

	@RequestMapping(method = GET)
	public Set<String> getAllZones() {
		return zoneService.getAllZones();
	}

	@RequestMapping(value = "/time", method = GET)
	public String getTimeByZone(@RequestParam String zoneIdText) {
		Assert.hasText(zoneIdText, "zoneIdText 값은 필수입니다.");
		return zoneService.getTimeByZone(zoneIdText);
	}
}

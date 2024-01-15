package com.spring.model;

import lombok.Getter;

@Getter
public enum GreetingType {
	ENGLISH("Hello. The time of zone: '%s' is '%s'!"),
	KOREAN("안녕하세요. zone: '%s' 의 시간은... '%s' 입니다!");

	private final String greetingFormat;

	GreetingType(String greetingFormat) {
		this.greetingFormat = greetingFormat;
	}
}

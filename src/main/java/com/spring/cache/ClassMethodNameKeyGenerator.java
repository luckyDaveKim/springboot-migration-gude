package com.spring.cache;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.SimpleKeyGenerator;

public class ClassMethodNameKeyGenerator extends SimpleKeyGenerator {
	public static Object generateKeyByCustom(Object target, Method method, Object... params) {
		Object[] newParams = new Object[params.length + 2];
		newParams[0] = target;
		newParams[1] = method;

		System.arraycopy(params, 0, newParams, 2, params.length);

		return generateKey(newParams);
	}

	@Override
	public Object generate(Object target, Method method, Object... params) {
		return generateKeyByCustom(target, method, params);
	}
}

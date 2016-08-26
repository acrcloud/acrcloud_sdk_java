package com.acrcloud.utils;

import java.io.IOException;

import com.acrcloud.exception.ACRException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * JSON Tools
 * 
 * @author hallwong E-mail: hallwong@163.com
 */
public enum JsonUtil {

	INSTANCE;

	private ObjectMapper mapper = null;

	private JsonUtil() {
		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}

	public static String toJson(Object obj) {
		return toJson(obj, null);
	}

	public static String toJson(Object obj, Class<?> viewClass) {
		if (obj == null) {
			return null;
		}
		try {
			return INSTANCE.mapper.writerWithView(viewClass).writeValueAsString(obj);
		} catch (IOException e) {
			RuntimeException runtimeException = new RuntimeException("toJson error!", e);
			throw runtimeException;
		}
	}

	public static <T> T fromJson(String json, Class<T> targetClass) throws ACRException {
		try {
			return INSTANCE.mapper.readValue(json, targetClass);
		} catch (IOException e) {
			throw new ACRException("parse error!", e);
		}
	}

	public static <C, T> C fromJson(String json, Class<C> collectionType, Class<T> targetClass) throws ACRException {
		try {
			return INSTANCE.mapper.readValue(json,
					INSTANCE.mapper.getTypeFactory().constructParametricType(collectionType, targetClass));
		} catch (IOException e) {
			throw new ACRException("parse error!", e);
		}
	}

}

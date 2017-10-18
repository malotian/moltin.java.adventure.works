package com.moltin.adventure.works;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
public class Tuple extends HashMap<String, Object> {

	public Tuple() {
	}

	public Tuple(Map<String, Object> map) {
		super(map);
	}

	public String getString(String key) {
		if (super.get(key) instanceof String) {
			return (String) super.get(key);
		}
		try {
			return new ObjectMapper().writeValueAsString(super.get(key));
		} catch (final JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public Tuple getTuple(String key) {

		if (super.get(key) instanceof Map) {
			return new Tuple((Map<String, Object>) super.get(key));
		}

		return null;
	}

	public Tuple putX(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
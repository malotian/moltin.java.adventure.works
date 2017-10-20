/**
 */
package com.moltin.adventure.works.app;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Context {

	private static Map<String, Object> context = new ConcurrentHashMap<>();

	public static <T> boolean contains(final Class<T> clazz) {
		return context.containsKey(clazz.getSimpleName());
	}

	public static boolean contains(final String key) {
		return context.containsKey(key);
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(final Class<T> clazz) {
		return (T) context.get(clazz.getSimpleName());
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(final String key) {
		return (T) context.get(key);
	}

	public static <T> Object put(final Class<T> clazz, final T value) {
		if (context.containsKey(clazz.getSimpleName())) {
			return context.put(clazz.getSimpleName(), value);
		}
		return context.put(clazz.getSimpleName(), value);
	}

	public static <T> Object put(final String key, final T value) {
		return context.put(key, value);
	}

	public static <T> void remove(final Class<T> clazz) {
		context.remove(clazz.getSimpleName());
	}

	public static void remove(final String key) {
		context.remove(key);
	}

	private Context() {

	}
}

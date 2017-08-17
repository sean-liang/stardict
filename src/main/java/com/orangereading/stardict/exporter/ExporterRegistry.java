package com.orangereading.stardict.exporter;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.reflections.Reflections;

import com.orangereading.stardict.annotation.Exporter;

public final class ExporterRegistry {

	private final static ConcurrentMap<String, Class<? extends DictionaryExporter>> exporters = new ConcurrentHashMap<>();

	private ExporterRegistry() {

	}

	@SuppressWarnings("unchecked")
	public static void init() {
		final Reflections reflections = new Reflections();
		final Set<Class<?>> exporterClasses = reflections.getTypesAnnotatedWith(Exporter.class);
		exporterClasses.forEach(clazz -> {
			final String key = clazz.getAnnotation(Exporter.class).value();
			exporters.put(key, (Class<? extends DictionaryExporter>) clazz);
		});
	}

	public static Class<? extends DictionaryExporter> getExporter(final String type) {
		return exporters.get(type.toLowerCase());
	}

}

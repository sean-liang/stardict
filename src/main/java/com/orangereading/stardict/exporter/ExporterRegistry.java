package com.orangereading.stardict.exporter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class ExporterRegistry {

	private final static ConcurrentMap<String, Class<? extends DictionaryExporter>> exporters = new ConcurrentHashMap<>();

	private ExporterRegistry() {

	}

	@SuppressWarnings("unchecked")
	public static void init() {
		try (final InputStream in = ExporterRegistry.class.getResourceAsStream("/exporters.properties")) {
			final Properties props = new Properties();
			props.load(in);
			props.forEach((type, exporter) -> {
				try {
					exporters.put(type.toString(), (Class<DictionaryExporter>) Class.forName(exporter.toString()));
				} catch (ClassNotFoundException e) {
					throw new RuntimeException(e);
				}
			});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static Class<? extends DictionaryExporter> getExporter(final String type) {
		return exporters.get(type.toLowerCase());
	}

}

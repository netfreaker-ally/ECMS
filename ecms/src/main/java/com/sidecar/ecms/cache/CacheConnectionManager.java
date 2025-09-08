package com.sidecar.ecms.cache;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.caching.sidecar.grpc.CacheConnectionConfig;
import com.sidecar.ecms.core.auth.mapper.CacheConfigMapper;
import com.sidecar.ecms.core.config.BaseCacheConnectionConfig;
import com.sidecar.ecms.core.connection.CacheConnection;
import com.sidecar.ecms.core.exception.cache.CacheConnectionException;
import com.sidecar.ecms.core.factory.impl.DefaultCacheConnectionFactory;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CacheConnectionManager {
	// haveToStore
	private final Map<String, CacheConnection> connectionPool = new ConcurrentHashMap<>();
	private final DefaultCacheConnectionFactory defaultCacheConnectionFactory;
	private final Logger log = LoggerFactory.getLogger(getClass());

	public CacheConnection getOrCreateConnection(CacheConnectionConfig config, String connectionId)
			throws CacheConnectionException {

		log.info("Request to get/create connection with ID: {}", connectionId);

		if (connectionId != null && connectionPool.containsKey(connectionId)) {
			log.debug("Reusing existing connection for ID: {}", connectionId);
			return connectionPool.get(connectionId); // reuse existing connection
		}

		try {
			// Map proto → internal domain config
			BaseCacheConnectionConfig internalConfig = CacheConfigMapper.toInternal(config);
			log.debug("Mapped external config to internal config: {}", internalConfig);

			// Decide type (Redis/Memcached etc.) and get connection
			CacheConnection connection = defaultCacheConnectionFactory.getConnection(internalConfig);
			log.info("Created new connection of type: {}", connection.getClass().getSimpleName());

			// Connect & authenticate
			connection.connect();
			log.info("Connection established successfully");

			// Store connection with ID
			String id = connectionId != null ? connectionId : UUID.randomUUID().toString();
			connectionPool.put(id, connection);
			log.debug("Connection stored in pool with ID: {}", id);

			return connection;

		} catch (Exception ex) {
			log.error("Failed to create or connect cache connection for ID: {}", connectionId, ex);
			throw new CacheConnectionException("Error creating connection", ex);
		}
	}

	public Optional<CacheConnection> getConnectionById(String connectionId) {
		log.debug("Retrieving connection by ID: {}", connectionId);
		return Optional.ofNullable(connectionPool.get(connectionId));
	}
}
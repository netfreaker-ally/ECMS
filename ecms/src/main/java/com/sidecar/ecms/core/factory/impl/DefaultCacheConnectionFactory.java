package com.sidecar.ecms.core.factory.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.esotericsoftware.minlog.Log;
import com.sidecar.ecms.core.config.BaseCacheConnectionConfig;
import com.sidecar.ecms.core.connection.CacheConnection;
import com.sidecar.ecms.core.connection.RedisConnectionConfig;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DefaultCacheConnectionFactory {

	private final Map<String, CacheConnection> connectionPool = new ConcurrentHashMap<>();

	private final CustomRedisConnectionFactory redisConnectionFactory;

	// If in future: private final MemcachedConnectionFactory
	// memcachedConnectionFactory;

	public CacheConnection getConnection(BaseCacheConnectionConfig config) {

		String key = buildKey(config);
		Log.info("key : "+key);
		return connectionPool.computeIfAbsent(key, k -> {
			if (config instanceof RedisConnectionConfig redisConfig) {
				return redisConnectionFactory.createConnection(redisConfig);
			}
			// extend for memcached, dynamodb, etc.
			throw new IllegalArgumentException("Unsupported cache type: " + config.getClass().getSimpleName());
		});
	}

	private String buildKey(BaseCacheConnectionConfig config) {
		// Build unique key: could be provider + endpoints + ssl flag, etc.
		if (config instanceof RedisConnectionConfig redisConfig) {
			return "REDIS-" + redisConfig.getEndpoints().toString() + "-" + redisConfig.isUseSsl() + "-"
					+ redisConfig.getAuthConfig();
		}
		return config.toString();
	}

}

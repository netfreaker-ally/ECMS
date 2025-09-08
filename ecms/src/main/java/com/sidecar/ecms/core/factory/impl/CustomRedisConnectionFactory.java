package com.sidecar.ecms.core.factory.impl;

import org.springframework.stereotype.Service;

import com.sidecar.ecms.core.connection.CacheConnection;
import com.sidecar.ecms.core.connection.RedisConnectionConfig;
import com.sidecar.ecms.core.connection.impl.RedisClusterConnection;
import com.sidecar.ecms.core.connection.impl.RedisConnection;
import com.sidecar.ecms.core.factory.CacheConnectionFactory;

@Service
public class CustomRedisConnectionFactory implements CacheConnectionFactory<RedisConnectionConfig> {

	@Override
	public CacheConnection createConnection(RedisConnectionConfig config) {
		if (isCluster(config)) {
			return createClusterConnection(config);
		} else {
			return createStandaloneConnection(config);
		}
	}

	private boolean isCluster(RedisConnectionConfig config) {
		return Boolean.parseBoolean(config.getExtraProperties().getOrDefault("isCluster", "false"));
	}

	private CacheConnection createStandaloneConnection(RedisConnectionConfig config) {

		return new RedisConnection(config);
	}

	private CacheConnection createClusterConnection(RedisConnectionConfig config) {

		return new RedisClusterConnection(config);
	}

}

package com.sidecar.ecms.core.connection.impl;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.protobuf.ByteString;
import com.sidecar.ecms.core.connection.IdentifiableConnection;
import com.sidecar.ecms.core.connection.RedisConnectionConfig;
import com.sidecar.ecms.core.exception.cache.CacheConnectionException;
import com.sidecar.ecms.core.exception.cache.CacheOperationException;

import lombok.Data;

@Component
@Data
public class RedisConnection implements IdentifiableConnection {
	private final RedisConnectionConfig config;
	private boolean connected;
	private String connectionId;
	private final Logger log = LoggerFactory.getLogger(getClass());
	// private final Auth
	

	@Override
	public void connect() throws CacheConnectionException {
		// TODO Auto-generated method stub /with ttl and all
//		Endpoint endpoint = config.getEndpoints().get(0);
//		String uri = "redis://" + endpoint.getHost() + ":" + endpoint.getPort();
//		RedisClient client = RedisClient.create(uri);
		log.info("in the redis connection in connect method----");

		// haveToConnect
	}

	@Override
	public void disconnect() throws CacheConnectionException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isConnected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean set(String key, ByteString value, int ttlSeconds, Optional<Instant> absoluteExpiration) {
		// TODO Auto-generated method stub
		log.info("In RedisConnection with key {} value {} ttl {} absoluteExpiration {}", key, value, ttlSeconds,
				absoluteExpiration);
		return false;
	}

	@Override
	public byte[] get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean delete(String key) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean batchSet(Map<String, Object> keyValuePairs) throws CacheOperationException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, Object> batchGet(List<String> keys) throws CacheOperationException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean batchDelete(List<String> keys) throws CacheOperationException {
		// TODO Auto-generated method stub
		return false;
	}

}

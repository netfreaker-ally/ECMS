package com.sidecar.ecms.core.connection.impl;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.google.protobuf.ByteString;
import com.sidecar.ecms.core.connection.CacheConnection;
import com.sidecar.ecms.core.connection.RedisConnectionConfig;
import com.sidecar.ecms.core.exception.cache.CacheConnectionException;
import com.sidecar.ecms.core.exception.cache.CacheOperationException;

import io.lettuce.core.RedisURI;
import io.lettuce.core.cluster.RedisClusterClient;
@Component
public class RedisClusterConnection implements CacheConnection{
	private final RedisConnectionConfig config;
	private boolean connected;

	public RedisClusterConnection(RedisConnectionConfig config) {
		this.config = config;
	}
	@Override
	public void connect() throws CacheConnectionException {
		// TODO Auto-generated method stub
//		List<RedisURI> nodes = config.getEndpoints().stream().map(e -> RedisURI.create(e.getHost(), e.getPort()))
//				.collect(Collectors.toList());
//
//		RedisClusterClient clusterClient = RedisClusterClient.create(nodes);
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
	@Override
	public boolean set(String key, ByteString byteString, int ttlSeconds, Optional<Instant> absoluteExpiration) {
		// TODO Auto-generated method stub
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

}

package com.sidecar.ecms.core.connection;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.google.protobuf.ByteString;
import com.sidecar.ecms.core.exception.cache.CacheConnectionException;
import com.sidecar.ecms.core.exception.cache.CacheOperationException;

public interface CacheConnection {
	
    // Connection lifecycle
    void connect() throws CacheConnectionException;
    void disconnect() throws CacheConnectionException;
    boolean isConnected();

    /**
     * Sets a value in the cache.
     *
     * @param key The cache key.
     * @param byteString The value to store.
     * @param ttlSeconds Time to live in seconds.
     * @param absoluteExpiration Optional absolute expiration timestamp.
     * @return true if the value was successfully set.
     */
    boolean set(String key, ByteString byteString, int ttlSeconds, Optional<Instant> absoluteExpiration);

    byte[] get(String key);

    boolean delete(String key);

    // Batch operations
    boolean batchSet(Map<String, Object> keyValuePairs) throws CacheOperationException;
    Map<String, Object> batchGet(List<String> keys) throws CacheOperationException;
    boolean batchDelete(List<String> keys) throws CacheOperationException;
}

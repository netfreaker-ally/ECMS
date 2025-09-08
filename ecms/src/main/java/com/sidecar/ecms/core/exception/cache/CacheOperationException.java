package com.sidecar.ecms.core.exception.cache;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString(callSuper = true)
public class CacheOperationException extends CacheException {

	private final String host;
	private final int port;

	/**
	 * Constructor with default error code
	 */
	public CacheOperationException(String message) {
		super("CACHE_CONNECTION_ERROR", message);
		this.host = null;
		this.port = -1;
	}

	/**
	 * Constructor with host and port info
	 */
	public CacheOperationException(String message, String host, int port) {
		super("CACHE_CONNECTION_ERROR", message);
		this.host = host;
		this.port = port;
	}

	/**
	 * Constructor with cause
	 */
	public CacheOperationException(String message, Throwable cause) {
		super("CACHE_CONNECTION_ERROR", message, cause);
		this.host = null;
		this.port = -1;
	}

	/**
	 * Full constructor with host, port, and cause
	 */
	public CacheOperationException(String message, String host, int port, Throwable cause) {
		super("CACHE_CONNECTION_ERROR", message, cause);
		this.host = host;
		this.port = port;
	}
}

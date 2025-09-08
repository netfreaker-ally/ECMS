package com.sidecar.ecms.core.exception.cache;

import lombok.Getter;
import lombok.ToString;

/**
 * Custom exception for cache-related errors.
 * Immutable and Lombok-friendly.
 */
@Getter
@ToString(callSuper = true)
public class CacheException extends Exception {

    private final String errorCode;
    private final String details;

    /**
     * Constructor with errorCode and details.
     * @param errorCode custom error code for identifying the error
     * @param details detailed message about the error
     */
    public CacheException(String errorCode, String details) {
        super(details); // sets the Exception message
        this.errorCode = errorCode;
        this.details = details;
    }

    /**
     * Convenience constructor for default error code.
     * @param details detailed message about the error
     */
    
    public CacheException(String details) {
        this("CACHE_ERROR", details);
    }

	public CacheException(String errorCode, String details ,Throwable tx) {
		super(tx);
		this.errorCode = errorCode;
		this.details = details;
	}
}


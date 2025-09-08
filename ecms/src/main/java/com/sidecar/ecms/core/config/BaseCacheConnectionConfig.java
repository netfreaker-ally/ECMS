package com.sidecar.ecms.core.config;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.caching.sidecar.grpc.AuthConfig;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Component
public  class BaseCacheConnectionConfig {
	private AuthConfig authConfig;
	private boolean useSsl;
	private String version;
	private Map<String, String> extraProperties;
	public BaseCacheConnectionConfig(AuthConfig authConfig, boolean useSsl, String version,
			Map<String, String> extraProperties) {
		super();
		this.authConfig = authConfig;
		this.useSsl = useSsl;
		this.version = version;
		this.extraProperties = extraProperties;
	}
	
}

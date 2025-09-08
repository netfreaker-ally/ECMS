package com.sidecar.ecms.core.connection;

import java.util.List;

import org.springframework.stereotype.Component;

import com.caching.sidecar.grpc.Endpoint;
import com.sidecar.ecms.core.config.BaseCacheConnectionConfig;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Component
@EqualsAndHashCode(callSuper = true)
public class RedisConnectionConfig extends BaseCacheConnectionConfig {
	private List<Endpoint> endpoints;

	 public RedisConnectionConfig(BaseCacheConnectionConfig base, List<Endpoint> endpoints) {
	        super(base.getAuthConfig(), base.isUseSsl(), base.getVersion(), base.getExtraProperties());
	        this.endpoints = endpoints;
	    }
}

///////
package com.sidecar.ecms.core.auth.mapper;

import com.caching.sidecar.grpc.CacheConnectionConfig;
import com.sidecar.ecms.core.config.BaseCacheConnectionConfig;
import com.sidecar.ecms.core.connection.RedisConnectionConfig;

public class GrpcToBaseCacheConnectionConfigMapper {
	public static RedisConnectionConfig toRedisConfig(CacheConnectionConfig protoConfig) {
        BaseCacheConnectionConfig base = new BaseCacheConnectionConfig(
            protoConfig.getAuthConfig(),
            protoConfig.getUseSsl(),
            protoConfig.getVersion(),
            protoConfig.getExtraPropertiesMap()
        );

        return new RedisConnectionConfig(base, protoConfig.getEndpointsList());
    }
}

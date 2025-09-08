package com.sidecar.ecms.core.auth.mapper;

import org.springframework.stereotype.Component;

import com.caching.sidecar.grpc.CacheConnectionConfig;
import com.sidecar.ecms.core.config.BaseCacheConnectionConfig;
import com.sidecar.ecms.core.connection.RedisConnectionConfig;

import lombok.Data;

@Component
public class CacheConfigMapper {

    public static BaseCacheConnectionConfig toInternal(CacheConnectionConfig protoConfig) {
        switch (protoConfig.getCacheType()) {
            case REDIS:
                return toRedisConfig(protoConfig);
//            case MEMCACHED:
//                return toMemcachedConfig(protoConfig);
            default:
                throw new UnsupportedOperationException(
                    "Unsupported cache type: " + protoConfig.getCacheType()
                );
        }
    }

    private static RedisConnectionConfig toRedisConfig(CacheConnectionConfig protoConfig) {
    	BaseCacheConnectionConfig base = new BaseCacheConnectionConfig(
                protoConfig.getAuthConfig(),
                protoConfig.getUseSsl(),
                protoConfig.getVersion(),
                protoConfig.getExtraPropertiesMap()
            );

            return new RedisConnectionConfig(base, protoConfig.getEndpointsList());
    }

//    private MemcachedConnectionConfig toMemcachedConfig(CacheConnectionConfig protoConfig) {
//        return new MemcachedConnectionConfig(
//            protoConfig.getAuthConfig(),
//            protoConfig.getUseSsl(),
//            protoConfig.getVersion(),
//            protoConfig.getExtraPropertiesMap(),
//            protoConfig.getEndpointsList()
//        );
//    }
}

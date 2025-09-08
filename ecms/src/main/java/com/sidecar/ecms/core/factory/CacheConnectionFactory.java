package com.sidecar.ecms.core.factory;

import com.sidecar.ecms.core.config.BaseCacheConnectionConfig;
import com.sidecar.ecms.core.connection.CacheConnection;

public interface CacheConnectionFactory <T extends BaseCacheConnectionConfig>{
    CacheConnection createConnection( T config);
}


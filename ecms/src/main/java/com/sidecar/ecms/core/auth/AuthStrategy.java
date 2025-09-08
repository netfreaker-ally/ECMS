package com.sidecar.ecms.core.auth;

import com.sidecar.ecms.core.connection.CacheConnection;

public interface AuthStrategy {
    void authenticate(CacheConnection connection);
}

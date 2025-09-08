package com.sidecar.ecms.core.auth.impl;

import org.springframework.stereotype.Component;

import com.sidecar.ecms.core.auth.AuthStrategy;
import com.sidecar.ecms.core.connection.CacheConnection;
@Component
public class NoOpAuthStrategy implements AuthStrategy {
    @Override
    public void authenticate(CacheConnection connection) {
        // do nothing
    }
}

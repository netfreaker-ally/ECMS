package com.sidecar.ecms.core.connection;
public interface IdentifiableConnection extends CacheConnection {
    String getConnectionId();
}

package com.sidecar.ecms.core.auth.impl;

import org.springframework.stereotype.Component;

import com.sidecar.ecms.core.auth.AuthStrategy;
import com.sidecar.ecms.core.config.UserNamePasswordAuthProperties;
import com.sidecar.ecms.core.connection.CacheConnection;

import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class UsernamePasswordAuthStrategy implements AuthStrategy {
   
	private UserNamePasswordAuthProperties authProperties;

   

    @Override
    public void authenticate(CacheConnection connection) {
        // perform login / auth for the cache
        System.out.println("Authenticating with username: " + authProperties.getUsername());
    }
}


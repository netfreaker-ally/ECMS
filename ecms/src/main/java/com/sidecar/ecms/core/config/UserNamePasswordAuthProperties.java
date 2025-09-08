/**
 * 
 */
package com.sidecar.ecms.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 
 * @author hanuma.ramavath
 * @category properties file
 * 
 */
@Data
@Component
@ConfigurationProperties(prefix = "cache.auth.usernamepasswordauth")
public class UserNamePasswordAuthProperties {
	private String username;
	private String password;
}

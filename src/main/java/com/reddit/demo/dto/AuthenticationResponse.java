package com.reddit.demo.dto;

import java.time.Instant;

public class AuthenticationResponse {

	private String authenticationToken;
	private String username;
	private String refreshToken;
	private Instant expiresAt;

	public AuthenticationResponse() {
		super();
	}

	public AuthenticationResponse(String authenticationToken, String username) {
		super();
		this.authenticationToken = authenticationToken;
		this.username = username;
	}

	public AuthenticationResponse(String authenticationToken, String username, Instant expiresAt) {
		this(authenticationToken, username, expiresAt, "");
	}

	public AuthenticationResponse(String authenticationToken, String username, Instant expiresAt, String refreshToken) {
		this(authenticationToken, username);
		this.refreshToken = refreshToken;
		this.expiresAt = expiresAt;
	}

	public String getAuthenticationToken() {
		return authenticationToken;
	}

	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Instant getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Instant expiresAt) {
		this.expiresAt = expiresAt;
	}

}

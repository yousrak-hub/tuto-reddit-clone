package com.reddit.demo.dto;

public class RefreshTokenRequest {

	private String refreshToken;
	private String username;

	public RefreshTokenRequest(String refreshToken, String username) {
		super();
		this.refreshToken = refreshToken;
		this.username = username;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}

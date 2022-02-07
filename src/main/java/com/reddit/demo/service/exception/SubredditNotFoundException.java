package com.reddit.demo.service.exception;

public class SubredditNotFoundException extends RuntimeException {
	public SubredditNotFoundException(String message) {
		super(message);
	}
}

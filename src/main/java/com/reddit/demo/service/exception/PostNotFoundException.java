package com.reddit.demo.service.exception;

public class PostNotFoundException extends RuntimeException {
	public PostNotFoundException(String message) {
		super(message);
	}
}

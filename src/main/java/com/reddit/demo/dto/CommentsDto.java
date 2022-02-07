package com.reddit.demo.dto;

import java.time.Instant;

public class CommentsDto {
	private Long id;
	private Long postId;
	private Instant createdDate;
	private String text;
	private String userName;

	public CommentsDto() {
		super();
	}

	public CommentsDto(Long id, Long postId, String text) {
		super();
		this.id = id;
		this.postId = postId;
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public Instant getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Instant createdDate) {
		this.createdDate = createdDate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}

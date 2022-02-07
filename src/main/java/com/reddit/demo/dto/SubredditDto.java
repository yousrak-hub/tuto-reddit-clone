package com.reddit.demo.dto;

public class SubredditDto {
	private Long id;
	private String name;
	private String description;
	private Integer numberOfPosts;

	public SubredditDto() {
		super();
	}

	public SubredditDto(Long id, String name, String description) {
		this(id, name, description, null);
	}

	public SubredditDto(Long id, String name, String description, Integer numberOfPosts) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.numberOfPosts = numberOfPosts;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getNumberOfPosts() {
		return numberOfPosts;
	}

	public void setNumberOfPosts(Integer numberOfPosts) {
		this.numberOfPosts = numberOfPosts;
	}

}

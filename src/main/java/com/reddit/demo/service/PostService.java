package com.reddit.demo.service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.reddit.demo.dto.PostRequest;
import com.reddit.demo.dto.PostResponse;
import com.reddit.demo.model.Post;
import com.reddit.demo.model.Subreddit;
import com.reddit.demo.model.User;
import com.reddit.demo.repository.PostRepository;
import com.reddit.demo.repository.SubredditRepository;
import com.reddit.demo.repository.UserRepository;
import com.reddit.demo.service.exception.PostNotFoundException;
import com.reddit.demo.service.exception.SubredditNotFoundException;

@Service
public class PostService {
	@Autowired
	private SubredditRepository subredditRepository;
	@Autowired
	private AuthService authService;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserRepository userRepository;

	public PostResponse save(PostRequest postRequest) {
		Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName())
				.orElseThrow(() -> new SubredditNotFoundException(postRequest.getSubredditName()));
		User currentUser = authService.getCurrentUser();
		Post post = postRepository.save(mapToPost(postRequest, subreddit, currentUser));
		return mapToDto(post);
	}

	public PostResponse getPost(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id.toString()));
		return mapToDto(post);
	}

	public List<PostResponse> getAllPosts() {
		return postRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
	}

	public List<PostResponse> getPostsBySubreddit(Long subredditid) {
		Subreddit subreddit = subredditRepository.findById(subredditid)
				.orElseThrow(() -> new SubredditNotFoundException(subredditid.toString()));
		return postRepository.findAllBySubreddit(subreddit).stream().map(this::mapToDto).collect(Collectors.toList());
	}

	public List<PostResponse> getPostsByUsername(String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("No user " + "Found with username : " + username));
		return postRepository.findByUser(user).stream().map(this::mapToDto).collect(Collectors.toList());
	}

	private Post mapToPost(PostRequest postRequest, Subreddit subreddit, User user) {
		Post post = new Post(postRequest.getPostName(), postRequest.getDescription(), postRequest.getUrl());
		post.setUser(user);
		post.setSubreddit(subreddit);
		post.setCreatedDate(Instant.now());
		return post;
	}

	private PostResponse mapToDto(Post post) {
		PostResponse postResponse = new PostResponse(post.getPostId(), post.getPostName(), post.getUrl(),
				post.getDescription());
		postResponse.setSubredditName(post.getSubreddit().getName());
		postResponse.setUserName(post.getUser().getUsername());
		postResponse.setVoteCount(post.getVoteCount() != null ? post.getVoteCount() : 0);
		postResponse.setDuration(post.getCreatedDate().toString());
		return postResponse;
	}
}

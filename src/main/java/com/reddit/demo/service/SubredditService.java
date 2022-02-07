package com.reddit.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reddit.demo.dto.SubredditDto;
import com.reddit.demo.model.Subreddit;
import com.reddit.demo.repository.SubredditRepository;
import com.reddit.demo.service.exception.SpringRedditException;

@Service
public class SubredditService {
	@Autowired
	private SubredditRepository subredditRepository;

	public SubredditDto save(SubredditDto dto) {
		Subreddit subreddit = subredditRepository.save(mapSubredditDto(dto));
		dto.setId(subreddit.getId());
		return dto;
	}

	private Subreddit mapSubredditDto(SubredditDto dto) {
		Subreddit subreddit = Subreddit.build();
		subreddit.setName(dto.getName());
		subreddit.setDescription(dto.getDescription());
		return subreddit;
	}

	public List<SubredditDto> getAll() {
		return subredditRepository.findAll().stream().map(this::mapToDto).collect(Collectors.toList());
	}

	private SubredditDto mapToDto(Subreddit subreddit) {
		return new SubredditDto(subreddit.getId(), subreddit.getName(), subreddit.getDescription());
	}
	public SubredditDto getSubreddit(Long id) {
		Subreddit subreddit = subredditRepository.findById(id).orElseThrow(()->new SpringRedditException("No subreddit found with id "+id));
		return mapToDto(subreddit);
	}
}

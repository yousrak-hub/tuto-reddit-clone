package com.reddit.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reddit.demo.dto.VoteDto;
import com.reddit.demo.model.Post;
import com.reddit.demo.model.Vote;
import com.reddit.demo.model.VoteType;
import com.reddit.demo.repository.PostRepository;
import com.reddit.demo.repository.VoteRepository;
import com.reddit.demo.service.exception.PostNotFoundException;
import com.reddit.demo.service.exception.SpringRedditException;

@Service
public class VoteService {

	@Autowired
	private PostRepository postRepository;
	@Autowired
	private VoteRepository voteRepository;
	@Autowired
	private AuthService authService;

	public void vote(VoteDto voteDto) {
		Long postId = voteDto.getPostId();
		Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
		Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
				authService.getCurrentUser());
		if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType())) {
			throw new SpringRedditException("You have already " + voteDto.getVoteType());
		}
		Integer voteCount = post.getVoteCount() != null ? post.getVoteCount() : 0;
		if (VoteType.UPVOTE.equals(voteDto.getVoteType())) {
			post.setVoteCount(voteCount + 1);
		} else {
			post.setVoteCount(voteCount - 1);
		}
		voteRepository.save(mapToVote(voteDto, post));
		postRepository.save(post);
	}

	private Vote mapToVote(VoteDto voteDto, Post post) {
		return new Vote(voteDto.getVoteType(), post, authService.getCurrentUser());
	}
}

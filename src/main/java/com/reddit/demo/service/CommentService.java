package com.reddit.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.reddit.demo.dto.CommentsDto;
import com.reddit.demo.model.Comment;
import com.reddit.demo.model.NotificationEmail;
import com.reddit.demo.model.Post;
import com.reddit.demo.model.User;
import com.reddit.demo.repository.CommentRepository;
import com.reddit.demo.repository.PostRepository;
import com.reddit.demo.repository.UserRepository;
import com.reddit.demo.service.exception.PostNotFoundException;

@Service
public class CommentService {
	private static final String POST_URL = "";
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AuthService authService;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private MailService mailService;

	public void save(CommentsDto commentsDto) {
		Post post = findPostById(commentsDto.getPostId());
		commentRepository.save(mapToComment(commentsDto, post, authService.getCurrentUser()));

		String message = post.getUser().getUsername() + " posted a comment on your post." + POST_URL;
		sendCommentNotification(message, post.getUser());
	}

	public List<CommentsDto> getAllCommentsForPost(Long postId) {
		List<Comment> comments = commentRepository.findByPost(findPostById(postId));
		return comments.stream().map(this::mapToDto).collect(Collectors.toList());
	}

	public List<CommentsDto> getAllCommentsForUser(String userName) {
		User user = userRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException("No user " +
                "Found with username : " + userName));
		return commentRepository.findAllByUser(user).stream().map(this::mapToDto).collect(Collectors.toList());
	}
	private void sendCommentNotification(String message, User user) {
		mailService.sendMail(
				new NotificationEmail(user.getUsername() + " Commented on your post", user.getEmail(), message));
	}

	private Post findPostById(Long postId) {
		return postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId.toString()));
	}

	private Comment mapToComment(CommentsDto commentsDto, Post post, User user) {
		return new Comment(commentsDto.getText(), post, user);
	}

	private CommentsDto mapToDto(Comment comment) {
		CommentsDto commentsDto = new CommentsDto(comment.getId(),comment.getPost().getPostId(),comment.getText());
		commentsDto.setCreatedDate(comment.getCreatedDate());
		commentsDto.setUserName(comment.getUser().getUsername());
		return commentsDto;
	}
}

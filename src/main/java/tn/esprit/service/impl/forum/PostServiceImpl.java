package tn.esprit.service.impl.forum;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tn.esprit.exception.ResourceNotFoundException;
import tn.esprit.model.forum.Comment;
import tn.esprit.model.forum.Post;
import tn.esprit.repository.forum.CommentRepository;
import tn.esprit.repository.forum.PostRepository;
import tn.esprit.service.forum.PostService;

/**
 * 
 * @author Ghassen Bargougui
 *
 */
@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final CommentRepository commentRepository;

	@Override
	public Post addPost(Post post) {
		return postRepository.save(post);
	}

	@Override
	public Post getPostById(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "id", id));
		return post;
	}

	@Override
	public Post updatePost(Post updatedPost, Long id) {
		getPostById(id);
		updatedPost.setId(id);
		return postRepository.save(updatedPost);
	}

	@Override
	public void deletePost(Long id) {
		Post post = getPostById(id);
		postRepository.delete(post);
	}

	@Override
	public void addCommentToPost(Comment comment, Long postId) {
		Post post = getPostById(postId);
		comment.setPost(post);
		if (post.getComments() == null) {
			post.setComments(new ArrayList<>());
		}
		post.getComments().add(comment);
		postRepository.save(post);
	}

	@Override
	public List<Comment> getCommentsByPost(Long postId) {
		return commentRepository.findByPostId(postId);
	}

}

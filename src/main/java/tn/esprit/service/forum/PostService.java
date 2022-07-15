package tn.esprit.service.forum;

import java.util.List;

import tn.esprit.model.forum.Comment;
import tn.esprit.model.forum.Post;

/**
 * 
 * @author Ghassen Bargougui
 *
 */
public interface PostService {

	Post addPost(Post post);

	Post getPostById(Long id);

	Post updatePost(Post updatedPost, Long id);

	void deletePost(Long id);

	void addCommentToPost(Comment comment, Long postId);

	List<Comment> getCommentsByPost(Long postId);

}

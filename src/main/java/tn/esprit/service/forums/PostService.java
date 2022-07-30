package tn.esprit.service.forums;

import java.util.List;

import tn.esprit.model.forums.Comment;
import tn.esprit.model.forums.Post;

/**
 * 
 * @author Mohamed Marzougui
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

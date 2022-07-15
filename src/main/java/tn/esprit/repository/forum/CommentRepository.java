package tn.esprit.repository.forum;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.esprit.model.forum.Comment;

/**
 * 
 * @author Ghassen Bargougui
 *
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query(value = "SELECT c FROM Comment c where c.post.id = ?1")
	List<Comment> findByPostId(Long postId);

}

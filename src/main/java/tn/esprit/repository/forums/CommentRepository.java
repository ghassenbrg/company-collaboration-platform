package tn.esprit.repository.forums;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.esprit.model.forums.Comment;

/**
 * 
 * @author Mohamed Marzougui
 *
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

	@Query(value = "SELECT c FROM Comment c where c.post.id = ?1")
	List<Comment> findByPostId(Long postId);
	
	@Query(value = "SELECT count(*) from comments where user_id= :userId ", nativeQuery = true)
	long countComments(Long userId);

}

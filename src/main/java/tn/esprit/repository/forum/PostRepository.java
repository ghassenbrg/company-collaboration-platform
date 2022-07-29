package tn.esprit.repository.forum;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.esprit.model.forum.Post;

/**
 * 
 * @author Ghassen Bargougui
 *
 */
public interface PostRepository extends JpaRepository<Post, Long> {

	@Query(value = "SELECT count(*) from post where user_id= :userId ", nativeQuery = true)
	long countPosts(Long userId);
}

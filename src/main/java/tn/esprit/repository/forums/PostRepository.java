package tn.esprit.repository.forums;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.esprit.model.forums.Post;

/**
 * 
 * @author Mohamed Marzougui
 *
 */
public interface PostRepository extends JpaRepository<Post, Long> {

	@Query(value = "SELECT count(*) from post where user_id= :userId ", nativeQuery = true)
	long countPosts(Long userId);
}

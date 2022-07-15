package tn.esprit.repository.forum;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.model.forum.Post;

/**
 * 
 * @author Ghassen Bargougui
 *
 */
public interface PostRepository extends JpaRepository<Post, Long> {

}

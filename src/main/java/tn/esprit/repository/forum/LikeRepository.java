package tn.esprit.repository.forum;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.model.forum.Like;

/**
 * 
 * @author Ghassen Bargougui
 *
 */
public interface LikeRepository extends JpaRepository<Like, Long> {

}

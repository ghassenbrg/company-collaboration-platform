package tn.esprit.repository.forums;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.model.forums.Like;

/**
 * 
 * @author Mohamed Marzougui
 *
 */
public interface LikeRepository extends JpaRepository<Like, Long> {

}

package tn.esprit.repository.forum;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.model.forum.Tag;

/**
 * 
 * @author Ghassen Bargougui
 *
 */
public interface TagRepository extends JpaRepository<Tag, Long> {

}

package tn.esprit.repository.forums;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.model.forums.Tag;

/**
 * 
 * @author Mohamed Marzougui
 *
 */
public interface TagRepository extends JpaRepository<Tag, Long> {

}

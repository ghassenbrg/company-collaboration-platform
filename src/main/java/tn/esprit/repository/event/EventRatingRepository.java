package tn.esprit.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.model.event.Rating;

/**
 * 
 * @author Marwen Lahmar
 *
 */

@Repository
public interface EventRatingRepository extends JpaRepository<Rating, Long> {

}

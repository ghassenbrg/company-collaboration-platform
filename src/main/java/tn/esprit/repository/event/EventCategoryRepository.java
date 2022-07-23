package tn.esprit.repository.event;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.model.event.Category;

/**
 * 
 * @author Marwen Lahmar
 *
 */

@Repository
public interface EventCategoryRepository extends JpaRepository<Category, Long> {

}

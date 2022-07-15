package tn.esprit.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.model.event.Event;

/**
 * 
 * @author Marwen Lahmar
 *
 */

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

}

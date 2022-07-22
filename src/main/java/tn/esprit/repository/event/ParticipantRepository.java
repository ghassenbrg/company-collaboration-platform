package tn.esprit.repository.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.model.event.Participant;

/**
 * 
 * @author Marwen Lahmar
 *
 */

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

}

package tn.esprit.repository.partner;

/**
 * 
 * @author Mazen Aissa
 *
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.model.partner.Reservation;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

}

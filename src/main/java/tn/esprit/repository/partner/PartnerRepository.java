package tn.esprit.repository.partner;

/**
 * 
 * @author Mazen Aissa
 *
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.model.partner.Partner;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {

}

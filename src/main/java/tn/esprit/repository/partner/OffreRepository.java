package tn.esprit.repository.partner;

import java.util.List;

/**
 * 
 * @author Mazen Aissa
 *
 */
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tn.esprit.model.partner.Offre;

@Repository
public interface OffreRepository extends JpaRepository<Offre, Long> {

	@Query(value = "SELECT o FROM Offre o where o.partner.id = ?1")
	List<Offre> findByPartnerId(Long idPartner);

}

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

import tn.esprit.model.partner.Collaboration;

@Repository
public interface CollaborationRepository extends JpaRepository<Collaboration, Long> {

	@Query(value = "SELECT c FROM Collaboration c where c.partner.id = ?1")
	List<Collaboration> findByPartnerId(Long idPartner);
}

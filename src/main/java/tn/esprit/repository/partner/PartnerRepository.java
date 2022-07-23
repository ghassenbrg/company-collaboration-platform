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

import tn.esprit.model.partner.Partner;
import tn.esprit.payload.dto.PartnerProjection;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {

	List<Partner> findByCompanyNameContaining(String name);

	@Query(value = "SELECT p.company_name as CompanyName, ROUND(avg(rating),2) as averageRating FROM partner_rating prt "
			+ "inner join partners p " + "on prt.partner_id = p.id " + "group by partner_id "
			+ "order by ROUND(avg(rating),2) desc limit ?1", nativeQuery = true)
	List<PartnerProjection> findTopPartners(int number);
}

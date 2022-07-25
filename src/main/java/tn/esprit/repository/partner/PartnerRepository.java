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
import tn.esprit.payload.TopPartner;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {

	List<Partner> findByNameContaining(String name);

	@Query(value = "SELECT p.name as Name,p.logo as Logo, ROUND(avg(rating),2) as averageRating FROM partner_rating prt "
			+ "inner join partners p " + "on prt.partner_id = p.id where "
			+ "prt.last_modified_date BETWEEN (sysdate() - INTERVAL 1 MONTH) AND sysdate() " + "group by partner_id "
			+ "order by averageRating desc limit ?1", nativeQuery = true)
	List<TopPartner> findTopPartnersOfPastMonth(int number);
}

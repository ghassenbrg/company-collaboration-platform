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

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {

	List<Partner> findByCompanyNameContaining(String name);

	@Query(value = "SELECT p.id,p.created_by,p.created_date,p.last_modified_by,p.last_modified_date,p.version,p.company_name, avg(rating) as average_rating FROM partner_rating prt "
			+ "inner join partners p " + "on prt.partner_id = p.id " + "group by partner_id "
			+ "order by avg(rating) desc limit ?1", nativeQuery = true)
	List<Partner> findTopPartners(int number);
}

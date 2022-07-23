package tn.esprit.service.partner;

import java.util.List;
import java.util.Optional;

import tn.esprit.model.partner.Partner;
import tn.esprit.payload.dto.PartnerProjection;

/**
 * 
 * @author Mazen Aissa
 *
 */
public interface PartnerService {

	Optional<Partner> findById(Long partnerId);

	List<Partner> getAllPartners();

	List<PartnerProjection> findTopPartners(int number);

	List<Partner> getAllPartnersByName(String name);

	Partner createPartner(Partner partner);

	void updatePartner(Long idPartner, Partner partner);

	void deletePartner(Partner partner);
}

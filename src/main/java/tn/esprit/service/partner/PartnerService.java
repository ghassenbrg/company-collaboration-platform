package tn.esprit.service.partner;

import java.util.Optional;

import tn.esprit.model.partner.Partner;

/**
 * 
 * @author Mazen Aissa
 *
 */
public interface PartnerService {
	
	Optional<Partner> findById(Long partnerId);

}

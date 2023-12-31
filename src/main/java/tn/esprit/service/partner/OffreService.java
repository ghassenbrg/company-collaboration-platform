package tn.esprit.service.partner;

import java.util.List;
import java.util.Optional;

import tn.esprit.model.partner.Offre;

/**
 * 
 * @author Mazen Aissa
 *
 */
public interface OffreService {

	Optional<Offre> findById(Long id);
	
	List<Offre> getAllOffres();

	List<Offre> getPartnerOffres(Long idPartner);

	Offre createOffre(Offre offre);

	void updateOffre(Long idOffre, Offre offre);

	void deleteOffre(Offre offre);

}

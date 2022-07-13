package tn.esprit.service.partner;

import java.util.List;
import java.util.Optional;

import tn.esprit.model.partner.Collaboration;

/**
 * 
 * @author Mazen Aissa
 *
 */
public interface CollaborationService {
	
	Optional<Collaboration> findById(Long id);
	
	List<Collaboration> getAllCollaborations();

	List<Collaboration> getPartnerCollaborations(Long idPartner);

	Collaboration createCollaboration(Collaboration collaboration);

	void updateCollaboration(Long idCollaboration, Collaboration collaboration);

	void deleteCollaboration(Collaboration collaboration);

}

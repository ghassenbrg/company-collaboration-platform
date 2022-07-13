package tn.esprit.service.impl.partner;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import tn.esprit.model.partner.Collaboration;
import tn.esprit.repository.partner.CollaborationRepository;
import tn.esprit.service.partner.CollaborationService;

@RequiredArgsConstructor
@Service
public class CollaborationServiceImpl implements CollaborationService {

	private final CollaborationRepository collaborationRepository;

	@Override
	public List<Collaboration> getAllCollaborations() {
		return collaborationRepository.findAll();
	}

	@Override
	public List<Collaboration> getPartnerCollaborations(Long idPartner) {
		return collaborationRepository.findByPartnerId(idPartner);
	}

	@Override
	public Collaboration createCollaboration(Collaboration collaboration) {
		return collaborationRepository.save(collaboration);
	}

	@Override
	public void updateCollaboration(Long idCollaboration, Collaboration collaboration) {
		collaborationRepository.save(collaboration);
	}

	@Override
	public void deleteCollaboration(Collaboration collaboration) {
		collaborationRepository.delete(collaboration);
	}

	@Override
	public Optional<Collaboration> findById(Long id) {
		return Optional.ofNullable(collaborationRepository.findById(id).orElse(null));
	}

}

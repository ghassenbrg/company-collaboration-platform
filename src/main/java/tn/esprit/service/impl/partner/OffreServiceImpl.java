package tn.esprit.service.impl.partner;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import tn.esprit.model.partner.Offre;
import tn.esprit.repository.partner.OffreRepository;
import tn.esprit.service.partner.OffreService;

@RequiredArgsConstructor
@Service
public class OffreServiceImpl implements OffreService {

	private final OffreRepository offreRepository;

	@Override
	public List<Offre> getAllOffres() {
		return offreRepository.findAll();
	}

	@Override
	public List<Offre> getPartnerOffres(Long idPartner) {
		return offreRepository.findByPartnerId(idPartner);
	}

	@Override
	public Offre createOffre(Offre offre) {
		return offreRepository.save(offre);
	}

	@Override
	public void updateOffre(Long idOffre, Offre offre) {
		offreRepository.save(offre);
	}

	@Override
	public void deleteOffre(Offre offre) {
		offreRepository.delete(offre);
	}

	@Override
	public Optional<Offre> findById(Long id) {
		return Optional.ofNullable(offreRepository.findById(id).orElse(null));
	}

}

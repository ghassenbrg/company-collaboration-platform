package tn.esprit.service.impl.partner;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tn.esprit.model.partner.Partner;
import tn.esprit.repository.partner.PartnerRepository;
import tn.esprit.service.partner.PartnerService;

@RequiredArgsConstructor
@Service
public class PartnerServiceImpl implements PartnerService {

	private final PartnerRepository partnerRepository;

	@Override
	public Optional<Partner> findById(Long partnerId) {
		return Optional.ofNullable(partnerRepository.findById(partnerId).orElse(null));
	}

	@Override
	public List<Partner> getAllPartners() {
		return partnerRepository.findAll();
	}

	@Override
	public Partner createPartner(Partner partner) {
		return partnerRepository.save(partner);
	}

	@Override
	public void updatePartner(Long idPartner, Partner partner) {
		partnerRepository.save(partner);
	}

	@Override
	public void deletePartner(Partner partner) {
		partnerRepository.delete(partner);
	}

	@Override
	public List<Partner> getAllPartnersByName(String name) {
		return partnerRepository.findByCompanyNameContaining(name);
	}

}

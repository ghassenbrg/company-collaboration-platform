package tn.esprit.service.impl.partner;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import tn.esprit.model.partner.PartnerRating;
import tn.esprit.repository.partner.PartnerRatingRepository;
import tn.esprit.service.partner.PartnerRatingService;

/**
 * 
 * @author Mazen Aissa
 *
 */
@RequiredArgsConstructor
@Service
public class PartnerRatingServiceImpl implements PartnerRatingService {

	private final PartnerRatingRepository partnerRatingRepository;

	@Override
	public PartnerRating ratePartner(PartnerRating partnerRating) {
		return partnerRatingRepository.save(partnerRating);
	}

}

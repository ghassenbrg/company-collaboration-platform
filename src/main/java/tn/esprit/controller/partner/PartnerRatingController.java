package tn.esprit.controller.partner;

import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tn.esprit.model.partner.Partner;
import tn.esprit.model.partner.PartnerRating;
import tn.esprit.model.user.User;
import tn.esprit.payload.ApiResponse;
import tn.esprit.payload.dto.PartnerRatingDTO;
import tn.esprit.security.CurrentUser;
import tn.esprit.security.UserPrincipal;
import tn.esprit.service.partner.PartnerRatingService;
import tn.esprit.service.partner.PartnerService;
import tn.esprit.service.user.UserService;

/**
 * 
 * @author Mazen Aissa
 *
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/partnerRatings")
public class PartnerRatingController {

	private final ModelMapper modelMapper;
	private final PartnerService partnerService;
	private final UserService userService;
	private final PartnerRatingService partnerRatingService;
	
	@PostMapping("/rate")
	public ResponseEntity<ApiResponse> ratePartner(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody PartnerRatingDTO partnerRatingDTO) {
		Optional<Partner> partner = partnerService.findById(partnerRatingDTO.getPartnerId());
		if(!partner.isPresent()) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "The partner with the id: "+partnerRatingDTO.getPartnerId()+" could not be found!"),HttpStatus.BAD_REQUEST);
		}
		Optional<User> user = Optional.ofNullable(userService.getCurrentUserEntity(currentUser));
		if(!user.isPresent()) {
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "The user with the id: "+currentUser.getId()+" could not be found!"),HttpStatus.BAD_REQUEST);
		}
		PartnerRating entity = this.convertToEntity(partnerRatingDTO);
		entity.setPartner(partner.get());
		entity.setUser(user.get());
		this.convertToDto(partnerRatingService.ratePartner(entity));
		return new ResponseEntity<>(new ApiResponse(true,"Partner rated with success!"),HttpStatus.CREATED);
	}
	
	private PartnerRatingDTO convertToDto(PartnerRating partnerRating) {
		PartnerRatingDTO partnerRatingDTO = modelMapper.map(partnerRating, PartnerRatingDTO.class);
	    return partnerRatingDTO;
	}
	
	private PartnerRating convertToEntity(PartnerRatingDTO partnerRatingDTO) {
		PartnerRating partnerRating = modelMapper.map(partnerRatingDTO, PartnerRating.class);
	    return partnerRating;
	}
}

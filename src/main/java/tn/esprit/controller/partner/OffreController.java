package tn.esprit.controller.partner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import tn.esprit.model.partner.Offre;
import tn.esprit.model.partner.Partner;
import tn.esprit.payload.ApiResponse;
import tn.esprit.payload.dto.OffreDTO;
import tn.esprit.service.partner.OffreService;
import tn.esprit.service.partner.PartnerService;

/**
 * 
 * @author Mazen Aissa
 *
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/offres")
public class OffreController {

	private final OffreService offreService;
	private final PartnerService partnerService;
    private final ModelMapper modelMapper;
    
	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> createOffre(@Valid @RequestBody OffreDTO offreDTO) {
		Optional<Partner> partner = partnerService.findById(offreDTO.getPartnerId());
		if(!partner.isPresent()) {
			return new ResponseEntity<>(new ApiResponse(false, "The partner with the id: "+offreDTO.getPartnerId()+" could not be found!"),HttpStatus.BAD_REQUEST);
		}
		Offre entity = this.convertToEntity(offreDTO);
		entity.setPartner(partner.get());
		this.convertToDto(offreService.createOffre(entity));
		return new ResponseEntity<>(new ApiResponse(true,"Offre created with success!"),HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> updateOffre(@PathVariable("id") Long id,
			@RequestBody OffreDTO offreDTO) {
		Optional<Offre> offre = offreService.findById(id);
		if(!offre.isPresent())
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "The processed offre could not be found!"),HttpStatus.NOT_FOUND);
		this.modelMapper.map(offreDTO, offre.get());
		offreService.updateOffre(id, offre.get());
		return new ResponseEntity<>(new ApiResponse(true,"Offre updated with success!"),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deleteOffre(@PathVariable("id") Long id) {
		Optional<Offre> offre = offreService.findById(id);
		if(!offre.isPresent())
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "The processed offre could not be found!"),HttpStatus.NOT_FOUND);
		offreService.deleteOffre(offre.get());
		return new ResponseEntity<>(new ApiResponse(true,"Offre deleted with success!"),HttpStatus.OK);
	}

	@GetMapping("/find/all")
	public List<OffreDTO> findAll() {
		return offreService.getAllOffres().stream().map(this::convertToDto)
		          .collect(Collectors.toList());
	}

	@GetMapping("/findByPartner/{idPartner}")
	public List<OffreDTO> findByPartner(@PathVariable("idPartner") Long idPartner) {
		return offreService.getPartnerOffres(idPartner).stream().map(this::convertToDto)
		          .collect(Collectors.toList());
	}

	private OffreDTO convertToDto(Offre offre) {
		OffreDTO offreDTO = modelMapper.map(offre, OffreDTO.class);
	    return offreDTO;
	}
	
	private Offre convertToEntity(OffreDTO offreDTO) {
		Offre offre = modelMapper.map(offreDTO, Offre.class);
	    return offre;
	}
}

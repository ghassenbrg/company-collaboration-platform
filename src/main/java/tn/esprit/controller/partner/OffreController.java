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
import tn.esprit.exception.BadRequestException;
import tn.esprit.model.partner.Offre;
import tn.esprit.model.partner.Partner;
import tn.esprit.payload.ApiResponse;
import tn.esprit.payload.OffreDTO;
import tn.esprit.service.partner.OffreService;
import tn.esprit.service.partner.PartnerService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/offres")
public class OffreController {

	private final OffreService offreService;
	private final PartnerService partnerService;
    private final ModelMapper modelMapper;
    
	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<OffreDTO> createOffre(@Valid @RequestBody OffreDTO offreDTO) {
		Optional<Partner> partner = partnerService.findById(offreDTO.getPartnerId());
		if(!partner.isPresent()) {
			throw new BadRequestException(new ApiResponse(false, "The partner with the id: "+offreDTO.getPartnerId()+" could not be found!"));
		}
		Offre entity = this.convertToEntity(offreDTO);
		entity.setPartner(partner.get());
		return new ResponseEntity<>(this.convertToDto(offreService.createOffre(entity)),HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> updateOffre(@PathVariable("id") Long id,
			@RequestBody OffreDTO offreDTO) {
		Offre offre = offreService.findById(id).orElseThrow(() -> new BadRequestException(new ApiResponse(false, "The processed offre could not be found!")));
		this.modelMapper.map(offreDTO, offre);
		offreService.updateOffre(id, offre);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> deleteOffre(@PathVariable("id") Long id) {
		Offre offre = offreService.findById(id).orElseThrow(() -> new BadRequestException(new ApiResponse(false, "The processed offre could not be found!")));
		offreService.deleteOffre(offre);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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

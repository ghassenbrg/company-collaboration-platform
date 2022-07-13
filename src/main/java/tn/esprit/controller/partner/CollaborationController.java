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
import tn.esprit.model.partner.Collaboration;
import tn.esprit.model.partner.Partner;
import tn.esprit.payload.ApiResponse;
import tn.esprit.payload.CollaborationDTO;
import tn.esprit.service.partner.CollaborationService;
import tn.esprit.service.partner.PartnerService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/collaborations")
public class CollaborationController {

	private final CollaborationService collaborationService;
	private final PartnerService partnerService;
    private final ModelMapper modelMapper;
    
	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<CollaborationDTO> createCollaboration(@Valid @RequestBody CollaborationDTO collaborationDTO) {
		Optional<Partner> partner = partnerService.findById(collaborationDTO.getPartnerId());
		if(!partner.isPresent()) {
			throw new BadRequestException(new ApiResponse(false, "The partner with the id: "+collaborationDTO.getPartnerId()+" could not be found!"));
		}
		Collaboration entity = this.convertToEntity(collaborationDTO);
		entity.setPartner(partner.get());
		return new ResponseEntity<>(this.convertToDto(collaborationService.createCollaboration(entity)),HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> updateCollaboration(@PathVariable("id") Long id,
			@RequestBody CollaborationDTO collaborationDTO) {
		Collaboration collaboration = collaborationService.findById(id).orElseThrow(() -> new BadRequestException(new ApiResponse(false, "The processed collaboration could not be found!")));
		this.modelMapper.map(collaborationDTO, collaboration);
		collaborationService.updateCollaboration(id, collaboration);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Object> deleteCollaboration(@PathVariable("id") Long id) {
		Collaboration collaboration = collaborationService.findById(id).orElseThrow(() -> new BadRequestException(new ApiResponse(false, "The processed collaboration could not be found!")));
		collaborationService.deleteCollaboration(collaboration);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/find/all")
	public List<CollaborationDTO> findAll() {
		return collaborationService.getAllCollaborations().stream().map(this::convertToDto)
		          .collect(Collectors.toList());
	}

	@GetMapping("/findByPartner/{idPartner}")
	public List<CollaborationDTO> findByPartner(@PathVariable("idPartner") Long idPartner) {
		return collaborationService.getPartnerCollaborations(idPartner).stream().map(this::convertToDto)
		          .collect(Collectors.toList());
	}

	private CollaborationDTO convertToDto(Collaboration collaboration) {
		CollaborationDTO collaborationDTO = modelMapper.map(collaboration, CollaborationDTO.class);
	    return collaborationDTO;
	}
	
	private Collaboration convertToEntity(CollaborationDTO collaborationDTO) {
		Collaboration collaboration = modelMapper.map(collaborationDTO, Collaboration.class);
	    return collaboration;
	}
}

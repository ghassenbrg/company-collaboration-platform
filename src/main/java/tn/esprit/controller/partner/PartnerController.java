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
import tn.esprit.model.partner.Partner;
import tn.esprit.payload.ApiResponse;
import tn.esprit.payload.PartnerDTO;
import tn.esprit.service.partner.PartnerService;

/**
 * 
 * @author Mazen Aissa
 *
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/partners")
public class PartnerController {

	private final PartnerService partnerService;
    private final ModelMapper modelMapper;
    
	@PostMapping("/create")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> createPartner(@Valid @RequestBody PartnerDTO partnerDTO) {
		this.convertToDto(partnerService.createPartner(this.convertToEntity(partnerDTO)));
		return new ResponseEntity<>(new ApiResponse(true,"Partner created with success!"),HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> updatePartner(@PathVariable("id") Long id,
			@RequestBody PartnerDTO partnerDTO) {
		Optional<Partner> partner = partnerService.findById(id);
		if(!partner.isPresent())
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "The processed partner could not be found!"),HttpStatus.NOT_FOUND);
		partner.get().setCompanyName(partnerDTO.getCompanyName());
		partnerService.updatePartner(id, partner.get());
		return new ResponseEntity<>(new ApiResponse(true,"Partner updated with success!"),HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deletePartner(@PathVariable("id") Long id) {
		Optional<Partner> partner = partnerService.findById(id);
		if(!partner.isPresent())
			return new ResponseEntity<ApiResponse>(new ApiResponse(false, "The processed partner could not be found!"),HttpStatus.NOT_FOUND);
		partnerService.deletePartner(partner.get());
		return new ResponseEntity<>(new ApiResponse(true,"Partner deleted with success!"),HttpStatus.OK);
	}

	@GetMapping("/find/all")
	public List<PartnerDTO> findAll() {
		return partnerService.getAllPartners().stream().map(this::convertToDto)
		          .collect(Collectors.toList());
	}

	@GetMapping("/find/{name}")
	public List<PartnerDTO> findByName(@PathVariable("name") String name) {
		return partnerService.getAllPartnersByName(name).stream().map(this::convertToDto)
		          .collect(Collectors.toList());
	}
	private PartnerDTO convertToDto(Partner partner) {
		PartnerDTO partnerDTO = modelMapper.map(partner, PartnerDTO.class);
	    return partnerDTO;
	}
	
	private Partner convertToEntity(PartnerDTO partnerDTO) {
		Partner partner = modelMapper.map(partnerDTO, Partner.class);
	    return partner;
	}
}

package tn.esprit.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnerDTO {
	private Long id;
	private String companyName;
	private List<OffreDTO> offres;
	private List<CollaborationDTO> collaborations;
	private List<PartnerRatingDTO> ratings;
}

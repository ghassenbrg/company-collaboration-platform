package tn.esprit.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Mazen Aissa
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartnerRatingDTO {
	private Long id;
	private float rating;
	private String comment;
	private Long partnerId;
	private Long userId;
}

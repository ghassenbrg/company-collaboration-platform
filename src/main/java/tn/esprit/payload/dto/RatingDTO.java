package tn.esprit.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingDTO {
	private Long id;
	private float rating;
	private String comment;
	private UserDTO user;
}

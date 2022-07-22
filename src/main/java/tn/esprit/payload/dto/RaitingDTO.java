package tn.esprit.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RaitingDTO {
	private float rating;
	private String comment;
	private UserDTO user;
	private EventDTO event;
}

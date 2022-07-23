package tn.esprit.payload.dto;

import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.model.event.EventAddress;

/**
 * 
 * @author Marwen Lahmar
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {
	private Long id;
	private String name;
	private String description;
	private LocalTime startTime;
	private LocalTime endTime;
	private CategoryDTO category;
	private UserDTO user;
	private EventAddress address;
	private List<ParticipantDTO> participants;
	private List<RatingDTO> ratings;
}

package tn.esprit.payload.dto;

import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.model.event.EventAddress;
import tn.esprit.model.event.Participant;

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
	private UserDTO user;
	private EventAddress address;
	private List<Participant> participants;
	private List<RaitingDTO> ratings;
}

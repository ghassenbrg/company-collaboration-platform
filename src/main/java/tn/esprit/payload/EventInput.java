package tn.esprit.payload;

import java.time.LocalTime;
import java.util.List;

import lombok.Data;
import tn.esprit.model.event.EventAddress;
import tn.esprit.model.event.Participant;
import tn.esprit.model.event.Rating;
import tn.esprit.model.user.User;

/**
 * 
 * @author Marwen Lahmar
 *
 */

@Data
public class EventInput {
	private Long id;
	private String name;
	private String description;
	private LocalTime startTime;
	private LocalTime endTime;
	private User user;
	private EventAddress address;
	private List<Participant> participants;
	private List<Rating> ratings;
}

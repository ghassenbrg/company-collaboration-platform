package tn.esprit.controller.event;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.model.event.Event;
import tn.esprit.payload.ApiResponse;
import tn.esprit.payload.dto.ParticipantDTO;
import tn.esprit.security.CurrentUser;
import tn.esprit.security.UserPrincipal;
import tn.esprit.service.event.EventService;
import tn.esprit.service.event.ParticipantService;

/**
 * 
 * @author Marwen Lahmar
 *
 */

@RestController
@RequestMapping("/events")
public class ParticipantController {

	@Autowired
	private ParticipantService participantService;

	@Autowired
	private EventService eventService;

	@PostMapping("/{eventId}/invite")
	public ResponseEntity<ApiResponse> createEvent(@CurrentUser UserPrincipal currentUser,
			@PathVariable("eventId") Long eventId, @Valid @RequestBody ParticipantDTO participant) {
		Event event = eventService.findEventById(eventId);
		participantService.inviteParticipant(currentUser, participant, event);
		ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "Participant added successfully!");
		return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
	}
}

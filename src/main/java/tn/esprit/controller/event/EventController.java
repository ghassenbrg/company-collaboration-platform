package tn.esprit.controller.event;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.model.event.Event;
import tn.esprit.payload.ApiResponse;
import tn.esprit.security.CurrentUser;
import tn.esprit.security.UserPrincipal;
import tn.esprit.service.event.EventService;

/**
 * 
 * @author Marwen Lahmar
 *
 */

@RestController
@RequestMapping("/events")
public class EventController {

	@Autowired
	private EventService eventService;

	@GetMapping()
	public ResponseEntity<List<Event>> findAll() {
		List<Event> events = eventService.getAllEvents();
		return new ResponseEntity<>(events, HttpStatus.OK);
	}

	@GetMapping("/my-calendar")
	public ResponseEntity<List<Event>> findAllByUser(@CurrentUser UserPrincipal currentUser) {
		List<Event> events = eventService.getEventsByUserId(currentUser);
		return new ResponseEntity<>(events, HttpStatus.OK);
	}

	@GetMapping("/{eventId}")
	public ResponseEntity<Object> getEventDetails(@PathVariable("eventId") Long eventId) {
		Event event = eventService.findEventById(eventId);
		if (event != null) {
			return new ResponseEntity<>(event, HttpStatus.OK);
		} else {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Event does not exist");
			return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping()
	public ResponseEntity<Event> createEvent(@CurrentUser UserPrincipal currentUser, @Valid @RequestBody Event event) {
		event = eventService.createEvent(currentUser, event);
		return new ResponseEntity<>(event, HttpStatus.CREATED);
	}

	@PutMapping("/{eventId}")
	public ResponseEntity<Event> updateEvent(@CurrentUser UserPrincipal currentUser,
			@PathVariable("eventId") Long eventId, @Valid @RequestBody Event event) {
		event = eventService.updateEvent(currentUser, eventId, event);
		return new ResponseEntity<>(event, HttpStatus.OK);
	}

	@DeleteMapping("/{eventId}/cancel")
	public ResponseEntity<ApiResponse> cancelEvent(@CurrentUser UserPrincipal currentUser,
			@PathVariable("eventId") Long eventId) {
		Event event = eventService.findEventById(eventId);
		ApiResponse apiResponse = eventService.cancelEvent(currentUser, event);
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);

	}

}

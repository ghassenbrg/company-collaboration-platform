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
		return new ResponseEntity<>(event, HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<ApiResponse> createEvent(@CurrentUser UserPrincipal currentUser,
			@Valid @RequestBody Event event) {
		event = eventService.createEvent(currentUser, event);
		ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "Event created successfully!");
		return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
	}

	@PutMapping("/{eventId}")
	public ResponseEntity<ApiResponse> updateEvent(@CurrentUser UserPrincipal currentUser,
			@PathVariable("eventId") Long eventId, @Valid @RequestBody Event event) {
		event = eventService.updateEvent(currentUser, eventId, event);
		ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "Event updated successfully!");
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	@DeleteMapping("/{eventId}/cancel")
	public ResponseEntity<ApiResponse> cancelEvent(@CurrentUser UserPrincipal currentUser,
			@PathVariable("eventId") Long eventId) {
		eventService.cancelEvent(currentUser, eventId);
		ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "Event was cancelled successfully");
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

}

package tn.esprit.controller.event;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
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
import tn.esprit.payload.dto.EventDTO;
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

	private ModelMapper modelMapper = new ModelMapper();

	@GetMapping()
	public ResponseEntity<List<EventDTO>> findAll() {
		List<EventDTO> events = eventService.getAllEvents().stream().map(this::convertEventEntityToEventDto)
				.collect(Collectors.toList());
		return new ResponseEntity<>(events, HttpStatus.OK);
	}

	@GetMapping("/my-calendar")
	public ResponseEntity<List<EventDTO>> findAllByUser(@CurrentUser UserPrincipal currentUser) {
		List<EventDTO> events = eventService.getEventsByUserId(currentUser).stream().map(this::convertEventEntityToEventDto)
				.collect(Collectors.toList());
		return new ResponseEntity<>(events, HttpStatus.OK);
	}

	@GetMapping("/{eventId}")
	public ResponseEntity<Object> getEventDetails(@PathVariable("eventId") Long eventId) {
		Event event = eventService.findEventById(eventId);
		return new ResponseEntity<>(event, HttpStatus.OK);
	}

	@PostMapping()
	public ResponseEntity<ApiResponse> createEvent(@CurrentUser UserPrincipal currentUser,
			@Valid @RequestBody EventDTO event) {
		eventService.createEvent(currentUser, convertEventDtoToEventEntity(event));
		ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "Event created successfully!");
		return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
	}

	@PutMapping("/{eventId}")
	public ResponseEntity<ApiResponse> updateEvent(@CurrentUser UserPrincipal currentUser,
			@PathVariable("eventId") Long eventId, @Valid @RequestBody EventDTO event) {
		eventService.updateEvent(currentUser, eventId, convertEventDtoToEventEntity(event));
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

	private EventDTO convertEventEntityToEventDto(Event event) {
		EventDTO eventDTO = modelMapper.map(event, EventDTO.class);
		return eventDTO;
	}

	private Event convertEventDtoToEventEntity(EventDTO eventDTO) {
		Event event = modelMapper.map(eventDTO, Event.class);
		return event;
	}
}

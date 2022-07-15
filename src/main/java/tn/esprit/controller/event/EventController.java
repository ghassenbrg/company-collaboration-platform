package tn.esprit.controller.event;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.model.event.Event;
import tn.esprit.service.event.EventService;

/**
 * 
 * @author Marwen Lahmar
 *
 */

@RestController
@RequestMapping("/api/events")
public class EventController {

	@Autowired
	private EventService eventService;

	@GetMapping("/findAll")
	public List<Event> findAll() {
		return eventService.getAllEvents();
	}

	@GetMapping("/findAllByUser/{userId}")
	public List<Event> findAllByUser(@PathVariable("userId") Long userId) {
		return eventService.getEventsByUserId(userId);
	}

	@GetMapping("/getDetails/{eventId}")
	public Event getEventDetails(@PathVariable("eventId") Long eventId) {
		return eventService.findEventById(eventId);
	}

	@PostMapping("/add")
	public Event createEvent(@Valid @RequestBody Event event) {
		return eventService.createEvent(event);
	}

	@PutMapping("/update/{eventId}")
	public Event updateEvent(@PathVariable("eventId") Long eventId, @Valid @RequestBody Event event) {
		return eventService.updateEvent(eventId, event);
	}

	@DeleteMapping("/cancel/{eventId}")
	public void cancelEvent(@PathVariable("eventId") Long eventId) {
		Event event = eventService.findEventById(eventId);
		eventService.cancelEvent(event);
	}

}

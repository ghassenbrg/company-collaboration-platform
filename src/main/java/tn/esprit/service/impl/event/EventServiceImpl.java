package tn.esprit.service.impl.event;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.exception.AccessDeniedException;
import tn.esprit.model.event.Event;
import tn.esprit.model.user.User;
import tn.esprit.payload.ApiResponse;
import tn.esprit.repository.event.EventRepository;
import tn.esprit.repository.user.UserRepository;
import tn.esprit.security.UserPrincipal;
import tn.esprit.service.event.EventService;

/**
 * 
 * @author Marwen Lahmar
 *
 */
@Service
public class EventServiceImpl implements EventService {

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public List<Event> getAllEvents() {
		return eventRepository.findAll();
	}

	@Override
	public List<Event> getEventsByUserId(UserPrincipal currentUser) {
		List<Event> events = new ArrayList<>();
		List<Event> allEvents = getAllEvents();
		allEvents.forEach(event -> {
			if (event.getUser() != null && event.getUser().getId().equals(currentUser.getId())) {
				events.add(event);
			}
		});
		return events;
	}

	@Override
	public Event findEventById(Long eventId) {
		Event event = eventRepository.findById(eventId).orElse(null);
		return event;
	}

	@Override
	public Event createEvent(UserPrincipal currentUser, Event event) {
		//User user = userRepository.findById(currentUser.getId()).orElse(null);
		//if (event != null) {
		//	event.setUser(user);
		//}
		return eventRepository.save(event);
	}

	@Override
	public Event updateEvent(UserPrincipal currentUser, Long eventId, Event event) {
		if (event.getUser() != null && event.getUser().getId().equals(currentUser.getId())) {
			return eventRepository.save(event);
		}
		ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to update this event");
		throw new AccessDeniedException(apiResponse);
	}

	@Override
	public ApiResponse cancelEvent(UserPrincipal currentUser, Event event) {
		if (event.getUser() != null && event.getUser().getId().equals(currentUser.getId())) {
			eventRepository.delete(event);
			return new ApiResponse(Boolean.TRUE, "Event was cancelled successfully");
		}
		ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to delete this event");
		throw new AccessDeniedException(apiResponse);
		

	}

}

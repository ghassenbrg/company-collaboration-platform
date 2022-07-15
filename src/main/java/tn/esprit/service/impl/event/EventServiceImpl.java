package tn.esprit.service.impl.event;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.model.event.Event;
import tn.esprit.model.user.User;
import tn.esprit.repository.event.EventRepository;
import tn.esprit.repository.user.UserRepository;
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
	public List<Event> getEventsByUserId(Long userId) {
		User user = userRepository.findById(userId).orElse(null);
		List<Event> events = new ArrayList<>();
		List<Event> allEvents = getAllEvents();
		allEvents.forEach(event -> {
			if (event.getUser() != null && event.getUser().equals(user)) {
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
	public Event createEvent(Event event) {
		return eventRepository.save(event);
	}

	@Override
	public Event updateEvent(Long eventId, Event event) {
		return eventRepository.save(event);
	}

	@Override
	public void cancelEvent(Event event) {
		eventRepository.delete(event);
	}

}

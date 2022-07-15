package tn.esprit.service.event;

import java.util.List;
import tn.esprit.model.event.Event;

/**
 * 
 * @author Marwen Lahmar
 *
 */
public interface EventService {

	List<Event> getAllEvents();

	List<Event> getEventsByUserId(Long userId);

	Event findEventById(Long eventId);

	Event createEvent(Event event);

	Event updateEvent(Long eventId, Event event);

	void cancelEvent(Event event);
}

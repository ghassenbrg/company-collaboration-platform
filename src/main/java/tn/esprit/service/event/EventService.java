package tn.esprit.service.event;

import java.util.List;
import tn.esprit.model.event.Event;
import tn.esprit.payload.dto.EventDTO;
import tn.esprit.security.UserPrincipal;

/**
 * 
 * @author Marwen Lahmar
 *
 */
public interface EventService {

	List<Event> getAllEvents();

	List<Event> getEventsByUserId(UserPrincipal currentUser);

	Event findEventById(Long eventId);

	void createEvent(UserPrincipal currentUser, Event event);

	void updateEvent(UserPrincipal currentUser, Long eventId, EventDTO event);

	void cancelEvent(UserPrincipal currentUser, Long eventId);

}

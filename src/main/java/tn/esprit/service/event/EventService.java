package tn.esprit.service.event;

import java.util.List;
import tn.esprit.model.event.Event;
import tn.esprit.payload.ApiResponse;
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

	Event createEvent(UserPrincipal currentUser, Event event);

	Event updateEvent(UserPrincipal currentUser, Long eventId, Event event);

	ApiResponse cancelEvent(UserPrincipal currentUser, Event event);
}

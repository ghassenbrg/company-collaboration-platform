package tn.esprit.service.impl.event;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.exception.AccessDeniedException;
import tn.esprit.exception.BadRequestException;
import tn.esprit.model.event.Category;
import tn.esprit.model.event.Event;
import tn.esprit.model.user.User;
import tn.esprit.payload.ApiResponse;
import tn.esprit.payload.dto.EventDTO;
import tn.esprit.payload.dto.ParticipantDTO;
import tn.esprit.repository.event.EventCategoryRepository;
import tn.esprit.repository.event.EventRepository;
import tn.esprit.repository.user.UserRepository;
import tn.esprit.security.UserPrincipal;
import tn.esprit.service.event.EventCategoryService;
import tn.esprit.service.event.EventService;
import tn.esprit.service.event.ParticipantService;

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

	@Autowired
	private ParticipantService participantService;

	@Autowired
	private EventCategoryService eventCategoryService;

	@Autowired
	private EventCategoryRepository eventCategoryRepository;

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
		if (event == null) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Event does not exist");
			throw new BadRequestException(apiResponse);
		}
		return event;
	}

	@Override
	public void createEvent(UserPrincipal currentUser, Event event) {
		User user = userRepository.findById(currentUser.getId()).orElse(null);
		if (event != null) {
			event.setUser(user);
		}
		eventRepository.save(event);
	}

	@Override
	public void updateEvent(UserPrincipal currentUser, Long eventId, EventDTO eventInput) {
		Event event = findEventById(eventId);
		event.setName(eventInput.getName());
		event.setDescription(eventInput.getDescription());
		event.setAddress(eventInput.getAddress());
		event.setStartTime(eventInput.getStartTime());
		event.setEndTime(eventInput.getEndTime());
		if (eventInput.getCategory() != null) {
			eventCategoryService.addEventCategory(currentUser, eventInput.getCategory(), event);
			Category category = eventCategoryRepository.findById(eventInput.getCategory().getId()).orElse(null);
			event.setCategory(category);
		}

		if (eventInput.getParticipants() != null) {
			for (ParticipantDTO participantDto : eventInput.getParticipants()) {
				participantService.inviteParticipant(currentUser, participantDto, event);
			}
		}
		if (event.getUser() == null || !event.getUser().getId().equals(currentUser.getId())) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to update this event");
			throw new AccessDeniedException(apiResponse);
		}
		eventRepository.save(event);
	}

	@Override
	public void cancelEvent(UserPrincipal currentUser, Long eventId) {
		Event event = findEventById(eventId);
		if (event.getUser() == null || !event.getUser().getId().equals(currentUser.getId())) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to delete this event");
			throw new AccessDeniedException(apiResponse);
		}
		eventRepository.delete(event);
	}

}

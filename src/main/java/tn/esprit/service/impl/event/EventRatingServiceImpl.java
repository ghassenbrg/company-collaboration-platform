package tn.esprit.service.impl.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.exception.AccessDeniedException;
import tn.esprit.exception.BadRequestException;
import tn.esprit.model.event.Event;
import tn.esprit.model.event.Participant;
import tn.esprit.model.event.Rating;
import tn.esprit.payload.ApiResponse;
import tn.esprit.payload.dto.RatingDTO;
import tn.esprit.repository.event.EventRatingRepository;
import tn.esprit.security.UserPrincipal;
import tn.esprit.service.event.EventRatingService;
import tn.esprit.service.event.ParticipantService;

/**
 * 
 * @author Marwen Lahmar
 *
 */
@Service
public class EventRatingServiceImpl implements EventRatingService {

	@Autowired
	private EventRatingRepository ratingRepository;

	@Autowired
	private ParticipantService participantService;

	@Override
	public Rating findEventRatingById(Long ratingId) {
		Rating rating = ratingRepository.findById(ratingId).orElse(null);
		if (rating == null) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "Rating does not exist");
			throw new BadRequestException(apiResponse);
		}
		return rating;
	}

	@Override
	public void addEventRating(UserPrincipal currentUser, RatingDTO ratingInput, Event event) {
		Participant participant = participantService.findParticipant(currentUser, event);
		Rating rating = new Rating();
		if (participant == null) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to review this event!");
			throw new AccessDeniedException(apiResponse);
		}
		rating.setRating(ratingInput.getRating());
		rating.setComment(ratingInput.getComment());
		rating.setUser(participant.getUser());
		rating.setEvent(event);
		ratingRepository.save(rating);
	}

	@Override
	public void updateEventRating(UserPrincipal currentUser, Long ratingId, RatingDTO ratingInput, Event event) {
		Participant participant = participantService.findParticipant(currentUser, event);
		Rating rating = findEventRatingById(ratingId);
		if (participant == null) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to review this event!");
			throw new AccessDeniedException(apiResponse);
		}
		rating.setRating(ratingInput.getRating());
		rating.setComment(ratingInput.getComment());
		ratingRepository.save(rating);

	}

	@Override
	public void removeEventRating(UserPrincipal currentUser, Long ratingId, Event event) {
		Participant participant = participantService.findParticipant(currentUser, event);
		Rating rating = findEventRatingById(ratingId);
		if (participant == null) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE,
					"You don't have permission to remove review on this event!");
			throw new AccessDeniedException(apiResponse);
		}
		ratingRepository.delete(rating);
	}

}

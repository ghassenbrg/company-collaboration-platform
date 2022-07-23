package tn.esprit.service.event;

import tn.esprit.model.event.Event;
import tn.esprit.model.event.Rating;
import tn.esprit.payload.dto.RatingDTO;
import tn.esprit.security.UserPrincipal;

/**
 * 
 * @author Marwen Lahmar
 *
 */
public interface EventRatingService {
	
	Rating findEventRatingById(Long ratingId);

	void addEventRating(UserPrincipal currentUser, RatingDTO ratingInput, Event event);
	
	void updateEventRating(UserPrincipal currentUser, Long ratingId, RatingDTO ratingInput, Event event);
	
	void removeEventRating(UserPrincipal currentUser, Long ratingId, Event event);

}

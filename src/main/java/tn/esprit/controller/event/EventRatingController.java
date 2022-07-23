package tn.esprit.controller.event;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.model.event.Event;
import tn.esprit.payload.ApiResponse;
import tn.esprit.payload.dto.RatingDTO;
import tn.esprit.security.CurrentUser;
import tn.esprit.security.UserPrincipal;
import tn.esprit.service.event.EventRatingService;
import tn.esprit.service.event.EventService;

/**
 * 
 * @author Marwen Lahmar
 *
 */

@RestController
@RequestMapping("/events")
public class EventRatingController {

	@Autowired
	private EventRatingService eventRatingService;

	@Autowired
	private EventService eventService;

	@PostMapping("/{eventId}/review")
	public ResponseEntity<ApiResponse> addEventRating(@CurrentUser UserPrincipal currentUser,
			@PathVariable("eventId") Long eventId, @Valid @RequestBody RatingDTO ratingInput) {
		Event event = eventService.findEventById(eventId);
		eventRatingService.addEventRating(currentUser, ratingInput, event);
		ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "Your review has been added successfully!");
		return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
	}

	@PutMapping("/{eventId}/review/{ratingId}")
	public ResponseEntity<ApiResponse> updateEventRating(@CurrentUser UserPrincipal currentUser,
			@PathVariable("eventId") Long eventId, @PathVariable("ratingId") Long ratingId,
			@Valid @RequestBody RatingDTO ratingInput) {
		Event event = eventService.findEventById(eventId);
		eventRatingService.updateEventRating(currentUser, ratingId, ratingInput, event);
		ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "Your review has been updated successfully!");
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

	@DeleteMapping("/{eventId}/review/{ratingId}/cancel")
	public ResponseEntity<ApiResponse> cancelEventRating(@CurrentUser UserPrincipal currentUser,
			@PathVariable("eventId") Long eventId, @PathVariable("ratingId") Long ratingId) {
		Event event = eventService.findEventById(eventId);
		eventRatingService.removeEventRating(currentUser, ratingId, event);
		ApiResponse apiResponse = new ApiResponse(Boolean.TRUE, "Your review has been removed successfully!");
		return new ResponseEntity<>(apiResponse, HttpStatus.OK);
	}

}

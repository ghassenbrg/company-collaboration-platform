package tn.esprit.service.event;

import tn.esprit.model.event.Event;
import tn.esprit.payload.dto.ParticipantDTO;
import tn.esprit.security.UserPrincipal;

/**
 * 
 * @author Marwen Lahmar
 *
 */
public interface ParticipantService {

	void inviteParticipant(UserPrincipal currentUser, ParticipantDTO participant, Event event);
	
	void acceptEvent(UserPrincipal currentUser, Event event);
	
	void refuseEvent(UserPrincipal currentUser, Event event);

}

package tn.esprit.service.impl.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.exception.AccessDeniedException;
import tn.esprit.model.event.Event;
import tn.esprit.model.event.InvitationStatus;
import tn.esprit.model.event.Participant;
import tn.esprit.model.user.User;
import tn.esprit.payload.ApiResponse;
import tn.esprit.payload.dto.ParticipantDTO;
import tn.esprit.repository.event.ParticipantRepository;
import tn.esprit.repository.user.UserRepository;
import tn.esprit.security.UserPrincipal;
import tn.esprit.service.event.ParticipantService;

/**
 * 
 * @author Marwen Lahmar
 *
 */
@Service
public class ParticipantServiceImpl implements ParticipantService {

	@Autowired
	private ParticipantRepository participantRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public void inviteParticipant(UserPrincipal currentUser, ParticipantDTO participantDto, Event event) {
		if (participantDto != null) {
			Participant participant = new Participant();
			if (participantDto.getUser() != null) {
				User user = userRepository.findById(participantDto.getUser().getId()).orElse(null);
				participant.setUser(user);
				participant.setEvent(event);
			}
			participantRepository.save(participant);
		}

	}

	@Override
	public void acceptEvent(UserPrincipal currentUser, Event event) {
		Participant participant = findParticipant(currentUser, event);
		if (participant == null) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to accept this event");
			throw new AccessDeniedException(apiResponse);
		}
		participant.setInvitationStatus(InvitationStatus.ACCEPTED);
		participantRepository.save(participant);
	}

	@Override
	public void refuseEvent(UserPrincipal currentUser, Event event) {
		Participant participant = findParticipant(currentUser, event);
		if (participant == null) {
			ApiResponse apiResponse = new ApiResponse(Boolean.FALSE, "You don't have permission to refuse this event");
			throw new AccessDeniedException(apiResponse);
		}
		participant.setInvitationStatus(InvitationStatus.REFUSED);
		participantRepository.save(participant);
	}

	@Override
	public Participant findParticipant(UserPrincipal currentUser, Event event) {
		if (event.getParticipants() != null && !event.getParticipants().isEmpty()) {
			for (Participant participant : event.getParticipants()) {
				if (participant != null && participant.getUser() != null && participant.getUser().getId() != null
						&& participant.getUser().getId().equals(currentUser.getId())) {
					return participant;
				}
				return null;
			}
		}
		return null;
	}

}

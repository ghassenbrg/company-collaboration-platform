package tn.esprit.service.impl.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.model.event.Event;
import tn.esprit.model.event.Participant;
import tn.esprit.model.user.User;
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

}

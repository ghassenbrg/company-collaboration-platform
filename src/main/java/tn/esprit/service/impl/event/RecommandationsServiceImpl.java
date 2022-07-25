package tn.esprit.service.impl.event;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.model.event.Event;
import tn.esprit.model.event.Participant;
import tn.esprit.model.user.User;
import tn.esprit.payload.dto.RecommandedEmplyeeDTO;
import tn.esprit.payload.dto.UserDTO;
import tn.esprit.repository.user.UserRepository;
import tn.esprit.security.UserPrincipal;
import tn.esprit.service.event.EventService;
import tn.esprit.service.event.RecommandationsService;

/**
 * 
 * @author Marwen Lahmar
 *
 */
@Service
public class RecommandationsServiceImpl implements RecommandationsService {

	@Autowired
	private EventService eventService;

	@Autowired
	private UserRepository userRepository;

	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public List<RecommandedEmplyeeDTO> findRecommandedEmployees(UserPrincipal currentUser) {
		Map<Long, RecommandedEmplyeeDTO> recommandedEmplyees = new HashMap<Long, RecommandedEmplyeeDTO>();
		List<Event> myEvents = eventService.getEventsByUserId(currentUser);
		for (Event event : myEvents) {
			Duration eventDuration = Duration.between(event.getEndTime(), event.getStartTime());
			for (Participant participant : event.getParticipants()) {
				if (participant.getUser() != null && participant.getUser().getId() != null) {
					if (recommandedEmplyees.containsKey(participant.getUser().getId())) {
						Duration totalTime = recommandedEmplyees.get(participant.getUser().getId()).getTotalTime().plus(eventDuration);
						recommandedEmplyees.get(participant.getUser().getId()).setTotalTime(totalTime);
						recommandedEmplyees.put(participant.getUser().getId(), recommandedEmplyees.get(participant.getUser().getId()));
					}
					else {
						User user = userRepository.findById(participant.getUser().getId()).orElse(null);
						RecommandedEmplyeeDTO recommandedEmplyee = new RecommandedEmplyeeDTO();
						recommandedEmplyee.setUser(convertUserEntityToUserDto(user));
						recommandedEmplyee.setTotalTime(eventDuration);
						recommandedEmplyees.put(participant.getUser().getId(), recommandedEmplyee);
					}
				}
				
			}
		}
		List<RecommandedEmplyeeDTO> recommandedEmplyeesList = new ArrayList<RecommandedEmplyeeDTO>(recommandedEmplyees.values());
		return recommandedEmplyeesList	;
	}

	private UserDTO convertUserEntityToUserDto(User user) {
		UserDTO userDto = modelMapper.map(user, UserDTO.class);
		return userDto;
	}

	

}

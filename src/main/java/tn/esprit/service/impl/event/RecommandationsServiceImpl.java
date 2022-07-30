package tn.esprit.service.impl.event;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.mail.Mail;
import tn.esprit.mail.MailBuilder;
import tn.esprit.model.event.Event;
import tn.esprit.model.event.Participant;
import tn.esprit.model.user.User;
import tn.esprit.payload.dto.RecommandedEmplyeeDTO;
import tn.esprit.payload.dto.UserDTO;
import tn.esprit.repository.user.UserRepository;
import tn.esprit.scheduler.BadgeScheduler;
import tn.esprit.security.UserPrincipal;
import tn.esprit.service.event.EventService;
import tn.esprit.service.event.RecommandationsService;

/**
 * 
 * @author Marwen Lahmar
 *
 */
@Service
@Slf4j
public class RecommandationsServiceImpl implements RecommandationsService {

	@Autowired
	private EventService eventService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Environment environment;

	@Autowired
	private JavaMailSender mailSender;

	private ModelMapper modelMapper = new ModelMapper();

	@Override
	public List<RecommandedEmplyeeDTO> findRecommandedEmployees(UserPrincipal currentUser) {
		Map<Long, RecommandedEmplyeeDTO> recommandedEmplyees = new HashMap<Long, RecommandedEmplyeeDTO>();
		List<Event> myEvents = eventService.getEventsByUserId(currentUser);
		for (Event event : myEvents) {
			Duration eventDuration = Duration.between(event.getEndTime(), event.getStartTime());
			for (Participant participant : event.getParticipants()) {
				if (participant.getUser() != null && participant.getUser().getId() != null
						&& participant.getUser().getId() != currentUser.getId()) {
					if (recommandedEmplyees.containsKey(participant.getUser().getId())) {
						Duration totalTime = recommandedEmplyees.get(participant.getUser().getId()).getTotalTime()
								.plus(eventDuration);
						recommandedEmplyees.get(participant.getUser().getId()).setTotalTime(totalTime);
						recommandedEmplyees.put(participant.getUser().getId(),
								recommandedEmplyees.get(participant.getUser().getId()));
					} else {
						User user = userRepository.findById(participant.getUser().getId()).orElse(null);
						RecommandedEmplyeeDTO recommandedEmplyee = new RecommandedEmplyeeDTO();
						recommandedEmplyee.setUser(convertUserEntityToUserDto(user));
						recommandedEmplyee.setTotalTime(eventDuration);
						recommandedEmplyees.put(participant.getUser().getId(), recommandedEmplyee);
					}
				}

			}
		}
		List<RecommandedEmplyeeDTO> recommandedEmplyeesList = new ArrayList<RecommandedEmplyeeDTO>(
				recommandedEmplyees.values());
		recommandedEmplyeesList = recommandedEmplyeesList.stream().sorted((recommandedEmplye1,
				recommandedEmplye2) -> recommandedEmplye1.getTotalTime().compareTo(recommandedEmplye2.getTotalTime()))
				.collect(Collectors.toList());
		// send email to the user
		String email = currentUser.getEmail();
		final Mail mail = new MailBuilder().From(environment.getProperty("spring.mail.username")).To(email)
				.Template("recommanded-employees-template.html")
				.AddContext("title", "Top Recommanded  employees for you !")
				.AddContext("employee1",
						recommandedEmplyeesList.get(0).getUser().getFirstName() + " "
								+ recommandedEmplyeesList.get(0).getUser().getLastName())
				.AddContext("employee2",
						recommandedEmplyeesList.get(1).getUser().getFirstName() + " "
								+ recommandedEmplyeesList.get(1).getUser().getLastName())
				.AddContext("employee3",
						recommandedEmplyeesList.get(2).getUser().getFirstName() + " "
								+ recommandedEmplyeesList.get(2).getUser().getLastName())
				.AddContext("totalTime1", recommandedEmplyeesList.get(0).getTotalTime())
				.AddContext("totalTime2", recommandedEmplyeesList.get(1).getTotalTime())
				.AddContext("totalTime3", recommandedEmplyeesList.get(2).getTotalTime())
				.Subject("Top Recommanded  employees for you !").createMail();
		try {
			MimeMessage emailMessage = mailSender.createMimeMessage();
			MimeMessageHelper mailBuilder = new MimeMessageHelper(emailMessage, true);
			mailBuilder.setTo(mail.getMailTo());
			mailBuilder.setFrom(mail.getMailFrom());
			mailBuilder.setText(mail.getMailContent(), true);
			mailBuilder.setSubject(mail.getMailSubject());
			mailSender.send(emailMessage);
		} catch (MessagingException e) {
			log.error("Failed to send email to " + mail.getMailTo());
		}

		log.info("Email sent successfully to " + mail.getMailTo());
		return recommandedEmplyeesList;
	}

	private UserDTO convertUserEntityToUserDto(User user) {
		UserDTO userDto = modelMapper.map(user, UserDTO.class);
		return userDto;
	}

}

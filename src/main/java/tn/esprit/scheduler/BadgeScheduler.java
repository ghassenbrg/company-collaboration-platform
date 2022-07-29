package tn.esprit.scheduler;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.mail.Mail;
import tn.esprit.mail.MailBuilder;
import tn.esprit.model.evaluation.Badge;
import tn.esprit.model.user.Employee;
import tn.esprit.model.user.User;
import tn.esprit.repository.evaluation.BadgeRepository;
import tn.esprit.repository.forum.CommentRepository;
import tn.esprit.repository.forum.PostRepository;
import tn.esprit.repository.user.UserRepository;

/**
 * 
 * @author Mohamed ElFadhel NAOUAR
 *
 */
@Configuration
@EnableScheduling
@Slf4j
public class BadgeScheduler {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BadgeRepository badgeRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Environment environment;

	@Scheduled(fixedDelay = 30000) // every 30 seconds
	// @Scheduled(cron = "0 0 */12 ? * *") //Every twelve hours
	void assignBadge() {
		List<User> allUsers = userRepository.findAll();
		for (Iterator<User> iterator = allUsers.iterator(); iterator.hasNext();) {
			User user = (User) iterator.next();
			Badge badgeA = badgeRepository.findById(1l)
					.orElseThrow(() -> new ResourceNotFoundException("Could not found the badge A"));
			Badge badgeB = badgeRepository.findById(1l)
					.orElseThrow(() -> new ResourceNotFoundException("Could not found the badge B"));
			if (postRepository.countPosts(user.getId()) >= 10
					&& !(badgeRepository.checkIfUserHasBadge(user.getId(), badgeA.getId()))) {
				List<Employee> employees = badgeA.getEmployees() != null ? badgeA.getEmployees() : new ArrayList<>();
				employees.add((Employee) user);
				badgeA.setEmployees(employees);
				badgeRepository.save(badgeA);
				// send email to the user
				String email = user.getEmail();
				final Mail mail = new MailBuilder().From(environment.getProperty("spring.mail.username")).To(email)
						.Template("badge-received.html").AddContext("title", "Congratulations on your new badge!")
						.AddContext("firstName", user.getFirstName()).AddContext("badgeName", badgeA.getName())
						.Subject("Congratulations on your new badge!").createMail();
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
			}
			if (commentRepository.countComments(user.getId()) >= 10
					&& !(badgeRepository.checkIfUserHasBadge(user.getId(), badgeB.getId()))) {
				List<Employee> employees = badgeB.getEmployees() != null ? badgeB.getEmployees() : new ArrayList<>();
				employees.add((Employee) user);
				badgeB.setEmployees(employees);
				badgeRepository.save(badgeB);
				// send email to the user
				String email = user.getEmail();
				final Mail mail = new MailBuilder().From(environment.getProperty("spring.mail.username")).To(email)
						.Template("badge-received.html").AddContext("title", "Congratulations on your new badge!")
						.AddContext("firstName", user.getFirstName()).AddContext("badgeName", badgeB.getName())
						.Subject("Congratulations on your new badge!").createMail();
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
			}
		}

	}
}

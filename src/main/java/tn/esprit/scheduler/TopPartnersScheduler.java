package tn.esprit.scheduler;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

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
import tn.esprit.model.user.Employee;
import tn.esprit.model.user.User;
import tn.esprit.payload.TopPartner;
import tn.esprit.service.partner.PartnerService;

/**
 * 
 * @author Mazen Aissa
 *
 */
@Configuration
@EnableScheduling
@Slf4j
public class TopPartnersScheduler {

	@Autowired
	private PartnerService partnerService;

	/*
	 * @Autowired private UserRepository userRepository;
	 */

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Environment environment;

	//to be activated(uncommented) before application startup
	//@Scheduled(fixedDelay = 20000)
	//@Scheduled(cron = "0 0 12 1 * ?") Every month on the 1st, at noon
	void sendTopPartnersOfTheMonthEmail() {
		List<TopPartner> partners = partnerService.findTopPartnersOfPastMonth(3);
		if (partners.size() != 3) {
			log.info("Top partners email ignored!");
			return;
		}
		List<User> allUsers = Arrays
				.asList(new Employee("Mazen", "AISSA", "mazenaissa", "mazenaissa16@gmail.com", "blabla"));// userRepository.findAll();
		for (Iterator<User> iterator = allUsers.iterator(); iterator.hasNext();) {
			User user = (User) iterator.next();
			String email = user.getEmail();
			final Mail mail = new MailBuilder().From(environment.getProperty("spring.mail.username")).To(email)
					.Template("top-partners-template.html").AddContext("title", "Our top 3 partners of the month!")
					.AddContext("firstName", user.getFirstName())
					.AddContext("partnerName1", partners.get(0).getName())
					.AddContext("partnerName2", partners.get(1).getName())
					.AddContext("partnerName3", partners.get(2).getName())
					.AddContext("logo1", partners.get(0).getLogo())
					.AddContext("logo2", partners.get(1).getLogo())
					.AddContext("logo3", partners.get(2).getLogo())
					.AddContext("rating1", partners.get(0).getAverageRating())
					.AddContext("rating2", partners.get(1).getAverageRating())
					.AddContext("rating3", partners.get(2).getAverageRating())
					.Subject("Our top 3 partners of the month!").createMail();
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

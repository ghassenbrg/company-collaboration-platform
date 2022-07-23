package tn.esprit.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.javafaker.Faker;

import tn.esprit.model.Geo;
import tn.esprit.model.event.Category;
import tn.esprit.model.event.Event;
import tn.esprit.model.event.Participant;
import tn.esprit.model.partner.Collaboration;
import tn.esprit.model.partner.Offre;
import tn.esprit.model.partner.Partner;
import tn.esprit.model.partner.PartnerRating;
import tn.esprit.model.user.Admin;
import tn.esprit.model.user.Employee;
import tn.esprit.model.user.Role;
import tn.esprit.model.user.RoleName;
import tn.esprit.model.user.User;
import tn.esprit.model.user.UserAddress;
import tn.esprit.repository.event.EventCategoryRepository;
import tn.esprit.repository.event.EventRepository;
import tn.esprit.repository.event.ParticipantRepository;
import tn.esprit.repository.partner.CollaborationRepository;
import tn.esprit.repository.partner.OffreRepository;
import tn.esprit.repository.partner.PartnerRatingRepository;
import tn.esprit.repository.partner.PartnerRepository;
import tn.esprit.repository.user.RoleRepository;
import tn.esprit.repository.user.UserRepository;
import tn.esprit.security.UserPrincipal;

/**
 * 
 * @author Mohamed Dhia Hachem
 * @author Mazen Aissa
 *
 */

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	private boolean alreadySetup = false;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PartnerRepository partnerRepository;
	
	@Autowired
	private PartnerRatingRepository partnerRatingRepository;
	
	@Autowired
	private CollaborationRepository collaborationRepository;
	
	@Autowired
	private OffreRepository offreRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private ParticipantRepository participantRepository;
	
	@Autowired
	private EventCategoryRepository eventCategoryRepository;
	
	@Override
	@Transactional
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		if (alreadySetup) {
			return;
		}	
		
		// Create initial roles
		Role roleAdmin = createRoleIfNotFound(RoleName.ROLE_ADMIN);
		Role roleUser = createRoleIfNotFound(RoleName.ROLE_USER);
		
		//add admin
		Admin admin = new Admin("admin","admin","admin","admin@admin.com",passwordEncoder.encode("admin"));
		admin.setCreatedBy("admin");
		admin.setLastModifiedBy("admin");
		admin.setRoles(Arrays.asList(roleAdmin, roleUser));
		userRepository.save(admin);

		//set admin as current user
		UsernamePasswordAuthenticationToken adminAuthentication = new UsernamePasswordAuthenticationToken(
				UserPrincipal.create(admin), null, UserPrincipal.create(admin).getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(adminAuthentication);
		
		List<Partner> partners = new ArrayList<>();
		List<User> users = new ArrayList<>();
		
		// init faker
		Faker faker = new Faker();
		
		//fill some partners, offres, collaborations
		for (int i = 0; i < 15; i++) {
			Partner p = new Partner();
			Collaboration c = new Collaboration();
			Offre o = new Offre();
			p.setCompanyName(faker.company().name());
			c.setTitle(faker.lorem().sentence(15));
			c.setDescription(faker.lorem().sentence(25));
			c.setStartDate(faker.date().between(new Date("11/08/2022"), new Date("11/12/2023")));
			c.setEndDate(DateUtils.addDays(c.getStartDate(), ThreadLocalRandom.current().nextInt(1,15)));
			c.setPartner(p);
			partners.add(partnerRepository.save(p));
			o.setTitle(faker.lorem().sentence(15));
			o.setDescription(faker.lorem().sentence(25));
			o.setPartner(p);
			o.setStartDate(faker.date().between(new Date("11/08/2022"), new Date("11/12/2023")));
			o.setEndDate(DateUtils.addYears(c.getStartDate(), ThreadLocalRandom.current().nextInt(1,3)));
			double x = (Math.round(ThreadLocalRandom.current().nextDouble(1,50) * 100d) / 100d);
			if((i%2)==0)
				o.setPrice(x);
			else
				o.setRemise((float)x);
			collaborationRepository.save(c);
			offreRepository.save(o);
		}
		
		for (int i = 0; i < 100; i++) {
			String username = faker.name().username();
			Employee employee = new Employee(faker.name().firstName(), faker.name().lastName(),
					username.length() > 15 ? username.substring(0, 14) : username, faker.internet().emailAddress(),
					passwordEncoder.encode("123456"));
			Geo geo = new Geo(String.valueOf(faker.random().nextInt(1, 10)),
					String.valueOf(faker.random().nextInt(1, 10)));
			UserAddress userAddress = new UserAddress(faker.address().streetAddress(), faker.address().city(),
					faker.address().zipCode(), geo);
			employee.setAddress(userAddress);
			employee.setPhone(faker.phoneNumber().phoneNumber());
			employee.setRoles(Arrays.asList(roleUser));
			users.add(userRepository.save(employee));
		}
		//clear admin from auth context
		SecurityContextHolder.clearContext();
		// fill some partner ratings
		for (int i = 0; i < 400; i++) {
			int userId = ThreadLocalRandom.current().nextInt(1, users.size() - 1);
			User user= users.get(userId);
			int partnerId = ThreadLocalRandom.current().nextInt(1, partners.size() - 1);
			float rating = Math.round(ThreadLocalRandom.current().nextFloat(1,5)*100)/100.0f;
			String comment = null;
			if (rating >= 1 && rating <= 2) {
				comment = "This is a bad partner :/";
			} else if (rating > 2 && rating <= 3) {
				comment = "This is a normal partner :)";
			} else
				comment = "This is a good partner :D";
			UsernamePasswordAuthenticationToken userAuthenticationToken = new UsernamePasswordAuthenticationToken(
					UserPrincipal.create(user), null, UserPrincipal.create(user).getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(userAuthenticationToken);
			PartnerRating ratingPartner = new PartnerRating();
			ratingPartner.setRating(rating);
			ratingPartner.setComment(comment);
			ratingPartner.setPartner(partners.get(partnerId));
			ratingPartner.setUser(users.get(userId));
			partnerRatingRepository.save(ratingPartner);
			SecurityContextHolder.clearContext();
		}
		
		//Set event categories
		for (int i = 0; i < 5; i++) {
			Category category = new Category();
			category.setName(faker.lorem().sentence(10));
			eventCategoryRepository.save(category);
		}
		
		//Set events
		for (int i = 0; i < 50; i++) {
			Event newEvent = new Event();
			newEvent.setName(faker.lorem().sentence(10));
			newEvent.setDescription(faker.lorem().sentence(15));
			eventRepository.save(newEvent);
		}
		for (Event createdEvent : eventRepository.findAll()) {
			for (int j = 1; j <= 5; j++) {
				Participant participant = new Participant();
				participant.setUser(users.get(faker.random().nextInt(1, 20)));
				participant.setEvent(createdEvent);
				participantRepository.save(participant);
			}
		}
		
		alreadySetup = true;
	}

	@Transactional
	private final Role createRoleIfNotFound(RoleName name) {
		Optional<Role> roleFromDb = roleRepository.findByName(name);
		if (!roleFromDb.isPresent()) {
			Role role = new Role(name);
			return roleRepository.save(role);
		}
		return roleFromDb.get();
	}

}
package tn.esprit.config;

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.javafaker.Faker;

import tn.esprit.model.Geo;
import tn.esprit.model.partner.Collaboration;
import tn.esprit.model.partner.Offre;
import tn.esprit.model.partner.Partner;
import tn.esprit.model.user.Admin;
import tn.esprit.model.user.Employee;
import tn.esprit.model.user.Role;
import tn.esprit.model.user.RoleName;
import tn.esprit.model.user.UserAddress;
import tn.esprit.repository.partner.CollaborationRepository;
import tn.esprit.repository.partner.OffreRepository;
import tn.esprit.repository.partner.PartnerRepository;
import tn.esprit.repository.user.RoleRepository;
import tn.esprit.repository.user.UserRepository;

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
	private CollaborationRepository collaborationRepository;
	
	@Autowired
	private OffreRepository offreRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsService customUserDetailsService;
	
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
		admin.setRoles(Arrays.asList(roleAdmin));
		userRepository.save(admin);

		//set admin as current user
		UserDetails userDetails = customUserDetailsService.loadUserByUsername("admin");
		UsernamePasswordAuthenticationToken adminAuthentication = new UsernamePasswordAuthenticationToken(
				userDetails, null, userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(adminAuthentication);

		// init faker
		Faker faker = new Faker();
		
		for (int i = 0; i < 15; i++) {
			Partner p = new Partner();
			Collaboration c = new Collaboration();
			Offre o = new Offre();
			p.setCompanyName(faker.company().name());
			c.setTitle(faker.lorem().sentence(15));
			c.setDescription(faker.lorem().sentence(25));
			c.setStartDate(faker.date().birthday());
			c.setEndDate(DateUtils.addYears(c.getStartDate(), 1));
			c.setPartner(p);
			partnerRepository.save(p);
			o.setTitle(faker.lorem().sentence(15));
			o.setDescription(faker.lorem().sentence(25));
			o.setPartner(p);
			o.setStartDate(faker.date().birthday());
			o.setEndDate(DateUtils.addMonths(o.getStartDate(), 1));
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
			userRepository.save(employee);
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
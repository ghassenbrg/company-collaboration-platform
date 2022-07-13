package tn.esprit.config;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.javafaker.Faker;

import tn.esprit.model.Geo;
import tn.esprit.model.user.Employee;
import tn.esprit.model.user.Role;
import tn.esprit.model.user.RoleName;
import tn.esprit.model.user.UserAddress;
import tn.esprit.repository.user.RoleRepository;
import tn.esprit.repository.user.UserRepository;

/**
 * 
 * @author Mohamed Dhia Hachem
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
	private PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		if (alreadySetup) {
			return;
		}

		// Create initial roles
		Role roleAdmin = createRoleIfNotFound(RoleName.ROLE_ADMIN);
		Role roleUser = createRoleIfNotFound(RoleName.ROLE_USER);

		// init faker
		Faker faker = new Faker();

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
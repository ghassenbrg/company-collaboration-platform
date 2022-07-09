package tn.esprit.controller.user;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import tn.esprit.exception.ApiException;
import tn.esprit.exception.AppException;
import tn.esprit.model.user.Employee;
import tn.esprit.model.user.Role;
import tn.esprit.model.user.RoleName;
import tn.esprit.model.user.User;
import tn.esprit.payload.ApiResponse;
import tn.esprit.payload.JwtAuthenticationResponse;
import tn.esprit.payload.LoginRequest;
import tn.esprit.payload.SignUpRequest;
import tn.esprit.repository.user.RoleRepository;
import tn.esprit.repository.user.UserRepository;
import tn.esprit.security.JwtTokenProvider;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private static final String USER_ROLE_NOT_SET = "User role not set";

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@PostMapping("/signin")
	public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsernameOrEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtTokenProvider.generateToken(authentication);
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
	}

	@PostMapping("/signup")
	public ResponseEntity<ApiResponse> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		if (Boolean.TRUE.equals(userRepository.existsByUsername(signUpRequest.getUsername()))) {
			throw new ApiException(HttpStatus.BAD_REQUEST, "Username is already taken");
		}

		if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
			throw new ApiException(HttpStatus.BAD_REQUEST, "Email is already taken");
		}

		Employee user = new Employee(signUpRequest.getFirstName().toLowerCase(), signUpRequest.getLastName().toLowerCase(),
				signUpRequest.getUsername().toLowerCase(), signUpRequest.getEmail().toLowerCase(),
				passwordEncoder.encode(signUpRequest.getPassword()));

		List<Role> roles = new ArrayList<>();
		roles.add(roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new AppException(USER_ROLE_NOT_SET)));

		user.setRoles(roles);

		User result = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users/{userId}")
				.buildAndExpand(result.getId()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(Boolean.TRUE, "User registered successfully"));
	}
}
package tn.esprit.repository.user;

import java.util.Optional;

import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tn.esprit.exception.ResourceNotFoundException;
import tn.esprit.model.user.User;
import tn.esprit.security.UserPrincipal;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(@NotBlank String username);

	Optional<User> findByEmail(@NotBlank String email);

	boolean existsByUsername(@NotBlank String username);

	boolean existsByEmail(@NotBlank String email);

	Optional<User> findByUsernameOrEmail(String username, String email);

	default User getUser(UserPrincipal currentUser) {
		return getUserByName(currentUser.getUsername());
	}

	default User getUserByName(String username) {
		return findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
	}
}
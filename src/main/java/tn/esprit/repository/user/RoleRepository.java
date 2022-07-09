package tn.esprit.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.esprit.model.user.Role;
import tn.esprit.model.user.RoleName;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(RoleName name);
}
package tn.esprit.service.user;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */
public interface CustomUserDetailsService  {

	UserDetails loadUserById(Long id);
}

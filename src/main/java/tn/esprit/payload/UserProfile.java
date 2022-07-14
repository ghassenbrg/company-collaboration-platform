package tn.esprit.payload;

import java.time.Instant;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.model.user.UserAddress;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProfile {
	private Long id;
	private String username;
	private String firstName;
	private String lastName;
	private Instant joinedAt;
	private String email;
	private UserAddress address;
	private String phone;	
	
}
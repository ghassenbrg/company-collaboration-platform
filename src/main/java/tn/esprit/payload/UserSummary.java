package tn.esprit.payload;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@Data
@AllArgsConstructor
public class UserSummary {
	private Long id;
	private String username;
	private String firstName;
	private String lastName;
}
package tn.esprit.payload.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	private Long id;
	private String username;
	private String firstName;
	private String lastName;
	private Instant joinedAt;
	private String email;
	private String phone;
}

package tn.esprit.model.user;

import javax.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Admin extends User {

	private static final long serialVersionUID = -7836309864282669570L;

	public Admin(String firstName, String lastName, String username, String email, String password) {
		super(firstName, lastName, username, email, password);
	}
}

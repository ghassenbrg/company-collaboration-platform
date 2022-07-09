package tn.esprit.model.user;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Partner extends User {

	private static final long serialVersionUID = 3038143583339315991L;

	public Partner(String firstName, String lastName, String username, String email, String password) {
		super(firstName, lastName, username, email, password);
	}

}

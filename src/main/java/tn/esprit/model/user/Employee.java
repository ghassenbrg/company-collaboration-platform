package tn.esprit.model.user;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Employee extends User {

	private static final long serialVersionUID = 3450093290931865308L;

	public Employee(String firstName, String lastName, String username, String email, String password) {
		super(firstName, lastName, username, email, password);
	}
}

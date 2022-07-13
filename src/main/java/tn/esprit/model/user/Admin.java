package tn.esprit.model.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tn.esprit.model.partner.Offre;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Admin extends User {

	private static final long serialVersionUID = -7836309864282669570L;

	@JsonIgnore
	@OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Offre> offres;

	public Admin(String firstName, String lastName, String username, String email, String password) {
		super(firstName, lastName, username, email, password);
	}
}

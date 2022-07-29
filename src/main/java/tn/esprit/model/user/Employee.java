package tn.esprit.model.user;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tn.esprit.model.evaluation.Badge;
import tn.esprit.model.partner.Reservation;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Employee extends User {

	private static final long serialVersionUID = 3450093290931865308L;

	@JsonIgnore
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Reservation> reservations;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "employees")
	private List<Badge> badges;
	
	public Employee(String firstName, String lastName, String username, String email, String password) {
		super(firstName, lastName, username, email, password);
	}
}

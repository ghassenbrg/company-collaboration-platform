package tn.esprit.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@Entity
@Data
@NoArgsConstructor
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "role_name")
	private RoleName name;

	public Role(RoleName name) {
		this.name = name;
	}
}
package tn.esprit.model.user;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tn.esprit.model.Address;
import tn.esprit.model.Geo;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class UserAddress extends Address {

	private static final long serialVersionUID = -9094519903412712007L;

	@OneToOne(mappedBy = "address")
	private User user;

	public UserAddress(String street, String city, String zipcode, Geo geo) {
		super(street, city, zipcode, geo);
	}
}

package tn.esprit.model.event;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tn.esprit.model.Address;
import tn.esprit.model.Geo;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class EventAddress extends Address {

	private static final long serialVersionUID = 7570186466493948561L;

	@OneToOne(mappedBy = "address")
	private Event user;

	public EventAddress(String street, String city, String zipcode, Geo geo) {
		super(street, city, zipcode, geo);
	}
}

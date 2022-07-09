package tn.esprit.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Mohamed Dhia Hachem
 *
 */

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "address")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Address extends BaseEntity {

	private static final long serialVersionUID = 6557481420641980883L;

	@Column(name = "street")
	private String street;

	@Column(name = "city")
	private String city;

	@Column(name = "zipcode")
	private String zipcode;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "geo_id")
	private Geo geo;

	public Address(String street, String city, String zipcode, Geo geo) {
		this.street = street;
		this.city = city;
		this.zipcode = zipcode;
		this.geo = geo;
	}

}

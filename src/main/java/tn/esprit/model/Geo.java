package tn.esprit.model;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "geo")
public class Geo extends BaseEntity {

	private static final long serialVersionUID = 3668445946509567602L;

	@Column(name = "lat")
	private String lat;

	@Column(name = "lng")
	private String lng;

	@OneToOne(mappedBy = "geo")
	private Address address;

	public Geo(String lat, String lng) {
		this.lat = lat;
		this.lng = lng;
	}

}

package tn.esprit.model.partner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tn.esprit.model.BaseEntity;
import tn.esprit.model.user.Admin;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "offres")
public class Offre extends BaseEntity {

	private static final long serialVersionUID = -3890181761903098002L;
	
	@Column(name = "title")
	@NotBlank
	@Size(min = 10, message = "Offre title must be minimum 10 characters")
	private String title;

	@Column(name = "description")
	@NotBlank
	@Size(min = 10, message = "Offre Description must be minimum 10 characters")
	private String description;
	
	@Column(name = "price")
	private double price;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "admin_id")
	private Admin admin;

}

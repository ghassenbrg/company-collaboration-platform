package tn.esprit.model.partner;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tn.esprit.model.BaseEntity;

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
	private Double price;
	
	@Column(name = "remise")
	private Float remise;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "partner_id")
	private Partner partner;
	
	@Column(name = "start_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	@Column(name = "end_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	
	@OneToMany(mappedBy = "offre", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Reservation> reservations;
}

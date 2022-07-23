package tn.esprit.model.partner;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tn.esprit.model.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "partners")
public class Partner extends BaseEntity {

	private static final long serialVersionUID = 1533266878309725038L;
	
	@Column(name = "company_name")
	@NotBlank
	private String companyName;
	
	@JsonIgnore
	@OneToMany(mappedBy = "partner", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PartnerRating> ratings;
	
	@JsonIgnore
	@OneToMany(mappedBy = "partner", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Offre> offres;
	
	@JsonIgnore
	@OneToMany(mappedBy = "partner", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Collaboration> collaborations;

}

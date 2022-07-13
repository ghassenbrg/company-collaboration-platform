package tn.esprit.model.partner;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
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
@Table(name = "partners")
public class Partner extends BaseEntity {

	private static final long serialVersionUID = 1533266878309725038L;
	
	@Column(name = "company_name")
	@NotBlank
	private String companyName;

}

package tn.esprit.model.partner;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "collaborations")
public class Collaboration extends BaseEntity {

	private static final long serialVersionUID = 3674285859372685815L;

	@Column(name = "title")
	@NotBlank
	@Size(min = 10, message = "Collaboration title must be minimum 10 characters")
	private String title;

	@Column(name = "description")
	@NotBlank
	@Size(min = 10, message = "Collaboration Description must be minimum 10 characters")
	private String description;

	@Column(name = "start_date")
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(name = "end_date")
	@Temporal(TemporalType.DATE)
	private Date endDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "partner_id")
	private Partner partner;
}

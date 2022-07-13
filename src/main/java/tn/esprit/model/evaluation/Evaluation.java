package tn.esprit.model.evaluation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tn.esprit.model.BaseEntity;
import tn.esprit.model.user.User;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
@Table(name = "evaluations")
public class Evaluation extends BaseEntity {

	private static final long serialVersionUID = -2426393872005316761L;

	@Column(name = "rating")
	@NotBlank
	@Min(value = 1)
	@Max(value = 5)
	private float rating;

	@Column(name = "comment")
	@Size(min = 10, message = "Comment body must be minimum 10 characters")
	private String comment;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "evaluated_by")
	private User evaluatedBy;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "evaluated")
	private User evaluated;

}

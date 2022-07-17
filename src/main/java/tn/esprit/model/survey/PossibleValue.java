package tn.esprit.model.survey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tn.esprit.model.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class PossibleValue extends BaseEntity {

	private static final long serialVersionUID = 8993317135991521777L;

	@Column(name = "value")
	private String value;

	@Column(name = "label")
	private String label;

	@Column(name = "order")
	private int order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id")
	private Question question;

}

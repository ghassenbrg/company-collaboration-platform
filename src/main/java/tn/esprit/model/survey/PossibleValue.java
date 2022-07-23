package tn.esprit.model.survey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tn.esprit.model.BaseEntityNoId;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class PossibleValue extends BaseEntityNoId {

	private static final long serialVersionUID = 8993317135991521777L;

	@Id
	private String id;
	
	@Column(name = "value")
	private String value;

	@Column(name = "label")
	private String label;

	@Column(name = "order_")
	private int order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "question_id")
	private Question question;

}

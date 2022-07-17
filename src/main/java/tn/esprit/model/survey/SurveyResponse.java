package tn.esprit.model.survey;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tn.esprit.model.BaseEntity;
import tn.esprit.model.user.User;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class SurveyResponse extends BaseEntity {

	private static final long serialVersionUID = -1062157530847136751L;

	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
	private SurveyResponseStatus status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "survey_id")
	private Survey survey;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(mappedBy = "surveyResponse", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<QuestionResponse> questionResponses;

	public List<QuestionResponse> getQuestionResponses() {
		return questionResponses == null ? new ArrayList<>() : questionResponses;
	}

}

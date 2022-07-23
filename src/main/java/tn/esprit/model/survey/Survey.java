package tn.esprit.model.survey;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tn.esprit.model.BaseEntityNoId;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Survey extends BaseEntityNoId {

	private static final long serialVersionUID = -1021596535985012012L;

	@Id
	private String id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "status")
	@Enumerated(EnumType.ORDINAL)
	private SurveyStatus status;

	@OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<Question> questions;

	@JsonIgnore
	@OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SurveyResponse> responses;

	public List<Question> getQuestions() {
		if (questions != null) {
			questions.sort(Comparator.comparing(Question::getOrder));
			return questions;
		}
		return new ArrayList<>();
	}

	public List<SurveyResponse> getResponses() {
		return responses == null ? new ArrayList<>() : responses;
	}

	public void setQuestions(List<Question> questions) {
		if (questions != null && !questions.isEmpty()) {
			IntStream.range(0, questions.size()).forEach(index -> questions.get(index).setOrder(index));
			questions.sort(Comparator.comparing(Question::getOrder));
		}
		this.questions = questions;
	}

}

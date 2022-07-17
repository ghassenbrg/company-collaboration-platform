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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import tn.esprit.model.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@NoArgsConstructor
public class Question extends BaseEntity {

	private static final long serialVersionUID = 8186195571034458084L;

	@Column(name = "text")
	private String text;

	@Column(name = "description")
	private String description;

	@Column(name = "type")
	@Enumerated(EnumType.ORDINAL)
	private QuestionType type;

	@Column(name = "defaultValue")
	private String defaultValue;

	@Column(name = "isMandatory")
	private boolean isMandatory;

	@Column(name = "isReadOnly")
	private boolean isReadOnly;

	@Column(name = "isMultiple")
	private boolean isMultiple;

	@Column(name = "order")
	private int order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "survey_id")
	private Survey survey;

	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<PossibleValue> possibleValues;

	@JsonIgnore
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<QuestionResponse> responses;

	public List<PossibleValue> getPossibleValues() {
		if (possibleValues != null) {
			possibleValues.sort(Comparator.comparing(PossibleValue::getOrder));
			return possibleValues;
		}
		return new ArrayList<>();
	}

	public List<QuestionResponse> getResponses() {
		return responses == null ? new ArrayList<>() : responses;
	}

	public void setPossibleValues(List<PossibleValue> possibleValues) {
		if (possibleValues != null && !possibleValues.isEmpty()) {
			IntStream.range(0, possibleValues.size()).forEach(index -> possibleValues.get(index).setOrder(index));
			possibleValues.sort(Comparator.comparing(PossibleValue::getOrder));
		}
		this.possibleValues = possibleValues;
	}

}

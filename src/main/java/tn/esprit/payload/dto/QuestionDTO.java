package tn.esprit.payload.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.model.survey.QuestionType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {

	private String id;
	
	private String text;

	private String description;

	private QuestionType type;

	private String defaultValue;

	private boolean isMandatory;

	private boolean isReadOnly;

	private boolean isMultiple;

	private int order;

	private List<PossibleValueDTO> possibleValues;

	private List<QuestionResponseDTO> responses;
}

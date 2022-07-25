package tn.esprit.payload.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.model.survey.SurveyResponseStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyResponseDTO {

	private Long id;

	private String surveyId;

	private String username;

	private SurveyResponseStatus status;

	private List<QuestionResponseDTO> questionResponses;

}

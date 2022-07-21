package tn.esprit.payload.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyResponseDTO {

	private Long id;
	
	private String surveyId;

	private List<QuestionResponseDTO> questionResponses;

}

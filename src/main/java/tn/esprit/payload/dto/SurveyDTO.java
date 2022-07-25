package tn.esprit.payload.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tn.esprit.model.survey.SurveyStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyDTO {

	private String id;

	private String name;

	private String description;

	private SurveyStatus status;

	private List<QuestionDTO> questions;

}

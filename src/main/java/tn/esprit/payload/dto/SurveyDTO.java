package tn.esprit.payload.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyDTO {

	private Long id;

	private String name;

	private String description;

	private List<QuestionDTO> questions;

	private List<SurveyResponseDTO> responses;
}

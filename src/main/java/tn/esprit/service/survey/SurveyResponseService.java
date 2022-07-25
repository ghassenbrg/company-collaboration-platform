package tn.esprit.service.survey;

import java.util.List;

import tn.esprit.model.survey.SurveyResponse;
import tn.esprit.payload.dto.SurveyResponseDTO;

/**
 * 
 * @author Ghassen Bargougui
 *
 */
public interface SurveyResponseService {

	SurveyResponse createResponse(SurveyResponse surveyResponse, String surveyId);

	SurveyResponse getResponse(Long id, String surveyId);

	List<SurveyResponse> getAllResponses(String surveyId);

	byte[] exportResponses(String surveyId);

	SurveyResponse updateResponse(SurveyResponse surveyResponse, Long id, String surveyId, String currentUsername);

	void submitResponse(Long id, String surveyId);

	SurveyResponseDTO convertSurveyResponseToDto(SurveyResponse surveyResponse);

	SurveyResponse convertDtoToSurveyResponse(SurveyResponseDTO surveyResponseDTO);
}

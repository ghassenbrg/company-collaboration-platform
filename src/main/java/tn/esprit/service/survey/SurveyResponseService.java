package tn.esprit.service.survey;

import java.util.List;

import tn.esprit.model.survey.SurveyResponse;

/**
 * 
 * @author Ghassen Bargougui
 *
 */
public interface SurveyResponseService {

	SurveyResponse createResponse(SurveyResponse surveyResponse, Long surveyId);

	SurveyResponse getResponse(Long id, Long surveyId);

	List<SurveyResponse> getAllResponses(Long id, Long surveyId);

	byte[] exportResponses(Long surveyId);

	SurveyResponse updateResponse(SurveyResponse surveyResponse, Long id, Long surveyId);

	void submitResponse(Long id, Long surveyId);

}

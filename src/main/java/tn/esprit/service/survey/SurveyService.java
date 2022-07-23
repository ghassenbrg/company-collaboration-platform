package tn.esprit.service.survey;

import tn.esprit.model.survey.Survey;
import tn.esprit.payload.dto.SurveyDTO;

/**
 * 
 * @author Ghassen Bargougui
 *
 */
public interface SurveyService {

	Survey createSurvey(Survey survey);
	
	Survey createOrUpdateSurvey(Survey survey);

	byte[] getDefaultSurveyTemplate();

	Survey createSurveyByFile(byte[] file);

	Survey getSurey(String id);

	byte[] exportSurvey(String id);

	Survey updateSurvey(Survey survey, String id);

	Survey updateSurveyByFile(byte[] file, String id);

	void deleteSurvey(String id);

	void publishSurvey(String id);

	void cancelSurvey(String id);

	void closeSurvey(String id);
	
	Survey getOrCreateSurvey(String id);
	
	SurveyDTO convertSurveyToDto(Survey survey);

	Survey convertDtoToSurvey(SurveyDTO surveyDTO);
}

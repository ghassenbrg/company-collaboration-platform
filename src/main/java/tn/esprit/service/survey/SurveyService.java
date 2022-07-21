package tn.esprit.service.survey;

import tn.esprit.model.survey.Survey;

/**
 * 
 * @author Ghassen Bargougui
 *
 */
public interface SurveyService {

	Survey createSurvey(Survey survey);

	byte[] getDefaultSurveyTemplate();

	Survey createSurveyByFile(byte[] file);

	Survey getSurey(Long id);

	byte[] exportSurvey(Long id);

	Survey updateSurvey(Survey survey, Long id);

	Survey updateSurveyByFile(byte[] file, Long id);

	void deleteSurvey(Long id);

	void publishSurvey(Long id);

	void cancelSurvey(Long id);

	void closeSurvey(Long id);

}

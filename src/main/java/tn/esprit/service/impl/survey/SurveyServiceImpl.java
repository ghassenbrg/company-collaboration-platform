package tn.esprit.service.impl.survey;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tn.esprit.model.survey.Survey;
import tn.esprit.repository.survey.SurveyRepository;
import tn.esprit.service.survey.SurveyService;

/**
 * 
 * @author Ghassen Bargougui
 *
 */
@RequiredArgsConstructor
@Service
public class SurveyServiceImpl implements SurveyService {

	private final SurveyRepository surveyRepository;

	@Override
	public Survey createSurvey(Survey survey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getDefaultSurveyTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Survey createSurveyByFile(byte[] file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Survey getSurey(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] exportSurvey(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Survey updateSurvey(Survey survey, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Survey updateSurveyByFile(byte[] file, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteSurvey(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void publishSurvey(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelSurvey(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeSurvey(Long id) {
		// TODO Auto-generated method stub

	}

}

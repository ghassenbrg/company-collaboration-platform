package tn.esprit.service.impl.survey;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tn.esprit.model.survey.Survey;
import tn.esprit.payload.dto.SurveyDTO;
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
	public Survey getSurey(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] exportSurvey(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Survey updateSurvey(Survey survey, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Survey updateSurveyByFile(byte[] file, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteSurvey(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void publishSurvey(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancelSurvey(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeSurvey(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Survey getOrCreateSurvey(String id) {
		// TODO Auto-generated method stub
		return null;
		
	}

	@Override
	public SurveyDTO convertSurveyToDto(Survey survey) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Survey convertDtoToSurvey(SurveyDTO surveyDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Survey createOrUpdateSurvey(Survey survey) {
		// TODO Auto-generated method stub
		return null;
	}

}

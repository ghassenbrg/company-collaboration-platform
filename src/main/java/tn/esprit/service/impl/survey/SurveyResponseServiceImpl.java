package tn.esprit.service.impl.survey;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tn.esprit.model.survey.SurveyResponse;
import tn.esprit.repository.survey.SurveyResponseRepository;
import tn.esprit.service.survey.SurveyResponseService;

/**
 * 
 * @author Ghassen Bargougui
 *
 */
@RequiredArgsConstructor
@Service
public class SurveyResponseServiceImpl implements SurveyResponseService {

	private final SurveyResponseRepository surveyResponseRepository;

	@Override
	public SurveyResponse createResponse(SurveyResponse surveyResponse, Long surveyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SurveyResponse getResponse(Long id, Long surveyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SurveyResponse> getAllResponses(Long id, Long surveyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] exportResponses(Long surveyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SurveyResponse updateResponse(SurveyResponse surveyResponse, Long id, Long surveyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void submitResponse(Long id, Long surveyId) {
		// TODO Auto-generated method stub

	}

}

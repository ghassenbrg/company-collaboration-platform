package tn.esprit.service.impl.survey;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tn.esprit.model.survey.Question;
import tn.esprit.model.survey.QuestionResponse;
import tn.esprit.model.survey.Survey;
import tn.esprit.model.survey.SurveyResponse;
import tn.esprit.model.survey.SurveyResponseStatus;
import tn.esprit.model.user.User;
import tn.esprit.payload.dto.QuestionResponseDTO;
import tn.esprit.payload.dto.SurveyResponseDTO;
import tn.esprit.repository.survey.SurveyResponseRepository;
import tn.esprit.service.survey.SurveyResponseService;
import tn.esprit.service.survey.SurveyService;
import tn.esprit.service.user.UserService;

/**
 * 
 * @author Ghassen Bargougui
 *
 */
@RequiredArgsConstructor
@Service
public class SurveyResponseServiceImpl implements SurveyResponseService {

	@Autowired
	private SurveyService surveyService;
	@Autowired
	private UserService userService;
	private final SurveyResponseRepository surveyResponseRepository;
	private final ModelMapper modelMapper;

	@Override
	public SurveyResponse createResponse(SurveyResponse surveyResponse, String surveyId) {
		if (!exist(surveyId, surveyResponse.getUser().getId())) {
			surveyResponse.setId(null);
			surveyResponse.setStatus(SurveyResponseStatus.DRAFT);
			return surveyResponseRepository.save(surveyResponse);
		}
		throw new RuntimeException("The user " + surveyResponse.getUser().getUsername()
				+ " has already respond to the survey with id: " + surveyId);

	}

	@Override
	public SurveyResponse getResponse(Long id, String surveyId) {
		SurveyResponse surveyResponse = surveyResponseRepository.findByIdAndSurvey(id, surveyId);
		if (surveyResponse != null) {
			return surveyResponse;
		}
		throw new RuntimeException("No response with id '" + id + "' for the survey '" + surveyId + "'.");
	}

	@Override
	public List<SurveyResponse> getAllResponses(String surveyId) {
		return surveyResponseRepository.findBySurvey(surveyId);
	}

	@Override
	public byte[] exportResponses(String surveyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SurveyResponse updateResponse(SurveyResponse surveyResponse, Long id, String surveyId,
			String currentUsername) {
		if (exist(surveyId, surveyResponse.getUser().getId())) {
			SurveyResponse surveyResponseFromDB = getResponse(id, surveyId);
			if (!currentUsername.equals(surveyResponseFromDB.getUser().getUsername())) {
				throw new RuntimeException("The current user can't change response of other user.FO");
			}
			if (SurveyResponseStatus.DRAFT.equals(surveyResponseFromDB.getStatus())) {
				surveyResponse.setStatus(SurveyResponseStatus.DRAFT);
				return surveyResponseRepository.save(surveyResponse);
			}
			throw new RuntimeException("You can modify survey responses onlsy when status = DRAFT.");
		}
		throw new RuntimeException("The user " + surveyResponse.getUser().getUsername()
				+ " has already respond to the survey with id: " + surveyId);
	}

	@Override
	public void submitResponse(Long id, String surveyId) {
		SurveyResponse surveyResponse = getResponse(id, surveyId);
		if (SurveyResponseStatus.DRAFT.equals(surveyResponse.getStatus())) {
			surveyResponse.setStatus(SurveyResponseStatus.SUBMITTED);
			surveyResponseRepository.save(surveyResponse);
		} else {
			throw new RuntimeException("Response already submitted.");
		}
	}

	@Override
	public SurveyResponseDTO convertSurveyResponseToDto(SurveyResponse surveyResponse) {
		SurveyResponseDTO surveyResponseDTO = modelMapper.map(surveyResponse, SurveyResponseDTO.class);
		surveyResponseDTO.setSurveyId(surveyResponse.getSurvey().getId());
		surveyResponseDTO.setUsername(surveyResponse.getUser().getUsername());
		if (surveyResponseDTO.getQuestionResponses() != null && !surveyResponseDTO.getQuestionResponses().isEmpty()
				&& surveyResponse.getQuestionResponses() != null && !surveyResponse.getQuestionResponses().isEmpty()) {
			surveyResponseDTO.getQuestionResponses().parallelStream().forEach(responseDTO -> {
				Question question = surveyResponse.getQuestionResponses().parallelStream()
						.filter(response -> responseDTO.getId().equals(response.getId())).findFirst()
						.orElse(new QuestionResponse()).getQuestion();
				responseDTO.setQuestionId(question != null ? question.getId() : "");
			});
		}
		return surveyResponseDTO;
	}

	@Override
	public SurveyResponse convertDtoToSurveyResponse(SurveyResponseDTO surveyResponseDTO) {
		SurveyResponse surveyResponse = modelMapper.map(surveyResponseDTO, SurveyResponse.class);
		Survey survey = surveyService.getSurvey(surveyResponseDTO.getSurveyId());
		User user = userService.getUserByName(surveyResponseDTO.getUsername());
		surveyResponse.setSurvey(survey);
		surveyResponse.setUser(user);
		if (surveyResponse.getQuestionResponses() != null && !surveyResponse.getQuestionResponses().isEmpty()) {
			surveyResponse.getQuestionResponses().parallelStream().forEach(questionResponse -> {
				questionResponse.setSurveyResponse(surveyResponse);
				if (surveyResponseDTO.getQuestionResponses() != null
						&& !surveyResponseDTO.getQuestionResponses().isEmpty() && survey.getQuestions() != null
						&& !survey.getQuestions().isEmpty()) {
					String questionId = surveyResponseDTO.getQuestionResponses().parallelStream()
							.filter(questionResponseDTO -> questionResponseDTO.getId().equals(questionResponse.getId()))
							.findFirst().orElse(new QuestionResponseDTO()).getQuestionId();
					Question question = survey.getQuestions().parallelStream()
							.filter(questionIt -> questionIt.getId().equals(questionId)).findFirst().orElse(null);
					questionResponse.setQuestion(question);
				}
			});
		}
		return surveyResponse;
	}

	private boolean exist(String surveyId, Long userId) {
		SurveyResponse surveyResponse = surveyResponseRepository.findByUserAndSurvey(surveyId, userId);
		return surveyResponse != null ? true : false;
	}

}

package tn.esprit.controller.survey;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import javax.validation.Valid;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.esprit.exception.ResourceNotFoundException;
import tn.esprit.model.survey.QuestionType;
import tn.esprit.model.survey.Survey;
import tn.esprit.model.survey.SurveyResponse;
import tn.esprit.payload.ApiResponse;
import tn.esprit.payload.dto.PossibleValueDTO;
import tn.esprit.payload.dto.QuestionDTO;
import tn.esprit.payload.dto.SurveyDTO;
import tn.esprit.payload.dto.SurveyResponseDTO;
import tn.esprit.security.CurrentUser;
import tn.esprit.security.UserPrincipal;
import tn.esprit.service.survey.SurveyResponseService;
import tn.esprit.service.survey.SurveyService;
import tn.esprit.utils.excel.exporters.SurveyExporter;

/**
 * 
 * @author Ghassen Bargougui
 *
 */

@RestController
@RequestMapping("/surveys")
@PreAuthorize("hasRole('USER')")
public class SurveyController {

	private final static String EXCEL_TEMPLATE_PATH = "survey/template.xlsx";

	@Autowired
	private SurveyService surveyService;

	@Autowired
	private SurveyResponseService surveyResponseService;

	@PostMapping
	public ResponseEntity<ApiResponse> createSurvey(@Valid @RequestBody SurveyDTO surveyDTO) {
		surveyService.createSurvey(surveyService.convertDtoToSurvey(surveyDTO));
		return new ResponseEntity<>(new ApiResponse(true, "Survey created with success!"), HttpStatus.CREATED);

	}

	@GetMapping(value = "/template", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	@ResponseBody
	public byte[] getDefaultSurveyTemplate() {
		try {
			InputStream excelFile = new ClassPathResource(EXCEL_TEMPLATE_PATH).getInputStream();
			return excelFile.readAllBytes();
		} catch (IOException e) {
			throw new ResourceNotFoundException("Survey Excel template file", "path", EXCEL_TEMPLATE_PATH);
		}
	}

	@PostMapping("/import")
	public ResponseEntity<ApiResponse> createSurveyByFile(@Valid @RequestBody byte[] file) {
		surveyService.createSurveyByFile(file);
		return new ResponseEntity<>(new ApiResponse(true, "Survey created with success!"), HttpStatus.CREATED);
	}

	@GetMapping("/{id}")
	public ResponseEntity<SurveyDTO> getSurey(@PathVariable(value = "id") String id) {
		Survey survey = surveyService.getSurvey(id);
		return new ResponseEntity<>(surveyService.convertSurveyToDto(survey), HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/export", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public byte[] exportSurvey(@PathVariable(value = "id") String id) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		SurveyExporter.getInstance().exportSurvey(workbook, getStaticSurvey());
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			workbook.write(bos);
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bos.toByteArray();
	}

	@PutMapping("/{id}")
	public ResponseEntity<SurveyDTO> updateSurvey(@Valid @RequestBody SurveyDTO surveyDTO,
			@PathVariable(value = "id") String id) {
		Survey survey = surveyService.convertDtoToSurvey(surveyDTO);
		survey = surveyService.updateSurvey(survey, id);
		return new ResponseEntity<>(surveyService.convertSurveyToDto(survey), HttpStatus.OK);

	}

	@PutMapping("/{id}/import")
	public ResponseEntity<SurveyDTO> updateSurveyByFile(@Valid @RequestBody byte[] file,
			@PathVariable(value = "id") String id) {
		Survey survey = surveyService.updateSurveyByFile(file, id);
		return new ResponseEntity<>(surveyService.convertSurveyToDto(survey), HttpStatus.OK);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteSurvey(@PathVariable(value = "id") String id) {
		surveyService.deleteSurvey(id);
		return new ResponseEntity<>(new ApiResponse(true, "Survey deleted with success."), HttpStatus.OK);

	}

	@PostMapping("/{id}/publish")
	public ResponseEntity<ApiResponse> publishSurvey(@PathVariable(value = "id") String id) {
		surveyService.publishSurvey(id);
		return new ResponseEntity<>(new ApiResponse(true, "Survey published with success."), HttpStatus.OK);

	}

	@PostMapping("/{id}/cancel")
	public ResponseEntity<ApiResponse> cancelSurvey(@PathVariable(value = "id") String id) {
		surveyService.cancelSurvey(id);
		return new ResponseEntity<>(new ApiResponse(true, "Survey canceled with success."), HttpStatus.OK);

	}

	@PostMapping("/{id}/close")
	public ResponseEntity<ApiResponse> closeSurvey(@PathVariable(value = "id") String id) {
		surveyService.closeSurvey(id);
		return new ResponseEntity<>(new ApiResponse(true, "Survey closed with success."), HttpStatus.OK);

	}

	@PostMapping("/{surveyId}/responses")
	public ResponseEntity<ApiResponse> createResponse(@Valid @RequestBody SurveyResponseDTO surveyResponseDTO,
			@PathVariable(value = "surveyId") String surveyId, @CurrentUser UserPrincipal currentUser) {
		surveyResponseDTO.setUsername(currentUser.getUsername());
		surveyResponseService.createResponse(surveyResponseService.convertDtoToSurveyResponse(surveyResponseDTO),
				surveyId);
		return new ResponseEntity<>(new ApiResponse(true, "Survey created with success!"), HttpStatus.CREATED);

	}

	@GetMapping("/{surveyId}/responses/{id}")
	public ResponseEntity<SurveyResponseDTO> getResponse(@PathVariable(value = "id") Long id,
			@PathVariable(value = "surveyId") String surveyId) {
		SurveyResponse surveyResponse = surveyResponseService.getResponse(id, surveyId);
		return new ResponseEntity<>(surveyResponseService.convertSurveyResponseToDto(surveyResponse), HttpStatus.OK);
	}

	@GetMapping("/{surveyId}/responses")
	public ResponseEntity<List<SurveyResponseDTO>> getAllResponses(@PathVariable(value = "surveyId") String surveyId) {
		List<SurveyResponse> surveyResponses = surveyResponseService.getAllResponses(surveyId);
		List<SurveyResponseDTO> surveyResponseDTOs = surveyResponses.parallelStream()
				.map(surveyResponse -> surveyResponseService.convertSurveyResponseToDto(surveyResponse)).toList();
		return new ResponseEntity<>(surveyResponseDTOs, HttpStatus.OK);
	}

	@GetMapping(value = "/{surveyId}/responses/export", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public byte[] exportResponses(@PathVariable(value = "surveyId") String surveyId) {
		return surveyResponseService.exportResponses(surveyId);
	}

	@PutMapping("/{surveyId}/responses/{id}")
	public ResponseEntity<SurveyResponseDTO> updateResponse(@Valid @RequestBody SurveyResponseDTO surveyResponseDTO,
			@PathVariable(value = "id") Long id, @PathVariable(value = "surveyId") String surveyId,
			@CurrentUser UserPrincipal currentUser) {
		SurveyResponse surveyResponse = surveyResponseService.convertDtoToSurveyResponse(surveyResponseDTO);
		surveyResponse = surveyResponseService.updateResponse(surveyResponse, id, surveyId, currentUser.getUsername());
		return new ResponseEntity<>(surveyResponseService.convertSurveyResponseToDto(surveyResponse), HttpStatus.OK);
	}

	@PostMapping("/{surveyId}/responses/{id}/submit")
	public ResponseEntity<ApiResponse> submitResponse(@PathVariable(value = "id") Long id,
			@PathVariable(value = "surveyId") String surveyId) {
		surveyResponseService.submitResponse(id, surveyId);
		return new ResponseEntity<>(new ApiResponse(true, "Survey submitted with success."), HttpStatus.OK);
	}

	private SurveyDTO getStaticSurvey() {
		SurveyDTO survey = new SurveyDTO();
		survey.setId("news_survey");
		survey.setName("News Survey");
		survey.setDescription("A fake survey for test purposes.");
		List<QuestionDTO> questions = new ArrayList<>();
		QuestionDTO question;
		String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
		// ------ 0 ------
		question = new QuestionDTO();
		question.setId("company");
		question.setText("What's your company name?");
		question.setType(QuestionType.STRING);
		question.setMandatory(true);
		question.setReadOnly(true);
		question.setDefaultValue("Vermeg");
		questions.add(question);
		// ------ 1 ------
		question = new QuestionDTO();
		question.setId("spent_time");
		question.setText("In a typical day, how much time do you spend on your device?");
		question.setType(QuestionType.STRING);
		question.setMandatory(true);
		questions.add(question);
		// ------ 2 ------
		question = new QuestionDTO();
		question.setId("uses_devices");
		question.setText("Which of the following electronic devices do you use? (Please select all that apply.)");
		question.setType(QuestionType.STRING);
		question.setMandatory(true);
		question.setMultiple(true);
		question.setPossibleValues(new ArrayList<>());
		String[] pvalues_1 = { "pc", "tablet", "phone", "tv", "other" };
		String[] plabels_1 = { "Computer", "Tablet computer (e.g. iPad, Samsung Galaxy)", "Smartphones",
				"High Definition TV (HDTV)", "Other" };
		for (int i = 0; i < 5; i++) {
			PossibleValueDTO possibleValue = new PossibleValueDTO();
			possibleValue.setOrder(i);
			possibleValue.setId(question.getId() + "_" + pvalues_1[i]);
			possibleValue.setValue(pvalues_1[i]);
			possibleValue.setLabel(plabels_1[i]);
			question.getPossibleValues().add(possibleValue);
		}
		questions.add(question);
		// ------ 3 ------
		question = new QuestionDTO();
		question.setId("social_media");
		question.setText(
				"In a typical month, which of the following social networking websites do you use most often?");
		question.setType(QuestionType.STRING);
		question.setDefaultValue("fb");
		;
		question.setPossibleValues(new ArrayList<>());
		String[] pvalues_2 = { "gplus", "fb", "tw", "inst", "other" };
		String[] plabels_2 = { "Google+", "Facebook", "Twitter", "Instagram", "Other" };
		for (int i = 0; i < 5; i++) {
			PossibleValueDTO possibleValue = new PossibleValueDTO();
			possibleValue.setOrder(i);
			possibleValue.setId(question.getId() + "_" + pvalues_1[i]);
			possibleValue.setValue(pvalues_2[i]);
			possibleValue.setLabel(plabels_2[i]);
			question.getPossibleValues().add(possibleValue);
		}
		questions.add(question);
		// ------ 4 ------
		question = new QuestionDTO();
		question.setId("social_smarter");
		question.setText("Do you believe technology and social media have made you a smarter, more informed person?");
		question.setType(QuestionType.BOOLEAN);
		questions.add(question);
		// ------ 5 ------
		question = new QuestionDTO();
		question.setId("hours_reading_posts");
		question.setText("In a typical day, how much hours do you spend reading posts on blogs?");
		question.setType(QuestionType.NUMBER);
		questions.add(question);
		// ------ 6 ------
		question = new QuestionDTO();
		question.setId("other_thoughts");
		question.setText("Any other thoughts?");
		question.setType(QuestionType.TEXT);
		questions.add(question);

		IntStream.range(0, questions.size()).forEach(index -> {
			questions.get(index).setOrder(index);
			questions.get(index).setDescription(loremIpsum);
		});
		questions.sort(Comparator.comparing(QuestionDTO::getOrder));

		survey.setQuestions(questions);
		return survey;

	}
}
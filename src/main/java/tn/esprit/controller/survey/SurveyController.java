package tn.esprit.controller.survey;

import java.io.ByteArrayInputStream;
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
import tn.esprit.payload.dto.PossibleValueDTO;
import tn.esprit.payload.dto.QuestionDTO;
import tn.esprit.payload.dto.SurveyDTO;
import tn.esprit.payload.dto.SurveyResponseDTO;
import tn.esprit.service.survey.SurveyResponseService;
import tn.esprit.service.survey.SurveyService;
import tn.esprit.utils.excel.exporters.SurveyExporter;
import tn.esprit.utils.excel.importers.SurveyImporter;

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
	public SurveyDTO createSurvey(@Valid @RequestBody SurveyDTO surveyDTO) {
		// TODO Auto-generated method stub
		return null;
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
	public SurveyDTO createSurveyByFile(@Valid @RequestBody byte[] file) {
		// TODO Auto-generated method stub
		return null;
	}

	@GetMapping("/{id}")
	public SurveyDTO getSurey(@PathVariable(value = "id") String id) {
		// TODO Auto-generated method stub
		return getStaticSurvey();
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
	public SurveyDTO updateSurvey(@Valid @RequestBody SurveyDTO surveyDTO, @PathVariable(value = "id") String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@PutMapping("/{id}/import")
	public SurveyDTO updateSurveyByFile(@Valid @RequestBody byte[] file, @PathVariable(value = "id") String id) {
		InputStream stream = new ByteArrayInputStream(file);
		XSSFWorkbook workbook;
		try {
			workbook = new XSSFWorkbook(stream);
			return SurveyImporter.getInstance().importSurvey(workbook);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Can't convert file to excel.");
		}
	}

	@DeleteMapping("/{id}")
	public void deleteSurvey(@PathVariable(value = "id") String id) {
		// TODO Auto-generated method stub

	}

	@PostMapping("/{id}/publish")
	public void publishSurvey(@PathVariable(value = "id") String id) {
		// TODO Auto-generated method stub

	}

	@PostMapping("/{id}/cancel")
	public void cancelSurvey(@PathVariable(value = "id") String id) {
		// TODO Auto-generated method stub

	}

	@PostMapping("/{id}/close")
	public void closeSurvey(@PathVariable(value = "id") String id) {
		// TODO Auto-generated method stub

	}

	@PostMapping("/{surveyId}/responses")
	public SurveyResponseDTO createResponse(@Valid @RequestBody SurveyResponseDTO surveyResponseDTO,
			@PathVariable(value = "surveyId") String surveyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@GetMapping("/{surveyId}/responses/{id}")
	public SurveyResponseDTO getResponse(@PathVariable(value = "id") String id,
			@PathVariable(value = "surveyId") String surveyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@GetMapping("/{surveyId}/responses")
	public List<SurveyResponseDTO> getAllResponses(@PathVariable(value = "id") String id,
			@PathVariable(value = "surveyId") String surveyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@GetMapping(value = "/{surveyId}/responses/export", produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public byte[] exportResponses(@PathVariable(value = "surveyId") String surveyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@PutMapping("/{surveyId}/responses/{id}")
	public SurveyResponseDTO updateResponse(@Valid @RequestBody SurveyResponseDTO surveyResponseDTO,
			@PathVariable(value = "id") String id, @PathVariable(value = "surveyId") String surveyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@PostMapping("/{surveyId}/responses/{id}/submit")
	public void submitResponse(@PathVariable(value = "id") String id,
			@PathVariable(value = "surveyId") String surveyId) {
		// TODO Auto-generated method stub

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
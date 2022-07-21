package tn.esprit.controller.survey;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.validation.Valid;

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
import tn.esprit.payload.dto.SurveyDTO;
import tn.esprit.payload.dto.SurveyResponseDTO;
import tn.esprit.service.survey.SurveyResponseService;
import tn.esprit.service.survey.SurveyService;

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
	public SurveyDTO getSurey(@PathVariable(value = "id") Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@GetMapping("/{id}/export")
	public byte[] exportSurvey(@PathVariable(value = "id") Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@PutMapping("/{id}")
	public SurveyDTO updateSurvey(@Valid @RequestBody SurveyDTO surveyDTO, @PathVariable(value = "id") Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@PutMapping("/{id}/import")
	public SurveyDTO updateSurveyByFile(@Valid @RequestBody byte[] file, @PathVariable(value = "id") Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@DeleteMapping("/{id}")
	public void deleteSurvey(@PathVariable(value = "id") Long id) {
		// TODO Auto-generated method stub

	}

	@PostMapping("/{id}/publish")
	public void publishSurvey(@PathVariable(value = "id") Long id) {
		// TODO Auto-generated method stub

	}

	@PostMapping("/{id}/cancel")
	public void cancelSurvey(@PathVariable(value = "id") Long id) {
		// TODO Auto-generated method stub

	}

	@PostMapping("/{id}/close")
	public void closeSurvey(@PathVariable(value = "id") Long id) {
		// TODO Auto-generated method stub

	}

	@PostMapping("/{surveyId}/responses")
	public SurveyResponseDTO createResponse(@Valid @RequestBody SurveyResponseDTO surveyResponseDTO, @PathVariable(value = "surveyId") Long surveyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@GetMapping("/{surveyId}/responses/{id}")
	public SurveyResponseDTO getResponse(@PathVariable(value = "id") Long id, @PathVariable(value = "surveyId") Long surveyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@GetMapping("/{surveyId}/responses")
	public List<SurveyResponseDTO> getAllResponses(@PathVariable(value = "id") Long id, @PathVariable(value = "surveyId") Long surveyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@GetMapping("/{surveyId}/responses/export")
	public byte[] exportResponses(@PathVariable(value = "surveyId") Long surveyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@PutMapping("/{surveyId}/responses/{id}")
	public SurveyResponseDTO updateResponse(@Valid @RequestBody SurveyResponseDTO surveyResponseDTO, @PathVariable(value = "id") Long id, @PathVariable(value = "surveyId") Long surveyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@PostMapping("/{surveyId}/responses/{id}/submit")
	public void submitResponse(@PathVariable(value = "id") Long id, @PathVariable(value = "surveyId") Long surveyId) {
		// TODO Auto-generated method stub

	}
}
package tn.esprit.service.impl.survey;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import tn.esprit.exception.ResourceNotFoundException;
import tn.esprit.model.survey.Survey;
import tn.esprit.model.survey.SurveyStatus;
import tn.esprit.payload.dto.SurveyDTO;
import tn.esprit.repository.survey.SurveyRepository;
import tn.esprit.service.survey.SurveyService;
import tn.esprit.utils.excel.exporters.SurveyExporter;
import tn.esprit.utils.excel.importers.SurveyImporter;

/**
 * 
 * @author Ghassen Bargougui
 *
 */
@RequiredArgsConstructor
@Service
public class SurveyServiceImpl implements SurveyService {

	private final static String EXCEL_TEMPLATE_PATH = "survey/template.xlsx";
	private final ModelMapper modelMapper;
	private final SurveyRepository surveyRepository;

	@Override
	public Survey createSurvey(Survey survey) {
		if (!exist(survey.getId())) {
			survey.setStatus(SurveyStatus.DRAFT);
			return surveyRepository.save(survey);
		}
		throw new RuntimeException("Survey with id: " + survey.getId() + " already exist.");
	}

	@Override
	public byte[] getDefaultSurveyTemplate() {
		try {
			InputStream excelFile = new ClassPathResource(EXCEL_TEMPLATE_PATH).getInputStream();
			return excelFile.readAllBytes();
		} catch (IOException e) {
			throw new ResourceNotFoundException("Survey Excel template file", "path", EXCEL_TEMPLATE_PATH);
		}
	}

	@Override
	public Survey createSurveyByFile(byte[] file) {
		InputStream stream = new ByteArrayInputStream(file);
		XSSFWorkbook workbook;
		try {
			workbook = new XSSFWorkbook(stream);
			SurveyDTO surveyDTO = SurveyImporter.getInstance().importSurvey(workbook);
			surveyDTO.setStatus(SurveyStatus.DRAFT);
			return createSurvey(convertDtoToSurvey(surveyDTO));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Can't convert file to excel.");
		}
	}

	@Override
	public Survey getSurvey(String id) {
		Survey survey = surveyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Survey", "id", id));
		if (SurveyStatus.ARCHIVED.equals(survey.getStatus())) {
			new ResourceNotFoundException("Survey", "id", id);
		}
		return survey;
	}

	@Override
	public byte[] exportSurvey(String id) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		Survey survey = getSurvey(id);
		SurveyExporter.getInstance().exportSurvey(workbook, convertSurveyToDto(survey));
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			workbook.write(bos);
			bos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bos.toByteArray();
	}

	@Override
	public Survey updateSurvey(Survey survey, String id) {
		Survey surveyFromDB = getSurvey(id);
		if (SurveyStatus.DRAFT.equals(surveyFromDB.getStatus())) {
			survey.setId(id);
			return surveyRepository.save(survey);
		} else {
			throw new RuntimeException("You can modify survey only when status = DRAFT.");
		}
	}

	@Override
	public Survey updateSurveyByFile(byte[] file, String id) {
		InputStream stream = new ByteArrayInputStream(file);
		XSSFWorkbook workbook;
		try {
			workbook = new XSSFWorkbook(stream);
			SurveyDTO surveyDTO = SurveyImporter.getInstance().importSurvey(workbook);
			return updateSurvey(convertDtoToSurvey(surveyDTO), id);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Can't convert file to excel.");
		}
	}

	@Override
	public void deleteSurvey(String id) {
		Survey survey = getSurvey(id);
		if (SurveyStatus.DRAFT.equals(survey.getStatus())) {
			surveyRepository.delete(survey);
		} else {
			survey.setStatus(SurveyStatus.ARCHIVED);
		}

	}

	@Override
	public void publishSurvey(String id) {
		Survey survey = getSurvey(id);
		if (SurveyStatus.DRAFT.equals(survey.getStatus())) {
			survey.setStatus(SurveyStatus.PENDING);
			surveyRepository.save(survey);
		} else {
			throw new RuntimeException("Can't publish survey with status: " + survey.getStatus() + ".");
		}
	}

	@Override
	public void cancelSurvey(String id) {
		Survey survey = getSurvey(id);
		if (SurveyStatus.DRAFT.equals(survey.getStatus()) || SurveyStatus.PENDING.equals(survey.getStatus())) {
			survey.setStatus(SurveyStatus.CANCELED);
			surveyRepository.save(survey);
		} else {
			throw new RuntimeException("Can't cancel survey with status: " + survey.getStatus() + ".");
		}

	}

	@Override
	public void closeSurvey(String id) {
		Survey survey = getSurvey(id);
		if (SurveyStatus.PENDING.equals(survey.getStatus())) {
			survey.setStatus(SurveyStatus.TERMINATED);
			surveyRepository.save(survey);
		} else {
			throw new RuntimeException("Only survey with status PENDING can be closed.");
		}
	}

	@Override
	public Survey getOrCreateSurvey(String id) {
		Survey survey;
		try {
			survey = getSurvey(id);
		} catch (Exception e) {
			survey = new Survey();
			survey.setStatus(SurveyStatus.DRAFT);
		}
		return survey;

	}

	@Override
	public SurveyDTO convertSurveyToDto(Survey survey) {
		SurveyDTO surveyDTO = modelMapper.map(survey, SurveyDTO.class);
		return surveyDTO;
	}

	@Override
	public Survey convertDtoToSurvey(SurveyDTO surveyDTO) {
		Survey survey = modelMapper.map(surveyDTO, Survey.class);
		if (survey.getQuestions() != null && !survey.getQuestions().isEmpty()) {
			survey.getQuestions().parallelStream().forEach(question -> {
				question.setSurvey(survey);
				if (question.getPossibleValues() != null && !question.getPossibleValues().isEmpty()) {
					question.getPossibleValues().parallelStream()
							.forEach(possibleValue -> possibleValue.setQuestion(question));
				}
			});
		}
		return survey;
	}

	@Override
	public Survey createOrUpdateSurvey(Survey survey) {
		try {
			Survey surveyFromDB = getSurvey(survey.getId());
			survey.setStatus(surveyFromDB.getStatus());
		} catch (Exception e) {
			survey.setStatus(SurveyStatus.DRAFT);
		}
		return surveyRepository.save(survey);
	}

	private boolean exist(String id) {
		Survey survey = surveyRepository.findById(id).orElse(null);
		if (SurveyStatus.ARCHIVED.equals(survey.getStatus())) {
			return false;
		}
		return survey != null ? true : false;
	}

}

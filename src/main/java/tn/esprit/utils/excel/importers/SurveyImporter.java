package tn.esprit.utils.excel.importers;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import tn.esprit.model.survey.QuestionType;
import tn.esprit.payload.dto.PossibleValueDTO;
import tn.esprit.payload.dto.QuestionDTO;
import tn.esprit.payload.dto.SurveyDTO;
import tn.esprit.service.survey.SurveyService;
import tn.esprit.utils.excel.helpers.ExcelConstants;
import tn.esprit.utils.excel.helpers.ExcelHelper;

public class SurveyImporter {

	private SurveyImporter() {
	}

	private static SurveyImporter instance = new SurveyImporter();

	public static SurveyImporter getInstance() {
		return instance;
	}

	public SurveyDTO importSurvey(XSSFWorkbook workbook) {
		// define sheet
		XSSFSheet sheet = workbook.getSheet(ExcelConstants.SURVEY_SHEET);
		// declare row & cell
		Row row = sheet.getRow(1);
		Cell cell;
		SurveyDTO survey;
		int colNum = 0;
		// start importing
		cell = row.getCell(colNum++);
		String identifier = cell.getStringCellValue();
		// survey =
		// surveyService.convertSurveyToDto(surveyService.getOrCreateSurvey(identifier));
		survey = new SurveyDTO();
		survey.setId(identifier);
		//-------------------------------------------------------------------------------
		// Import Name
		cell = row.getCell(colNum++);
		if (cell != null) {
			String name = cell.getStringCellValue();
			survey.setName(name);
		}

		// Import if has Questions
		cell = row.getCell(colNum++);
		if (cell != null && cell.getHyperlink() != null) {
			importQuestions(workbook, survey, ExcelHelper.getInstance().getRowNumFromLink(cell.getHyperlink()));
		}

		// Import Description
		cell = row.getCell(colNum++);
		if (cell != null) {
			String description = cell.getStringCellValue();
			survey.setDescription(description);
		}

		return survey;
	}

	private void importQuestions(XSSFWorkbook workbook, SurveyDTO survey, int rowNum) {
		XSSFSheet sheet = workbook.getSheet(ExcelConstants.QUESTIONS_SHEET);
		List<QuestionDTO> questions = new ArrayList<>();
		// declare row & cell
		Row row = sheet.getRow(rowNum);
		Cell cell;
		DataFormatter formatter = new DataFormatter();
		// start importing
		do {
			int colNum = 0;
			QuestionDTO question = new QuestionDTO();

			// import Order
			cell = row.getCell(colNum++);
			if (cell != null && !("").equals(formatter.formatCellValue(cell))) {
				question.setOrder((int) cell.getNumericCellValue());
			}

			// import Identifier
			cell = row.getCell(colNum++);
			if (cell != null) {
				String identifier = cell.getStringCellValue();
				question.setId(identifier);
			}

			// import Text
			cell = row.getCell(colNum++);
			if (cell != null) {
				String text = cell.getStringCellValue();
				question.setText(text);
				;
			}

			// import Type
			cell = row.getCell(colNum++);
			if (cell != null) {
				String type = cell.getStringCellValue();
				question.setType(QuestionType.valueOf(type));
			}

			// import Default Value
			cell = row.getCell(colNum++);
			if (cell != null) {
				String defaultValue = cell.getStringCellValue();
				question.setDefaultValue(defaultValue);
			}

			// import Is Mandatory
			cell = row.getCell(colNum++);
			if (cell != null && !("").equals(formatter.formatCellValue(cell))) {
				question.setMandatory(cell.getBooleanCellValue());
				;
			}

			// import Is Read Only
			cell = row.getCell(colNum++);
			if (cell != null && !("").equals(formatter.formatCellValue(cell))) {
				question.setReadOnly(cell.getBooleanCellValue());
				;
			}

			// import Is Multiple
			cell = row.getCell(colNum++);
			if (cell != null && !("").equals(formatter.formatCellValue(cell))) {
				question.setMultiple(cell.getBooleanCellValue());
				;
			}

			// import PossibleValues
			cell = row.getCell(colNum++);
			if (cell != null && cell.getHyperlink() != null) {
				importPossibleValues(workbook, question,
						ExcelHelper.getInstance().getRowNumFromLink(cell.getHyperlink()));
			}

			// import Description
			cell = row.getCell(colNum++);
			if (cell != null) {
				String description = cell.getStringCellValue();
				question.setDescription(description);
			}

			// add to list
			questions.add(question);
			row = sheet.getRow(row.getRowNum() + 1);
		} while (row != null);
		// set questions List to survey
		survey.setQuestions(questions);
	}

	private void importPossibleValues(XSSFWorkbook workbook, QuestionDTO question, int rowNum) {
		XSSFSheet sheet = workbook.getSheet(ExcelConstants.POSSIBLE_VALUES_SHEET);
		List<PossibleValueDTO> possibleValues = new ArrayList<>();
		// declare row & cell
		Row row = sheet.getRow(rowNum);
		Cell cell;
		DataFormatter formatter = new DataFormatter();
		// start importing
		do {
			PossibleValueDTO possibleValue = new PossibleValueDTO();
			int colNum = 0;

			// skip question identifier
			colNum++;

			// import Order
			cell = row.getCell(colNum++);
			if (cell != null && !("").equals(formatter.formatCellValue(cell))) {
				possibleValue.setOrder((int) cell.getNumericCellValue());
			}

			// import Identifier
			cell = row.getCell(colNum++);
			if (cell != null) {
				String identifier = cell.getStringCellValue();
				possibleValue.setId(identifier);
			}

			// import Value
			cell = row.getCell(colNum++);
			if (cell != null) {
				String value = cell.getStringCellValue();
				possibleValue.setValue(value);
				;
			}

			// import Label
			cell = row.getCell(colNum++);
			if (cell != null) {
				String label = cell.getStringCellValue();
				possibleValue.setLabel(label);
			}

			// add to list
			possibleValues.add(possibleValue);
			row = sheet.getRow(row.getRowNum() + 1);
		} while ((row != null) && (("").equals(formatter.formatCellValue(row.getCell(0)))));
		// set possible value List to question
		question.setPossibleValues(possibleValues);
	}

}

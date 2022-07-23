package tn.esprit.utils.excel.exporters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import tn.esprit.model.survey.QuestionType;
import tn.esprit.payload.dto.PossibleValueDTO;
import tn.esprit.payload.dto.QuestionDTO;
import tn.esprit.payload.dto.SurveyDTO;
import tn.esprit.utils.excel.helpers.ExcelConstants;
import tn.esprit.utils.excel.helpers.ExcelHelper;
import tn.esprit.utils.excel.helpers.ExcelStyleHelper;

public class SurveyExporter {

	private SurveyExporter() {
	}

	private static SurveyExporter instance = new SurveyExporter();

	public static SurveyExporter getInstance() {
		return instance;
	}

	public int exportSurvey(XSSFWorkbook workbook, SurveyDTO survey) {
		int reference;

		initSheet(workbook);

		// init principal sheet
		Row row;
		Cell cell;
		XSSFSheet surveySheet = workbook.getSheet(ExcelConstants.SURVEY_SHEET);

		// check if the coverage alreay exists
		reference = ExcelHelper.getInstance().exist(survey.getId(), surveySheet, 0);
		if (reference != -1) {
			return reference + 1;
		}

		// parse Entities
		XSSFSheet questionsSheet = workbook.getSheet(ExcelConstants.QUESTIONS_SHEET);

		XSSFSheet possibleValuesSheet = workbook.getSheet(ExcelConstants.POSSIBLE_VALUES_SHEET);

		surveySheet.setTabColor(IndexedColors.ROYAL_BLUE.getIndex());
		questionsSheet.setTabColor(IndexedColors.ORANGE.getIndex());
		possibleValuesSheet.setTabColor(IndexedColors.GOLD.getIndex());
		
		// set Reference
		reference = surveySheet.getLastRowNum() + 2; // +2 for the Link

		// sheet SURVEY
		row = surveySheet.createRow(surveySheet.getLastRowNum() + 1);
		int colNum = 0;
		// set Identifier
		cell = row.createCell(colNum++);
		cell.setCellValue(survey.getId());
		// set Name
		cell = row.createCell(colNum++);
		cell.setCellValue(survey.getName());

		// set Questions
		cell = row.createCell(colNum++);
		if (survey.getQuestions() != null && !survey.getQuestions().isEmpty()) {
			cell.setCellValue("Questions (" + survey.getQuestions().size() + ")");
			ExcelHelper.getInstance().createLink(questionsSheet, questionsSheet.getLastRowNum() + 2, cell);
		}

		// set Description
		cell = row.createCell(colNum++);
		if (survey.getDescription() != null) {
			cell.setCellValue(survey.getDescription());
		}

		// sheet QUESTIONS
		exportQuestions(questionsSheet, possibleValuesSheet, survey);

		// Adjust style & validations
		setValidations(workbook);
		ExcelStyleHelper.getInstance().applyCellStyle(workbook);
		ExcelStyleHelper.getInstance().autoSizeColumns(workbook);

		return reference;
	}

	private void initSheet(XSSFWorkbook workbook) {
		if (workbook.getSheet(ExcelConstants.SURVEY_SHEET) == null) {
			// sheets names List
			List<String> sheets = new ArrayList<>(Arrays.asList(ExcelConstants.SURVEY_SHEET,
					ExcelConstants.QUESTIONS_SHEET, ExcelConstants.POSSIBLE_VALUES_SHEET));

			// headers Lists
			Map<String, List<String>> headers = new HashMap<>();
			headers.put(ExcelConstants.SURVEY_SHEET,
					new ArrayList<>(Arrays.asList("Identifier", "Name", "Questions", "Description")));
			headers.put(ExcelConstants.QUESTIONS_SHEET,
					new ArrayList<>(Arrays.asList("Order", "Identifier", "Text", "Type", "Default Value",
							"Is Mandatory", "Is Read Only", "Is Multiple", "Possible Values", "Description")));
			headers.put(ExcelConstants.POSSIBLE_VALUES_SHEET, new ArrayList<>(
					Arrays.asList("Attached To (Question Identifier)", "Order", "Identifier", "Value", "Label")));

			// init sheets
			ExcelHelper.getInstance().initSheets(sheets, workbook);

			// init headers
			sheets.stream().forEach(sheet -> {
				if (headers.get(sheet) != null)
					ExcelHelper.getInstance().initHeaders(headers.get(sheet), 0, 0, workbook.getSheet(sheet));
			});
		}
	}

	private void exportQuestions(XSSFSheet sheet, XSSFSheet possibleValuesSheet, SurveyDTO survey) {
		Row row;
		Cell cell;
		// sheet QEUSTIONS
		for (QuestionDTO question : survey.getQuestions()) {
			row = sheet.createRow(sheet.getLastRowNum() + 1);
			int colNum = 0;
			// set Order
			cell = row.createCell(colNum++);
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			cell.setCellValue(question.getOrder());

			// set Identifier
			cell = row.createCell(colNum++);
			cell.setCellValue(question.getId());

			// set Text
			cell = row.createCell(colNum++);
			cell.setCellValue(question.getText());

			// set Type
			cell = row.createCell(colNum++);
			cell.setCellValue(question.getType().toString());

			// set Default Value
			cell = row.createCell(colNum++);
			if (question.getDefaultValue() != null) {
				cell.setCellValue(question.getDefaultValue());
			}

			// set Is Mandatoy
			cell = row.createCell(colNum++);
			cell.setCellValue(question.isMandatory());

			// set Is Read Only
			cell = row.createCell(colNum++);
			cell.setCellValue(question.isReadOnly());

			// set Is Multiple
			cell = row.createCell(colNum++);
			cell.setCellValue(question.isMultiple());

			// set Possible Values
			cell = row.createCell(colNum++);
			if (question.getPossibleValues() != null && !question.getPossibleValues().isEmpty()) {
				cell.setCellValue("Possible Values (" + question.getPossibleValues().size() + ")");
				ExcelHelper.getInstance().createLink(possibleValuesSheet, possibleValuesSheet.getLastRowNum() + 2,
						cell);
				// Sheet POSSIBLE_VALUES
				exportPossibleValues(possibleValuesSheet, question);
			}

			// set Description
			cell = row.createCell(colNum++);
			if (question.getDescription() != null) {
				cell.setCellValue(question.getDescription());
			}
		}
	}

	private void exportPossibleValues(XSSFSheet sheet, QuestionDTO question) {
		// sheet POSSIBLE_VALUES
		boolean isFirstObject = true;
		Row row;
		Cell cell;
		for (PossibleValueDTO possibleValue : question.getPossibleValues()) {
			row = sheet.createRow(sheet.getLastRowNum() + 1);
			cell = row.createCell(0);
			int colNum = 0;
			if (isFirstObject) {
				cell.setCellValue(question.getId());
				isFirstObject = false;
			}
			// skip question identifier
			colNum++;
			// set Order
			cell = row.createCell(colNum++);
			cell.setCellType(Cell.CELL_TYPE_NUMERIC);
			cell.setCellValue(possibleValue.getOrder());
			// set Identifier
			cell = row.createCell(colNum++);
			cell.setCellValue(possibleValue.getId());
			// set Value
			cell = row.createCell(colNum++);
			cell.setCellValue(possibleValue.getValue());
			// set Label
			cell = row.createCell(colNum++);
			if (possibleValue.getLabel() != null) {
				cell.setCellValue(possibleValue.getLabel());
			}
		}
	}

	private void setValidations(XSSFWorkbook workbook) {
		String[] typeValues = Arrays.stream(QuestionType.values()).map(QuestionType::name).toArray(String[]::new);
		ExcelHelper.getInstance().setDataValidation(workbook.getSheet(ExcelConstants.QUESTIONS_SHEET), typeValues, 1,
				workbook.getSheet(ExcelConstants.QUESTIONS_SHEET).getPhysicalNumberOfRows() - 1, 3);
		String[] booleanValues = { "TRUE", "FALSE" };
		ExcelHelper.getInstance().setDataValidation(workbook.getSheet(ExcelConstants.QUESTIONS_SHEET), booleanValues, 1,
				workbook.getSheet(ExcelConstants.QUESTIONS_SHEET).getPhysicalNumberOfRows() - 1, 5, 7);

	}

}

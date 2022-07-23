package tn.esprit.utils.excel.helpers;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelStyleHelper {

	private ExcelStyleHelper() {
	}

	private static ExcelStyleHelper instance = new ExcelStyleHelper();

	public static ExcelStyleHelper getInstance() {
		return instance;
	}

	private static final String CALIBRI_FONT = "Calibri";

	public static XSSFCellStyle localStyle(XSSFWorkbook workbook, String border, short position, XSSFFont font) {
		font.setFontName("Consolas");
		XSSFCellStyle style = workbook.createCellStyle();
		style.setFont(font);
		style.setAlignment(position);
		// [_-]
		if (border.contains("[")) {
			style.setBorderLeft(CellStyle.BORDER_THICK);
			style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		}
		if (border.contains("_")) {
			style.setBorderBottom(CellStyle.BORDER_THICK);
			style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		}
		if (border.contains("-")) {
			style.setBorderTop(CellStyle.BORDER_THICK);
			style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		}
		if (border.contains("]")) {
			style.setBorderRight(CellStyle.BORDER_THICK);
			style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		}
		style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
		return style;
	}

	public void autoSizeColumns(Workbook workbook) {
		int numberOfSheets = workbook.getNumberOfSheets();
		for (int i = 0; i < numberOfSheets; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			if (sheet.getPhysicalNumberOfRows() > 0) {
				Row row = sheet.getRow(sheet.getFirstRowNum());
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					int columnIndex = cell.getColumnIndex();
					sheet.autoSizeColumn(columnIndex);
				}
			}
		}
	}

	public CellStyle getFeaturedCellStyle(Workbook workbook, short color) {
		// Create cell style
		CellStyle style = workbook.createCellStyle();
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(color);

		return style;
	}

	public void applyCellStyle(Workbook workbook) {
		int numberOfSheets = workbook.getNumberOfSheets();
		DataFormatter formatter = new DataFormatter();
		// primary Color Per Sheet
		HashMap<String, Short> primaryColorPerSheet = new HashMap<>();
		primaryColorPerSheet.put(ExcelConstants.SURVEY_SHEET, IndexedColors.ROYAL_BLUE.getIndex());
		primaryColorPerSheet.put(ExcelConstants.QUESTIONS_SHEET, IndexedColors.ORANGE.getIndex());
		primaryColorPerSheet.put(ExcelConstants.POSSIBLE_VALUES_SHEET, IndexedColors.GOLD.getIndex());
		// secondary Color Per Sheet
		HashMap<String, Short> secondaryColorPerSheet = new HashMap<>();
		secondaryColorPerSheet.put(ExcelConstants.SURVEY_SHEET, IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		secondaryColorPerSheet.put(ExcelConstants.QUESTIONS_SHEET, IndexedColors.TAN.getIndex());
		secondaryColorPerSheet.put(ExcelConstants.POSSIBLE_VALUES_SHEET, IndexedColors.LIGHT_YELLOW.getIndex());

		// create font
		// white
		Font fontWhite = workbook.createFont();
		fontWhite.setFontHeightInPoints((short) 12);
		fontWhite.setFontName("Arial");
		fontWhite.setColor(IndexedColors.WHITE.getIndex());
		// black
		Font fontBlack = workbook.createFont();
		fontBlack.setFontHeightInPoints((short) 12);
		fontBlack.setFontName("Arial");
		fontBlack.setColor(IndexedColors.BLACK.getIndex());
		// header Height
		short headerHeight = (short) (300 * 1.3);
		// others Height
		short height = (short) (300 * 1.6);
		// apply for all cells
		for (int i = 0; i < numberOfSheets; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			if (sheet.getPhysicalNumberOfRows() > 0) {
				Iterator<Row> rowIterator = sheet.rowIterator();
				while (rowIterator.hasNext()) {
					Row row = rowIterator.next();
					if (row.getRowNum() == 0)
						row.setHeight(headerHeight);
					else
						row.setHeight(height);
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						CellStyle style;
						if (("").equals(formatter.formatCellValue(cell)))
							style = getFeaturedCellStyle(workbook, (short) 22);
						else if (row.getRowNum() == 0 || cell.getColumnIndex() == 0) {
							if (row.getRowNum() == 0) {
								style = getFeaturedCellStyle(workbook, primaryColorPerSheet.get(sheet.getSheetName()));
								style.setFont(fontWhite);

							} else {
								style = getFeaturedCellStyle(workbook,
										secondaryColorPerSheet.get(sheet.getSheetName()));
								style.setFont(fontBlack);
							}
							style.setAlignment(CellStyle.ALIGN_CENTER);
						} else {
							style = workbook.createCellStyle();
							style.setFont(fontBlack);
						}
						style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
						// set border
						style.setBorderTop((short) 1);
						style.setBorderBottom((short) 1);
						style.setBorderRight((short) 1);
						style.setBorderLeft((short) 1);
						// set border color
						style.setTopBorderColor((short) 8);
						style.setBottomBorderColor((short) 8);
						style.setRightBorderColor((short) 8);
						style.setLeftBorderColor((short) 8);
						cell.setCellStyle(style);
					}
				}
			}
		}
	}
}

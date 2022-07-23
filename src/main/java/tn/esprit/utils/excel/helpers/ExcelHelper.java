package tn.esprit.utils.excel.helpers;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.formula.FormulaParseException;
import org.apache.poi.ss.formula.eval.NotImplementedException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelHelper {

	private ExcelHelper() {
	}

	private static ExcelHelper instance = new ExcelHelper();

	public static ExcelHelper getInstance() {
		return instance;
	}

	// get cell by coordinates if row exists
	public Cell getCell(int x, int y, XSSFSheet sheet) {
		y++;
		x++;
		CellReference cellReferenceREF = new CellReference(getColumnChar(x) + y);
		Row rowREF = sheet.getRow(cellReferenceREF.getRow());
		if (rowREF == null) {
			return null;
		}
		return rowREF.getCell(cellReferenceREF.getCol());
	}

	public String getCellStringValue(int x, int y, XSSFSheet sheet) {
		Cell cell = getCell(x, y, sheet);
		if (cell != null) {
			return cell.getStringCellValue();
		} else {
			return null;
		}
	}

	// set cell by coordinates if cell dosen't exist it will create it
	public Cell setCell(int x, int y, XSSFSheet sheet, String value) {
		Cell cell;
		Row row;
		if (sheet.getRow(y) != null) {
			row = sheet.getRow(y);
		} else {
			row = sheet.createRow(y);
		}
		cell = row.createCell(x);
		cell.setCellValue(value);
		return cell;
	}

	// set num cell by coordinates if cell dosen't exist it will create it
	public Cell setNumericCell(int x, int y, XSSFSheet sheet, String value) {
		Cell cell;
		Row row;
		if (sheet.getRow(y) != null) {
			row = sheet.getRow(y);
		} else {
			row = sheet.createRow(y);
		}
		cell = row.createCell(x);
		XSSFCellStyle style = sheet.getWorkbook().createCellStyle();
		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
		cell.setCellStyle(style);
		cell.setCellValue(Float.parseFloat(value));
		return cell;
	}

	private String getColumnChar(int columnNumber) {
		// To store result (Excel column name)
		StringBuilder columnName = new StringBuilder();

		while (columnNumber > 0) {
			// Find remainder
			int rem = columnNumber % 26;

			// If remainder is 0, then a
			// 'Z' must be there in output
			if (rem == 0) {
				columnName.append("Z");
				columnNumber = (columnNumber / 26) - 1;
			} else // If remainder is non-zero
			{
				columnName.append((char) ((rem - 1) + 'A'));
				columnNumber = columnNumber / 26;
			}
		}

		// Reverse the string and print result
		return (columnName.reverse().toString());
	}

	public Map<String, CellStyle> createStyles(Workbook wb) {
		Map<String, CellStyle> styles = new HashMap<>();
		DataFormat df = wb.createDataFormat();

		CellStyle style;
		Font headerFont = wb.createFont();
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerFont.setFontHeightInPoints((short) 12);
		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFont(headerFont);
		styles.put("style1", style);

		style = createBorderedStyle(wb);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setFont(headerFont);
		style.setDataFormat(df.getFormat("d-mmm"));
		styles.put("date_style", style);
		return styles;
	}

	private CellStyle createBorderedStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		return style;
	}

	public int getEntityRowNum(XSSFSheet sheet, int columnIndex, String identifier) {
		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			if (identifier.equals(row.getCell(columnIndex).getStringCellValue())) {
				return row.getRowNum();
			}
		}
		throw new RuntimeException("Link not found in original sheet");
	}

	public void createRangeLink(Sheet sheet, int from, int to, Cell cell) {
		Hyperlink link = sheet.getWorkbook().getCreationHelper().createHyperlink(Hyperlink.LINK_DOCUMENT);
		link.setAddress("'" + sheet.getSheetName() + "'!A" + from + ":" + "A" + to);
		cell.setHyperlink(link);
	}

	public String getLinkedSheetName(Hyperlink link) {
		String[] splits = link.getAddress().split("!");
		return splits[0].replace("'", "");
	}

	public Map<String, XSSFSheet> getSheetsMap(XSSFWorkbook workbook) {
		Map<String, XSSFSheet> allSheets = new HashMap<>();
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			allSheets.put(workbook.getSheetName(i), workbook.getSheetAt(i));
		}
		return allSheets;
	}

	public XSSFSheet getOrcreateSheet(XSSFWorkbook workbook, String sheetname) {
		if (workbook.getSheet(sheetname) == null) {
			workbook.createSheet(sheetname);
			return workbook.getSheet(sheetname);
		} else {
			return workbook.getSheet(sheetname);
		}
	}

	public void createLink(XSSFSheet sheet, int index, Cell cell) {
		Hyperlink link = sheet.getWorkbook().getCreationHelper().createHyperlink(Hyperlink.LINK_DOCUMENT);
		link.setAddress("'" + sheet.getSheetName() + "'!A" + index);
		cell.setHyperlink(link);
	}

	public void initHeaders(List<String> headers, int rowNum, int colNum, Sheet sheet) {
		Row row = sheet.createRow(rowNum);
		AtomicInteger index = new AtomicInteger(colNum);
		headers.stream().forEach(header -> {
			Cell cell = row.createCell(index.get());
			cell.setCellValue(header);
			index.getAndIncrement();
		});
	}

	public XSSFSheet getOrcreateSheet(XSSFWorkbook workbook, String sheetname, List<String> headers) {
		if (workbook.getSheet(sheetname) == null) {
			workbook.createSheet(sheetname);
			if (sheetname.equals("PARAMETERS")) {
				initHeaders(headers, 0, 0, workbook.getSheet("PARAMETERS"));
			} else if (sheetname.equals("TRADUCTIONS")) {
				initHeaders(headers, 0, 1, workbook.getSheet("TRADUCTIONS"));

			}
			return workbook.getSheet(sheetname);

		} else {
			return workbook.getSheet(sheetname);
		}

	}

	public int getRowNumFromLink(Hyperlink link) {
		String address = link.getAddress();
		int index = address.length() - 1;
		StringBuilder ref = new StringBuilder();
		while ((index >= 0) && (isNumeric(address.charAt(index)))) {
			ref.append(address.charAt(index));
			index--;
		}
		if (ref.length() > 0) {
			ref.reverse();
			return Integer.parseInt(ref.toString()) - 1;
		}
		return -1;
	}

	private boolean isNumeric(char c) {
		return (c == '0' || c == '1' || c == '2' || c == '3' || c == '4' || c == '5' || c == '6' || c == '7' || c == '8'
				|| c == '9');
	}

	public int exist(String value, XSSFSheet sheet, int columnIndex) {
		Row row;
		Cell cell;
		DataFormatter formatter = new DataFormatter();
		Iterator<Row> rowIterator = sheet.rowIterator();
		// skip header
		rowIterator.next();
		while (rowIterator.hasNext()) {
			row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				cell = cellIterator.next();
				if ((cell.getColumnIndex() == columnIndex) && (value.equals(formatter.formatCellValue(cell))))
					return row.getRowNum();
			}
		}
		return -1;
	}

	public void setDataValidation(XSSFSheet sheet, String[] possibleValues, int startingRow, int endingRow,
			int startingColumn)  {
		setDataValidation(sheet, possibleValues, startingRow, endingRow, startingColumn, startingColumn);
	}
	public void setDataValidation(XSSFSheet sheet, String[] possibleValues, int startingRow, int endingRow,
			int startingColumn, int endingColumn) {
		XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);
		XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
				.createExplicitListConstraint(possibleValues);
		CellRangeAddressList addressList = new CellRangeAddressList(startingRow, endingRow, startingColumn,
				endingColumn);
		XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
		validation.setShowErrorBox(true);
		sheet.addValidationData(validation);
	}

	public void initFile(String path, XSSFWorkbook workbook) throws IOException {
		FileOutputStream outputStream = new FileOutputStream(path);
		workbook.write(outputStream);
	}

	public int getColumnsCount(XSSFSheet sheet) {
		int result = 0;
		Iterator<Row> rowIterator = sheet.iterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();
			List<Cell> cells = new ArrayList<>();
			Iterator<Cell> cellIterator = row.cellIterator();
			while (cellIterator.hasNext()) {
				cells.add(cellIterator.next());
			}
			for (int i = cells.size(); i > 0; i--) {
				Cell cell = cells.get(i - 1);
				if (cell.toString().trim().isEmpty()) {
					cells.remove(i - 1);
				} else {
					result = cells.size() > result ? cells.size() : result;
					break;
				}
			}
		}
		return result;
	}

	public List<XSSFSheet> getSheets(XSSFWorkbook workbook) {
		List<XSSFSheet> allSheets = new ArrayList<>();
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			allSheets.add(workbook.getSheetAt(i));
		}
		return allSheets;
	}

	public void initSheets(List<String> sheets, XSSFWorkbook workbook) {
		List<XSSFSheet> sheetNames = new ArrayList<>();
		sheets.stream().forEach(sheet -> sheetNames.add(workbook.createSheet(sheet)));
	}

	public void createNewRowAndSetFirstCell(XSSFSheet sheet, String value, boolean isFirstRow) {
		if (isFirstRow) {
			Row row = sheet.createRow(0);
			Cell cell = row.createCell(0);
			cell.setCellValue(value);
			return;
		}
		Row row = sheet.createRow(sheet.getLastRowNum() + 1);
		Cell cell = row.createCell(0);
		cell.setCellValue(value);
	}

	public Double getNumericFormCell(FormulaEvaluator evaluator, XSSFSheet sheet, String ref) {
		CellReference cellReferenceREF = new CellReference(ref);
		Row rowREF = sheet.getRow(cellReferenceREF.getRow());
		if (rowREF == null) {
			return null;
		}
		Cell cellREF = rowREF.getCell(cellReferenceREF.getCol());
		try {
			CellValue cellValue = evaluator.evaluate(cellREF);
			if (cellValue != null && Cell.CELL_TYPE_NUMERIC == cellValue.getCellType()) {
				return cellValue.getNumberValue();
			} else if (cellValue != null && Cell.CELL_TYPE_ERROR == cellValue.getCellType()) {
				return cellREF.getNumericCellValue();
			}
		} catch (Exception e1) {
			return cellREF.getNumericCellValue();
		}
		return null;
	}

	public Date getDateFormCell(FormulaEvaluator evaluator, XSSFSheet sheet, String ref) {
		CellReference cellReferenceREF = new CellReference(ref);
		Row rowREF = sheet.getRow(cellReferenceREF.getRow());
		Cell cellREF = rowREF.getCell(cellReferenceREF.getCol());
		try {
			CellValue cellValue = evaluator.evaluate(cellREF);
			if (cellValue != null && Cell.CELL_TYPE_NUMERIC == cellValue.getCellType()) {
				return cellREF.getDateCellValue();
			}
		} catch (IllegalArgumentException | NotImplementedException e1) {
			return null;
		}
		return null;
	}

	public String getStringFormCell(FormulaEvaluator evaluator, XSSFSheet sheet, String ref) {
		CellReference cellReferenceREF = new CellReference(ref);
		Row rowREF = sheet.getRow(cellReferenceREF.getRow());
		if (rowREF == null) {
			return null;
		}
		Cell cellREF = rowREF.getCell(cellReferenceREF.getCol());
		try {
			CellValue cellValue = evaluator.evaluate(cellREF);
			if (cellValue != null && Cell.CELL_TYPE_STRING == cellValue.getCellType()) {
				return cellValue.getStringValue();
			} else if (cellValue != null && Cell.CELL_TYPE_BOOLEAN == cellValue.getCellType()) {
				return "" + cellValue.getBooleanValue();
			} else if (cellValue != null && Cell.CELL_TYPE_ERROR == cellValue.getCellType()) {
				return cellREF.getStringCellValue();
			}
		} catch (FormulaParseException e) {
			return cellREF.getStringCellValue();
		} catch (IllegalArgumentException | NotImplementedException e1) {
			return cellREF.getStringCellValue();
		} catch (IllegalStateException e) {
			return null;
		}
		return null;
	}

	public String getAnyTypeFormCellAsString(FormulaEvaluator evaluator, XSSFSheet sheet, String ref) {
		String valueString = getStringFormCell(evaluator, sheet, ref);
		if (valueString == null) {
			Double valueDouble = getNumericFormCell(evaluator, sheet, ref);
			DecimalFormat df = new DecimalFormat("#.####");
			if (valueDouble != null) {
				valueString = df.format(valueDouble);
				if (valueString.endsWith(".0")) {
					valueString = valueString.replace(".0", "");
				}
			}
		}
		return valueString;
	}

	public String parseToExcelCellReference(int rowNumber, int columnNumber) {
		return parseToAlphabeticColumn(columnNumber) + rowNumber;
	}

	public String parseToAlphabeticColumn(int i) {
		if (i < 0) {
			return "-" + parseToAlphabeticColumn(-i - 1);
		}
		int quot = i / 26;
		int rem = i % 26;
		char letter = (char) ((int) 'A' + rem);
		if (quot == 0) {
			return "" + letter;
		} else {
			return parseToAlphabeticColumn(quot - 1) + letter;
		}
	}

	public Row getRow(XSSFSheet sheet, int rowid) {
		Row row = sheet.getRow(rowid);
		if (row == null) {
			row = sheet.createRow(rowid);
		}
		return row;
	}

	public Cell getCell(Row row, int cellid) {
		Cell cell = row.getCell(cellid);
		if (cell == null) {
			cell = row.createCell(cellid);
		}
		return cell;
	}

	public void autoSize(XSSFSheet sheet) {
		for (int i = 0; i < 100; i++) {
			sheet.autoSizeColumn(i);
		}
	}

	public Cell getCellFromRef(String ref, XSSFSheet sheet) {
		CellReference cellReferenceREF = new CellReference(ref);
		Row rowREF = sheet.getRow(cellReferenceREF.getRow());
		if (rowREF == null) {
			return null;
		}
		Cell cell = rowREF.getCell(cellReferenceREF.getCol());
		return cell;
	}
}

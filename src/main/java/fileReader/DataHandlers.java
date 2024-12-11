
package fileReader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import base.Config;
import commonHelper.GenericHelper;
import commonHelper.ResourceHelper;

public class DataHandlers {
	private Logger log = Logger.getLogger(DataHandlers.class.getName());

	private FileInputStream inputStream;
	Workbook workbook;

	/**this method returns work book object**/
	private Workbook getExcelWorkBook() {
		ResourceHelper resourceHelper = new ResourceHelper();
		String filename = new Config().getExcelFileName();
		try {
			inputStream = resourceHelper.getResourceInputStream(filename);
		} catch (Exception e) {
			log.info("Error creating File Input Stream Object for " + filename);
			e.printStackTrace();
		}
		String fileExtensionName = resourceHelper.getFileExtention(filename);
		log.info(resourceHelper.getFileExtention(filename));
		// Check condition if the file is xlsx file
		if (fileExtensionName.equals(".xlsx")) {
			// If it is xlsx file then create object of XSSFWorkbook class
			try {
				workbook = new XSSFWorkbook(inputStream);
			} catch (IOException exp) {
				log.info("Error in Creating XSSF Workbook Object");
				exp.printStackTrace();
			}
			log.info("Created XSSFWorkbook object");
		}
		// Check condition if the file is xls file
		else if (fileExtensionName.equals(".xls")) {
			try {
				// If it is xls file then create object of HSSFWorkbook class
				workbook = new HSSFWorkbook(inputStream);
				log.info("Created HSSFWorkbook object");
			} catch (Exception exp) {
				exp.printStackTrace();
			}
		}
		return workbook;
	}

	/*** Method to work with excel files for Test data management ***/
	private Sheet getExcelSheet(String sheetName) {
		log.info("Returning Sheet Object For " + sheetName);
		return getExcelWorkBook().getSheet(sheetName);
	}

	/*** This method returns cell values wrt indexes ***/
	public String getCellValue(String sheetName, int rowValue, int coloumn) throws IOException {
		// Load excel Sheet Object
		Sheet sheet = getExcelSheet(sheetName);
		// Create Row Object
		Row row = sheet.getRow(rowValue);
		// Copy cell value
		String cellValue = row.getCell(coloumn).toString();
		// Return read data
		if (cellValue == null) {
			try {
				log.info("cellValue is null");
				throw new Exception("cellValue is null");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cellValue;
	}

	/*** This method returns cell values wrt indexes in integer ***/
	public String getIntegerCellValue(String sheetName, int rowValue, int coloumn) throws IOException {
		// Load excel Sheet Object
		Sheet sheet = getExcelSheet(sheetName);
		// Create Row Object
		Row row = sheet.getRow(rowValue);
		// Copy cell value
		String cellValue = new DataFormatter().formatCellValue(row.getCell(coloumn));
		// Return read data
		if (cellValue == null) {
			try {
				log.info("cellValue is null");
				throw new Exception("cellValue is null");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cellValue;
	}

	/*** This method is used to save data written into excel ***/
	public void setExcelData() {
		// Close Saved file
		try {
			inputStream.close();
			// Create an object of FileOutputStream class to create write data in excel file
			String filename = new Config().getExcelFileName();
			FileOutputStream fos = new ResourceHelper().getResourceOutputStream(filename);
			// write data in the excel file
			if (workbook == null) {
				getExcelWorkBook();
			}
			workbook.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** This method writes data in desired row and column **/
	public void setCellValue(String sheetName, int rowValue, int coloumn, String value) {
	log.info("Setting excel data for given index");
	Sheet sheet = getExcelSheet(sheetName);
	Cell cell2Update = sheet.getRow(rowValue).createCell(coloumn);
	cell2Update.setCellValue(value);
	setExcelData();
	}

	/*** This method returns number of rows from given sheet ***/
	public int rowCount(String sheetName) {
		// Load Sheet
		Sheet sheet = getExcelSheet(sheetName);
		// Returns total active columns
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();

		return rowCount;

	}

	/*** This method returns number of column from given sheet ***/
	public int columnCount(String sheetName) throws IOException {
		// Load Sheet
		Sheet sheet = getExcelSheet(sheetName);

		// Returns total active columns
		return (sheet.getRow(0).getLastCellNum()) - (sheet.getRow(0).getFirstCellNum());

	}

	/***
	 * This method returns collection of objects that maps keys to values from given
	 * sheet
	 ***/
	public Map<String, Map<String, String>> readTestData(String sheetName) {

		Sheet sheet = getExcelSheet(sheetName);

		int lastRow = sheet.getLastRowNum();

		Map<String, Map<String, String>> fileMap = new HashMap<String, Map<String, String>>();

		Map<String, String> dataMap = new HashMap<String, String>();

		for (int i = 0; i <= lastRow; i++) {
			if (sheet.getRow(i) != null) {
				Row row = sheet.getRow(i);
				if (row.getCell(0) != null) {
					Cell keyCol = row.getCell(0);
					keyCol.setCellType(Cell.CELL_TYPE_STRING);
					String keyData = keyCol.toString().trim();

					Cell valueCol = row.getCell(1);
					if (valueCol != null && valueCol.toString().length() != 0) {
						valueCol.setCellType(Cell.CELL_TYPE_STRING);
						String valueData = valueCol.toString().trim();

						dataMap.put(keyData, valueData);
					}
					fileMap.put("TestData", dataMap);
				}
			}
		}
		return fileMap;

	}

	/***
	 * This method returns an object that maps keys to values from given sheet
	 ***/
	public String getMapData(String sheet, String key) {

		Map<String, String> mapData = readTestData(sheet).get("TestData");

		String value = mapData.get(key);
		if (value == null) {
			new GenericHelper().throwUserException("null value found for key or key does not exists");
		}
		return value;

	}

	/***
	 * This method returns an object that maps keys to values from given sheet
	 ***/
	public String getMapData(String key) {
		Map<String, String> mapData = readTestData("TestDataSheet").get("TestData");

		String value = mapData.get(key);
		if (value == null) {
		}
		return value;
	}

	/*** This method writes data for desired key ***/
	public void setCellValue(String sheetName, String key, String value) throws IOException {
		// load excel
		Sheet sheet = getExcelSheet(sheetName);
		Row row = null;
		int rowIndex = 0;
		// get row number for key
		for (int i = 0; i <= sheet.getLastRowNum(); i++) {
			try {
				row = sheet.getRow(i);
				Cell col = row.getCell(0);
				if (col.getStringCellValue().equals(key.substring(key.indexOf('_') + 1))) {
					rowIndex = i;
					break;
				}
			} catch (Exception e) {
				log.info("Space found for entering key");
			}
		}
		Row rowCreated = sheet.createRow(rowIndex);
		// Creates Column object
		Cell colCreated0 = rowCreated.createCell(0);
		// Writes data
		colCreated0.setCellValue(key.substring(key.indexOf('_') + 1));
		Cell colCreated1 = rowCreated.createCell(1);
		colCreated1.setCellValue(value);
		// Saves data
		setExcelData();
	}

	/*** This method writes data in desired row and column ***/
	public void setNewCellValue(String sheetName, String key, String value) {
		log.info("Adding new Key " + key + " Value " + value);
		// load excel
		Sheet sheet = getExcelSheet(sheetName);
		int rowValue = sheet.getLastRowNum() + 1;
		// Creates row object
		Row row = sheet.createRow(rowValue);
		// Creates Column object
		Cell col = row.createCell(0);
		// Writes data
		col.setCellValue(key);
		// Saves data
		// Creates Column object
		Cell col1 = row.createCell(1);
		// Writes data
		col1.setCellValue(value);
		// Saves data
		setExcelData();
	}
}

package utility;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;

public class ExcelUtils {
	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;

	public static void setExcelFile(String Path, String SheetName) throws Exception {
		try {
			// Open the Excel file
			FileInputStream ExcelFile = new FileInputStream(Path);
			// Access the required test data sheet
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
		} catch (Exception e) {
			throw (e);
		}
	}

	public static Object[][] getTableArray(String FilePath, String SheetName) throws Exception {
		String[][] tabArray = null;

		try {
			FileInputStream ExcelFile = new FileInputStream(FilePath);
			// Access the required test data sheet
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int startRow = 1;
			int startCol = 0;
			int ci, cj;
			int totalRows = ExcelWSheet.getLastRowNum();
			int totalCols = ExcelWSheet.getRow(0).getLastCellNum();
			tabArray = new String[totalRows][totalCols];
			ci = 0;

			for (int i = startRow; i <= totalRows; i++, ci++) {
				cj = 0;
				for (int j = startCol; j < totalCols; j++, cj++) {
					tabArray[ci][cj] = getCellData(i, j);
					System.out.println(tabArray[ci][cj]);
				}
			}
		}

		catch (FileNotFoundException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		}

		catch (IOException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		}
		return (tabArray);
	}

	public static String getCellData(int RowNum, int ColNum) throws Exception {
		try {
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			if (Cell.getCellType() == CellType.BLANK) {
				return "";
			} else {
				String CellData = Cell.getStringCellValue();
				return CellData;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw (e);
		}
	}

	public static List<Integer> getRowContains(String sTestCaseName, int iTestCaseNameColNum) throws Exception {
		List <Integer> listRow = new ArrayList<Integer>();
		try {
			int rowCount = ExcelUtils.getRowUsed();
			for (int i = 1; i <= rowCount; i++) {
				if (ExcelUtils.getCellData(i, iTestCaseNameColNum).equalsIgnoreCase(sTestCaseName)) {
					listRow.add(i);
				}
				else {
					continue;
				}
			}
			return listRow;
		} catch (Exception e) {
			throw (e);
		}
	}

	public static int getRowUsed() throws Exception {
		try {
			int RowCount = ExcelWSheet.getLastRowNum();
			return RowCount;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			throw (e);
		}
	}

	public static Object[][] getTableArray(String FilePath, String SheetName, String sTestCaseName, int iTestCaseNameColNum) throws Exception {
		String[][] tabArray = null;

		try {
			FileInputStream ExcelFile = new FileInputStream(FilePath);
			// Access the required test data sheet
			ExcelWBook = new XSSFWorkbook(ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);

			int startCol = 1;
			int ci = 0;
			
			List<Integer> listTestCaseRow = getRowContains(sTestCaseName, iTestCaseNameColNum);
			int totalRows = listTestCaseRow.size();
			int totalCols = ExcelWSheet.getRow(listTestCaseRow.get(0)).getLastCellNum();
			tabArray = new String[totalRows][totalCols-1];
			System.out.println("----------Data Test----------");
			for (int i = 0; i < totalRows; i++, ci++) {
				int cj = 0;
				for (int j = startCol; j < totalCols; j++, cj++) {
					tabArray[ci][cj] = getCellData(listTestCaseRow.get(i), j);
					System.out.println(tabArray[ci][cj]);
				}
			}
			System.out.println("-----------------------------");
		} catch (FileNotFoundException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Could not read the Excel sheet");
			e.printStackTrace();
		}
		return (tabArray);
	}
}

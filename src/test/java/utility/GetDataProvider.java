package utility;

import org.testng.annotations.DataProvider;

public class GetDataProvider {
	@DataProvider(name = "email-with-content")
	public static Object[][] emailWithContent() throws Exception {
//		ExcelUtils.setExcelFile("./test-data/TestData.xlsx", "Sheet1");
		Object[][] testData = ExcelUtils.getTableArray("./test-data/TestData.xlsx", "Sheet1",
				"sendEmailWithContent", 0);
		return testData;
	}

	@DataProvider(name = "email-without-content")
	public static Object[][] emailWithoutContent() throws Exception {
//		ExcelUtils.setExcelFile("./test-data/TestData.xlsx", "Sheet1");
		Object[][] testData = ExcelUtils.getTableArray("./test-data/TestData.xlsx", "Sheet1",
				"sendEmailWithoutContent", 0);
		return testData;
	}
}

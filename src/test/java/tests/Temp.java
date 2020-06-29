package tests;

import utility.ExcelUtils;

public class Temp {
	public static void main(String[] args) throws Exception {
		Object[][] a2 = ExcelUtils.getTableArray("./src/test/java/testData/TestData.xlsx", "Sheet1", "sendEmailWithContent", 0);
		System.out.println(a2);
	}

}

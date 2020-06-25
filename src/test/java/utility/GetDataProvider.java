package utility;

import org.testng.annotations.DataProvider;

public class GetDataProvider {
  @DataProvider(name="email-to-send")
  public static Object[][] dataProviderMethod() throws Exception{
	  Object[][] testData = ExcelUtils.getTableArray("./src/test/java/testData/TestData.xlsx", "Sheet1");
	  return testData;
  }
}

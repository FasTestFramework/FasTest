## Introduction
The FasTest Automation project is designed to test the Restful API's by using Rest Assured and TestNG.
### Uses:
The benefits of this project are to reduce the code complexity for testing and to shift the user focus entirely on testing scenarios.
### Functional Flow:
In order to use this project, we just have to edit the test data excel sheet and provide it as an input. The output is also produced in the form of excel files.

In the input excel files, the following data must be provided:
+ Test Case description
+ Headers JSON Path(Path of JSON files containing headers)
+ Path/Request param
+ Input JSON path(Path of JSON file for Payload)
+ Expected Response
+ Expected Http status

__Note:__ Leave excel cells blank if any field is not required.

The requested URL, the excel file name and the sheet names of excel that you want to hit are to be given in the application.properties file.

The requested URL provided inside the application.properties is appended with the request type using the `|` symbol. Example-`/peripherals/Fedexoffice/v1/printjsonreceipt|post`.

When we run the application, it will take the inputs from the excel file(s) and the requested URL from application properties. The output will be produced in the form of excel sheets containing the data (Pass/Fail) of test cases.

__Note:__ The output excel files have date and time appended with their name.

### Technical Flow:
We have to give the path of excel file, name of excel file and sheet names in excel files containing our input data in the application.properties as values for keys `fastest.inputFolderPath`, `fastest.excelSheetName`, `fastest.testClass` respectively.

__Note:__ The name of Sheets inside Excel is the same as the Test class names.

If we want to give multiple Excel files, then they should be separated in application.properties using `|` symbol. Example- `fastest.excelSheetName: Receipt Test Case1.xlsx|Receipt Test Case2.xlsx`. 

And for multiple sheets, we give names separated through `,` for same excel files and through `|` for different excel files. Example- `ClaimTest,ReceiptTest|BarcodeTest,ScannerTest,ReleaseTest` (in this example the first 2 tests are from excel1 and next 3 are from excel2)

When we run the program, each excel file is run one at a time. Then according to the test classes given in the application.properties, we set the test classes that need to be run inside the TestNG object and this TestNG object is run.

All the test classes are extended from a parent class named as `AutomationAbstractTests`. The URL that is initially given in the application.properties  is passed in its constructor which is called from `super()` of the child class.

Inside the Parent class's constructor, we make a list of a DTO named as `AutomationInputDTO` that extracts all the input data from the excel sheets using `readInputExcelFile` method of `AutomationExcelUtility` Class.

Using this extracted data we set JSON objects we just added in `AutomationInputDTO` using `setInputJsonObjects` method of `AutomationJsonInputOutputUtility` class that would be passed as payload.

Then we update the ClaimId inside this JSON object needed for hitting URLs
using the `updateClaimId` method of `PeripheralClaimsUtility` class. But this method is skipped in case of Claim URL test.

Then we take the Header JSON path given in excel to extract the headers with their key as `Headers` object using `getValueFromJson` method of `AutomationJsonToValueUtility` class. The baseURI is also fetched from application.properties and stored in a string.

Then according to the given request type(GET, POST, PUT, DELETE) and the type of payload/path parameter/request parameters, the requested URL is hit and response is saved inside `PeripheralInputDTO`.

After this, we come to the overridden method of the test class where we have to write validations and according to their results, we determine whether the test case passed or failed. 

ValidationExample- 
```java
@Override
public void performValidations() {
	peripheralInputDTOList.forEach(peripheralInputDTO -> {
		peripheralInputDTO.setTestCaseResult(peripheralInputDTO.getActualHttpStatus()
			.equals(peripheralInputDTO.getExpectedHttpStatus())
			&& peripheralInputDTO.getExpectedOutput().equalsIgnoreCase(peripheralInputDTO.getActualOutput())
				? PeripheralAutomationConstants.TEST_CASE_RESULT_PASS
				: PeripheralAutomationConstants.TEST_CASE_RESULT_FAIL);
	});
}
```

After these validations, we save them inside an excel file using  `AutomationAbstractTests` class's method named `publishResults`. This method has @AfterClass annotation and calls the `writeOutputExcelFile` method of `PeripheralsExcelUtility` class that writes data in the output excel file.

The result data is saved in the database using Hibernate through `convertAutomationDtoToAutomationOutputModel` method of `AutomationInputDtoToAutomationModelMapper` class.

At the end `cleanup` method of `AutomationAbstractTests` writes the final excel using `saveDataToExcel` method of `AutomationExcelUtility` class and releases the claimId using `releasePeripheralServer` method of `AutomationClaimsUtility` class. `cleanup` is annotated with @AfterTest annotation.
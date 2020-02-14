DROP TABLE IF EXISTS PERIPHERAL_TEST_RESULTS ;

DROP TABLE IF EXISTS PERIPHERAL_SPRINT_INFO;

DROP TABLE IF EXISTS PeripheralPIInfo;

-----------PERIPHERAL_TEST_RESULTS TABLES-----------

CREATE TABLE PERIPHERAL_TEST_RESULTS (
Execution_Date_Time datetime2 NOT NULL,
Input_Excel_Name varchar(255) NOT NULL,
Input_Sheet_Name varchar(255) NOT NULL,
EXECUTION_DATE date NULL,
Output_Excel_Name varchar(255) NULL,
Output_Sheet_Name varchar(255) NULL,
TESTED_BY varchar(255) NULL,
Total_Executed_Cases int NULL,
Total_Fail_Test_Cases int NULL,
Total_Pass_Test_Cases int NULL,
PRIMARY KEY (
Execution_Date_Time ,
Input_Excel_Name ,
Input_Sheet_Name 
));



-----------PERIPHERAL_SPRINT_INFO TABLES-----------

CREATE TABLE PERIPHERAL_SPRINT_INFO(
	PI varchar(20) NOT NULL,
	SPRINT varchar(20) NOT NULL,
	PROJECT_NAME varchar(200) NULL,
	SPRINT_START_DATE date NOT NULL,
	SPRINT_END_DATE date NOT NULL,
 CONSTRAINT PK_PERIPHERAL_SPRINT_INFO PRIMARY KEY  
(
	PI ,
	SPRINT 
));
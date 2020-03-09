@echo off
echo %cd%
PushD "%~dp0"
echo *****************************************************************************
echo current directory %~dp0
set jdkPath=%~dp0\jdk1.8.0_211\bin
set InstallerJARName=fastest-automation-1.0.0-SNAPSHOT.jar
echo JDK path: %jdkPath%
set path=%jdkPath%
set configPropertyPath=%~dp0\conf\fastest.properties
echo configuration property file path: %configPropertyPath%
echo ******************************************************************************
echo ******************************************************************************
echo ******************************************************************************
echo ***************Identifying Fastest jar****************************************
PushD lib
FOR /f "usebackq tokens=* delims=" %%I in (`dir *fastest-automation*.jar /b /a-d`) DO (
 	SET InstallerJARName=%%I
)
echo Fastest jar identified: %InstallerJARName%
PopD
echo ************ STARTING AUTOMATION FRAMEWORK ***********************************
java -jar lib\%InstallerJARName% %configPropertyPath%
PopD
@echo off

set folderName=peripheral-service
set /p response="Do you wish to customize folderName? y/n "
if "%response%"=="y" (
set /p folderName="Enter desired folderName : "
)

IF EXIST %folderName% echo "DELETING EXISTING FOLDER..." & RMDIR /S /Q %folderName%

echo ---------------------
echo BEGINNING TO CLONE...
echo ---------------------

FOR /F "tokens=1* delims=:" %%G IN (./input/application.properties) DO (
if "%%G"=="id"         SET USER_ID=%%H
if "%%G"=="password"   SET PASSWORD=%%H
if "%%G"=="clone-url"  SET CLONE_URL=%%H
if "%%G"=="branch"     SET BRANCH=%%H
)

git clone --single-branch --branch %BRANCH% https://%USER_ID%:%PASSWORD%@%CLONE_URL% %folderName%

cd %folderName%
set targetfoldername=%cd%\target
call mvn clean install -o -nsu -DskipTests -U -s  %userprofile%/.m2/settings.xml

cd ..
cd peripheral-server-installer
cd lib
echo %cd%

set libfolder = %cd%
echo libfolder is %libfolder%
echo target is %targetfoldername%

for /f "tokens=*" %%a in ('dir /b/s %targetfoldername%\*SNAPSHOT.jar') do copy "%%a" %libfolder%

cd ..
cd bin
call installPeripheralServer.bat D:/peripheral-server
cd ..
cd ..
set inputpath=%cd%/input/application.properties
java -cp  lib/rt-peripherals-automation-1.0.0-SNAPSHOT.jar com.fedex.peripherals.RTPeripheralsAutomationApplication %inputpath%
RMDIR /S /Q test-output

@echo OFF
SETLOCAL EnableDelayedExpansion

PushD "%~dp0"

SET logFileName=..\logs\peripheralInstaller.log
SET STRING_TO_REPLACE_ARGUMENTS=arguments
SET STRING_TO_REPLACE_LOGS=logpath
SET STRING_TO_REPLACE_ENVIRONMENT_VARIABLE=env
SET ENVRIONMENT_VARIABLE_NAME=
SET peripheralServerjarPath=..\lib
SET peripheralServerjar=
SET InstallerJARName=
SET INSTALLATION_LOCATION_INPUT=%1
SET LAUNCH_DIRECTORY= %~dp0

rem Config XML path *******************************************************************
SET INTIALLER_INITIALIZATION_CONFIG_XML=..\config\peripheralInitializationInstaller.xml
SET INTIALLER_CONFIG_XML=..\config\peripheralInstaller.xml
SET INSTALLER_CONFIG_PROPERTY=..\config\peripheralServerInstaller.properties
SET PERIPHERAL_SERVER_INSTALLER_XML=..\serviceInstaller\peripheral-server
SET PERIPHERAL_SERVER_INSTALLER_XML_NAME=peripheral-server
rem Config XML path *******************************************************************

echo **************************************************************>>!logFileName!
echo *************                                     ************>>!logFileName!
echo ************* Running peripheral server installer ************>>!logFileName!
echo *************                                     ************>>!logFileName!
echo **************************************************************>>!logFileName!

echo **************************************************************>>!logFileName!
echo *************       Get Installation Path         ************>>!logFileName!
echo **************************************************************>>!logFileName!

rem this operation checks if installation path has been provided via command line, if not get value of installation path from property file
For /F "tokens=1* delims==" %%A IN (!INSTALLER_CONFIG_PROPERTY!) DO (
    IF "%%A"=="peripheral.installer.installationLocation" IF [%1] == [] SET INSTALLATION_LOCATION_INPUT=%%B
    rem get enviornment variable name from property file
    IF "%%A"=="peripheral.environmentvariablename.InstalledLocation" SET ENVRIONMENT_VARIABLE_NAME=%%B
)

echo **************************************************************>>!logFileName!
echo ************* Installation Folder:%INSTALLATION_LOCATION_INPUT% **>>!logFileName!
echo **************************************************************>>!logFileName!

rem identifyPeripheralServerInstallerJAR start--------------
:identifyPeripheralServerInstallerJAR

pushd ..
pushd lib
SET PERIPHERAL_SERVER_INSTALLER_JAR=%CD%
echo **************************************************************>>!logFileName!
echo **** Identify Peripheral Server Installer Artifacts : %PERIPHERAL_SERVER_INSTALLER_JAR%**********>>!logFileName!
echo **************************************************************>>!logFileName!

FOR /f "usebackq tokens=* delims=" %%I in (`dir *installer*.jar /b /a-d`) DO (
	SET InstallerJARName=%%I
)

echo **************************************************************>>!logFileName!
echo **** Installer Jar : !InstallerJARName!  **>>!logFileName!
echo **************************************************************>>!logFileName!
rem identifyPeripheralServerInstallerJAR end--------------

echo **************************************************************>>!logFileName!
echo ***  Identify to trigger full build or incremental build  ***>>!logFileName!
echo **************************************************************>>!logFileName!

rem java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=y,address=8085 -Dserver.port=8085 -classpath %PERIPHERAL_SERVER_INSTALLER_JAR%\!InstallerJARName! com.fedex.peripherals.installer.PeripheralServerInstaller "!INTIALLER_INITIALIZATION_CONFIG_XML!" "!INSTALLER_CONFIG_PROPERTY!">>!logFileName! 2>&1

java -classpath %PERIPHERAL_SERVER_INSTALLER_JAR%\!InstallerJARName! com.fedex.peripherals.installer.PeripheralServerInstaller "!INTIALLER_INITIALIZATION_CONFIG_XML!" "!INSTALLER_CONFIG_PROPERTY!" "!INSTALLATION_LOCATION_INPUT!">>!logFileName! 2>&1

rem checking if errorlevel is 0 or not start----------
echo **************************************************************>>!logFileName!
echo **** Error level recieved after calling peripheralInitializationInstaller.xml is: !errorlevel!>>!logFileName!
echo **************************************************************>>!logFileName!
rem IF NOT "[!errorlevel!]"=="[0]" (
rem Exit /b 1
rem )
echo errorlevel recieved !errorlevel!
IF "[!errorlevel!]"=="[2]" (
rem Error Level 2 means it is a full installation. Call the relevant actions for full installation
    echo **************************************************************>>!logFileName!
    echo Starting full build installation on location: !INSTALLATION_LOCATION_INPUT!>>!logFileName!
	echo **************************************************************>>!logFileName!
	goto :createEnvironmentvariable>>!logFileName!
) Else IF "[!errorlevel!]"=="[3]" (
rem Error Level 3 means it is a incremental installation. Call the relevant actions for full installation
	call SET INSTALLATION_LOCATION_INPUT=!%ENVRIONMENT_VARIABLE_NAME%!
	echo **************************************************************>>!logFileName!
    echo Starting incremental build installation on location: !INSTALLATION_LOCATION_INPUT!>>!logFileName!
	echo **************************************************************>>!logFileName!
    goto :identifyPeripheralServerJAR>>!logFileName!
) ELSE (
    Exit 1
)
rem checking if errorlevel is 0 or not end------------

:createEnvironmentvariable
rem create enviornment variable for peripheral server installed location -----

echo **************************************************************>>!logFileName!
echo ***  Create Enviornment Variable for Peripheral Server Installed Location  ***>>!logFileName!
echo **************************************************************>>!logFileName!
SET !ENVRIONMENT_VARIABLE_NAME! !INSTALLATION_LOCATION_INPUT!
SETX -m !ENVRIONMENT_VARIABLE_NAME! !INSTALLATION_LOCATION_INPUT! >>!logFileName!

goto :identifyPeripheralServerJAR

rem create enviornment variable for peripheral server installed location -----

rem identifyPeripheralServerJAR start--------------
:identifyPeripheralServerJAR

echo **************************************************************>>!logFileName!
echo *******   Identify Peripheral Server Artifacts   *************>>!logFileName!
echo ******identifyPeripheralServerJAR********************************************************>>!logFileName!
rem >>!logFileName!
FOR /f "usebackq tokens=* delims=" %%I in (`dir *rtl-peripherals*.jar /b /a-d`) DO (
	SET peripheralServerjar=%%~nxI
)

echo **************************************************************>>!logFileName!
echo **** Peripheral Server Jar : !peripheralServerjar!  *************>>!logFileName!
echo **************************************************************>>!logFileName!

goto :updatePeripheralServerInstallerXML
rem identifyPeripheralServerInstallerJAR start--------------

rem updatePeripheralServerInstallerXML start--------------
:updatePeripheralServerInstallerXML

echo **************************************************************>>!logFileName!
echo *******   Update Peripheral Server Service File  *************>>!logFileName!
echo **************************************************************>>!logFileName!
rem deleting the peripheral-server_new.xml file if it already exists
IF exist "!PERIPHERAL_SERVER_INSTALLER_XML!_new.xml" (
echo **** Deleting !PERIPHERAL_SERVER_INSTALLER_XML!_new.xml file ***** >>!logFileName!
                                del "!PERIPHERAL_SERVER_INSTALLER_XML!_new.xml"
)
FOR /f "usebackq tokens=* delims=" %%I in ("!PERIPHERAL_SERVER_INSTALLER_XML!.xml") DO (
SET TOKEN=%%I
IF NOT "[!TOKEN:%STRING_TO_REPLACE_ARGUMENTS%=!]"=="[!TOKEN!]" (
	SET TOKEN=    ^<arguments^>-jar "%INSTALLATION_LOCATION_INPUT%\lib\!peripheralServerjar!"^</arguments^>
)
rem update log path for peripheral server
IF NOT "[!TOKEN:%STRING_TO_REPLACE_LOGS%=!]"=="[!TOKEN!]" (
	SET TOKEN=    ^<logpath^>%INSTALLATION_LOCATION_INPUT%\logs^</logpath^>
)
rem update environement variable for peripheral server
IF NOT "[!TOKEN:%STRING_TO_REPLACE_ENVIRONMENT_VARIABLE%=!]"=="[!TOKEN!]" (
	SET TOKEN=    ^<env name="%ENVRIONMENT_VARIABLE_NAME%" value="%INSTALLATION_LOCATION_INPUT%"/^>
)                              
echo !TOKEN!>>"!PERIPHERAL_SERVER_INSTALLER_XML!_new.xml"
)
ren "!PERIPHERAL_SERVER_INSTALLER_XML!.xml" "!PERIPHERAL_SERVER_INSTALLER_XML_NAME!_old.xml">>!logFileName! 2>&1
ren "!PERIPHERAL_SERVER_INSTALLER_XML!_new.xml" "!PERIPHERAL_SERVER_INSTALLER_XML_NAME!.xml">>!logFileName! 2>&1
rem deleting the peripheral-server_old.xml file if it already exists
IF exist "!PERIPHERAL_SERVER_INSTALLER_XML!_old.xml" (
	del "!PERIPHERAL_SERVER_INSTALLER_XML!_old.xml"
)              

goto :startInstallation
rem updatePeripheralServerInstallerXML end-----------

:startInstallation
echo ************* Start Peripheral Server Installation ************ >>!logFileName!
rem echo %PERIPHERAL_SERVER_INSTALLER_JAR%\!InstallerJARName! com.fedex.peripherals.installer.PeripheralServerInstaller "!INTIALLER_CONFIG_XML!" "!INSTALLER_CONFIG_PROPERTY!" "%INSTALLATION_LOCATION_INPUT%">>!logFileName! 2>&1
rem java -Dserver.port=8085 -classpath %PERIPHERAL_SERVER_INSTALLER_JAR%\!InstallerJARName! com.fedex.peripherals.installer.PeripheralServerInstaller "!INTIALLER_CONFIG_XML!" "!INSTALLER_CONFIG_PROPERTY!" "%INSTALLATION_LOCATION_INPUT%">>!logFileName! 2>&1
java -classpath %PERIPHERAL_SERVER_INSTALLER_JAR%\!InstallerJARName! com.fedex.peripherals.installer.PeripheralServerInstaller "!INTIALLER_CONFIG_XML!" "!INSTALLER_CONFIG_PROPERTY!" "%INSTALLATION_LOCATION_INPUT%">>!logFileName! 2>&1

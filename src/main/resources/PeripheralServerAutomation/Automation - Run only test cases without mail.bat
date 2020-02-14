@echo off
set inputpath=%cd%/input/application.properties
java -cp  lib/rt-peripherals-automation-1.0.0-SNAPSHOT.jar com.fedex.peripherals.RTPeripheralsAutomationApplication %inputpath% --skipUi --skipEmail
RMDIR /S /Q test-output
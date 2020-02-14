@echo off
set inputpath=%cd%/input/application.properties
java -cp  lib/rt-peripherals-automation-1.0.0-SNAPSHOT.jar com.fedex.peripherals.RTPeripheralsAutomationApplication %inputpath%
RMDIR /S /Q test-output
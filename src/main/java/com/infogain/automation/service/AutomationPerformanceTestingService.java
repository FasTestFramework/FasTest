package com.infogain.automation.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
import org.apache.jmeter.assertions.DurationAssertion;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.config.CSVDataSet;
import org.apache.jmeter.config.gui.ArgumentsPanel;
import org.apache.jmeter.control.LoopController;
import org.apache.jmeter.control.gui.TestPlanGui;
import org.apache.jmeter.engine.StandardJMeterEngine;
import org.apache.jmeter.protocol.http.control.Header;
import org.apache.jmeter.protocol.http.control.HeaderManager;
import org.apache.jmeter.protocol.http.control.gui.HttpTestSampleGui;
import org.apache.jmeter.protocol.http.gui.HeaderPanel;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.report.config.ConfigurationException;
import org.apache.jmeter.report.dashboard.GenerationException;
import org.apache.jmeter.report.dashboard.ReportGenerator;
import org.apache.jmeter.reporters.ResultCollector;
import org.apache.jmeter.reporters.Summariser;
import org.apache.jmeter.save.SaveService;
import org.apache.jmeter.testbeans.gui.TestBeanGUI;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jmeter.testelement.TestPlan;
import org.apache.jmeter.threads.SetupThreadGroup;
import org.apache.jmeter.threads.gui.ThreadGroupGui;
import org.apache.jmeter.util.JMeterUtils;
import org.apache.jorphan.collections.HashTree;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infogain.automation.dto.AutomationPerformanceTestingAssertions;
import com.infogain.automation.dto.AutomationPerformanceTestingCsvConfigDto;
import com.infogain.automation.dto.AutomationPerformanceTestingDurationAssertion;
import com.infogain.automation.dto.AutomationPerformanceTestingHttpHandlerDto;
import com.infogain.automation.dto.AutomationPerformanceTestingResultsHttpSample;
import com.infogain.automation.dto.AutomationPerformanceTestingResultsTestResults;
import com.infogain.automation.dto.AutomationPerformanceTestingResultsTestResultsDto;
import com.infogain.automation.dto.AutomationPerformanceTestingTest;
import com.infogain.automation.dto.AutomationPerformanceTestingTestPlanDto;
import com.infogain.automation.dto.AutomationPerformanceTestingThreadGroupDto;
import com.infogain.automation.dto.AutomationResponse;
import com.infogain.automation.properties.AutomationProperties;


/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class creates a Performance test
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Mar 3, 2020
 */
@Service
public class AutomationPerformanceTestingService {
    CSVDataSet csvDataSet;
    HTTPSamplerProxy httpSamplerProxy;
    LoopController loopController;
    SetupThreadGroup setupThreadGroup;
    TestPlan jmeterTestPlan;
    HashTree testHashTree;
    Summariser summariser;
    String dateTimesuffix;
    @Autowired
    AutomationProperties automationProperties;

    public AutomationResponse<AutomationPerformanceTestingResultsTestResultsDto> createAndRunPerformanceTest(
                    AutomationPerformanceTestingTest automationPerformanceTestingTest) {
    	dateTimesuffix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss a"));
        AutomationPerformanceTestingCsvConfigDto csvConfig = automationPerformanceTestingTest.getCsvConfig();
        AutomationPerformanceTestingHttpHandlerDto httpHandler = automationPerformanceTestingTest.getHttpHandler();
        AutomationPerformanceTestingTestPlanDto testPlan = automationPerformanceTestingTest.getTestPlan();
        AutomationPerformanceTestingThreadGroupDto threadGroup = automationPerformanceTestingTest.getThreadGroup();
        // create csv config
        configureTestEnvironment();
        createCsvConfig(csvConfig);
        createHttpHandler(httpHandler);
        createLoopController();
        createThreadGroup(threadGroup);
        createTestPlan(testPlan);
        createTestHashTree(testPlan);
        createAndAddAssertions(automationPerformanceTestingTest.getAssertions());
        runTestCase(testHashTree);
        AutomationPerformanceTestingResultsTestResults automationPerformanceTestingResultsTestResults = null;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(AutomationPerformanceTestingResultsTestResults.class);
            Unmarshaller jaxbMarshaller = jaxbContext.createUnmarshaller();
            automationPerformanceTestingResultsTestResults =
                            (AutomationPerformanceTestingResultsTestResults) JAXBIntrospector.getValue(jaxbMarshaller
                                            .unmarshal(new FileInputStream(new File(automationProperties.getProperty("fastest.jmeterxmlreportsfilepath").concat("/xmlResultreport_").concat(dateTimesuffix).concat(".xml")))));
        } catch (FileNotFoundException | JAXBException e) {
            e.printStackTrace();
        }
        AutomationPerformanceTestingResultsTestResultsDto output =
                        new AutomationPerformanceTestingResultsTestResultsDto();
        if (automationPerformanceTestingTest.getDeatiledResponse().booleanValue()) {
            output.setDetailedResults(automationPerformanceTestingResultsTestResults);
        }
        output.setSummary(summariser.results);
        if (automationPerformanceTestingResultsTestResults != null) {
            generateHtmlReport(testPlan, automationPerformanceTestingResultsTestResults, output);
        }
        return AutomationResponse.success(output);


    }

    private void createAndAddAssertions(AutomationPerformanceTestingAssertions automationPerformanceTestingAssertions) {
        if (automationPerformanceTestingAssertions != null) {
            AutomationPerformanceTestingDurationAssertion automationAurationAssertion =
                            automationPerformanceTestingAssertions.getDurationAssertion();
            DurationAssertion durationAssertion = new DurationAssertion();
            durationAssertion.setName(automationAurationAssertion.getName());
            durationAssertion.setAllowedDuration(automationAurationAssertion.getAllowedDuration());
            testHashTree.add(httpSamplerProxy, durationAssertion);
        }


    }

    /**
     * @param testPlan
     * @param automationPerformanceTestingResultsTestResults
     * @param output
     */
    private void generateHtmlReport(AutomationPerformanceTestingTestPlanDto testPlan,
                    AutomationPerformanceTestingResultsTestResults automationPerformanceTestingResultsTestResults,
                    AutomationPerformanceTestingResultsTestResultsDto output) {

        try {


            File csvFile = new File(automationProperties.getProperty("fastest.jmetercsvreportsfilepath").concat("/csvResultreport_").concat(dateTimesuffix).concat(".csv"));
            FileWriter myWriter = new FileWriter(csvFile);
            AutomationPerformanceTestingResultsHttpSample[] httpSamples =
                            automationPerformanceTestingResultsTestResults.getHttpSample();
            myWriter.write("timeStamp,elapsed,label,responseCode,responseMessage,threadName,dataType,success,failureMessage,bytes,sentBytes,grpThreads,allThreads,URL,Latency,IdleTime,Connect\n");
            for (int i = 0; i < httpSamples.length; i++) {
                myWriter.write(httpSamples[i].getTimeStamp() + "," + httpSamples[i].getElapsed() + ","
                                + httpSamples[i].getLabel() + "," + httpSamples[i].getResponseCode() + ","
                                + httpSamples[i].getResponseMessage() + "," + httpSamples[i].getThreadName() + ","
                                + httpSamples[i].getDataType() + "," + httpSamples[i].getSuccess() + "," + " " + ","
                                + httpSamples[i].getBytes() + "," + httpSamples[i].getSentBytes() + ","
                                + httpSamples[i].getGrpThreads() + "," + httpSamples[i].getAllThreads() + ","
                                + httpSamples[i].getRequestUrl() + "," + httpSamples[i].getLatency() + ","
                                + httpSamples[i].getIdleTime() + "," + httpSamples[i].getConnect() + "\n");
            }
            myWriter.close();


            ReportGenerator rg = new ReportGenerator(csvFile.getAbsolutePath(), null,
                            automationProperties.getProperty("fastest.jmeterreportpropertiespath"));
            rg.generate();
            
            File sourceReportHtmlDirectory = new File(
                            automationProperties.getProperty("fastest.jmeterhome").concat("/bin/report-output/"));
            File destinationReportHtmlDirectory = new File(automationProperties.getProperty("fastest.jmeterhtmlreportfilepath")
                    .concat("/htmlreport_".concat(dateTimesuffix)));
            java.nio.file.Files.createDirectories(Paths.get(destinationReportHtmlDirectory.getAbsolutePath()));
            

            if (sourceReportHtmlDirectory.isDirectory()) {
                FileUtils.copyDirectory(sourceReportHtmlDirectory, destinationReportHtmlDirectory);
                FileUtils.cleanDirectory(sourceReportHtmlDirectory);
            }


            Properties reportProps = new Properties();
            reportProps.load(new FileInputStream(
                            automationProperties.getProperty("fastest.jmeterreportpropertiespath")));

            File sourceReportJsonDirectory = new File(
                            reportProps.getProperty("jmeter.reportgenerator.exporter.json.property.output_dir"));
            File destinationReportJsonDirectory = new File(automationProperties.getProperty("fastest.jmeterjsonreportsfilepath")
                    .concat("/jsonreport_".concat(dateTimesuffix)));
            java.nio.file.Files.createDirectories(Paths.get(destinationReportJsonDirectory.getAbsolutePath()));
            

            if (sourceReportJsonDirectory.isDirectory()) {

                FileUtils.copyDirectory(sourceReportJsonDirectory, destinationReportJsonDirectory);
                FileUtils.cleanDirectory(sourceReportJsonDirectory);

            }
            output.setHtmlReportPath(destinationReportHtmlDirectory.getAbsolutePath().concat("/index.html"));
            JSONParser jsonParser = new JSONParser();
            FileReader reader = new FileReader(destinationReportJsonDirectory.getAbsolutePath()
                            .concat("/statistics.json"));
            Object obj = jsonParser.parse(reader);
            // Read JSON file
            output.setResultStats((JSONObject) obj);
        } catch (ConfigurationException | GenerationException | IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void createTestHashTree(AutomationPerformanceTestingTestPlanDto testPlan) {
        testHashTree = new HashTree();
        testHashTree.add(jmeterTestPlan);

        HashTree groupTree = testHashTree.add(jmeterTestPlan, setupThreadGroup);
        groupTree.add(httpSamplerProxy, httpSamplerProxy.getHeaderManager());
        groupTree.add(csvDataSet);

        // Save this test plan as a .jmx for future reference
        try {
            SaveService.saveTree(testHashTree, new FileOutputStream(automationProperties.getProperty("fastest.jmeterjmxfilepath").concat("/jmxreport_".concat(dateTimesuffix).concat(".jmx"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Added summarizer for logging meta info
        summariser = new Summariser("summaryOfResults");
        // Collect results
        ResultCollector resultCollector = new ResultCollector(summariser);

        resultCollector.setFilename(automationProperties.getProperty("fastest.jmeterxmlreportsfilepath").concat("/xmlResultreport_".concat(dateTimesuffix).concat(".xml")));
        testHashTree.add(testHashTree.getArray()[0], resultCollector);

    }

    private void createTestPlan(AutomationPerformanceTestingTestPlanDto testPlan) {
        jmeterTestPlan = new TestPlan(testPlan.getName());
        // Adding GUI pieces for Jmeter gui
        jmeterTestPlan.setProperty(TestElement.TEST_CLASS, TestPlan.class.getName());
        jmeterTestPlan.setProperty(TestElement.GUI_CLASS, TestPlanGui.class.getName());
        jmeterTestPlan.setUserDefinedVariables((Arguments) new ArgumentsPanel().createTestElement());

    }

    private void createThreadGroup(AutomationPerformanceTestingThreadGroupDto threadGroup) {
        setupThreadGroup = new SetupThreadGroup();
        setupThreadGroup.setName(threadGroup.getThreadGroupName());
        setupThreadGroup.setNumThreads(threadGroup.getNumberOfThreads());
        setupThreadGroup.setRampUp(threadGroup.getRampUp());
        setupThreadGroup.setSamplerController(loopController);
        // Adding GUI pieces for Jmeter
        setupThreadGroup.setProperty(TestElement.TEST_CLASS, ThreadGroup.class.getName());
        setupThreadGroup.setProperty(TestElement.GUI_CLASS, ThreadGroupGui.class.getName());

    }

    private void createLoopController() {
        loopController = new LoopController();
        loopController.setLoops(LoopController.INFINITE_LOOP_COUNT);
        loopController.setFirst(true);
        loopController.initialize();

    }

    private void createHttpHandler(AutomationPerformanceTestingHttpHandlerDto httpHandler) {

        httpSamplerProxy = new HTTPSamplerProxy();
        httpSamplerProxy.setDomain(httpHandler.getDomain());
        httpSamplerProxy.setProtocol(httpHandler.getProtocol());
        httpSamplerProxy.setPath(httpHandler.getPath());
        httpSamplerProxy.setMethod(httpHandler.getHttpMethod());
        httpSamplerProxy.setName(httpHandler.getName());
        httpSamplerProxy.setPort(httpHandler.getPort());
        HeaderManager headerManager = new HeaderManager();
        HashMap<String, String> headers = httpHandler.getHeaders();
        Set<String> keySet = headers.keySet();
        for (Iterator<String> keysetIterator = keySet.iterator(); keysetIterator.hasNext();) {
            String key = (String) keysetIterator.next();
            headerManager.add(new Header(key, headers.get(key)));
        }
        headerManager.setName(JMeterUtils.getResString("header_manager_title")); // $NON-NLS-1$
        headerManager.setProperty(TestElement.TEST_CLASS, HeaderManager.class.getName());
        headerManager.setProperty(TestElement.GUI_CLASS, HeaderPanel.class.getName());
        httpSamplerProxy.setHeaderManager(headerManager);
        httpSamplerProxy.addNonEncodedArgument("body", "${body}", "");
        httpSamplerProxy.setPostBodyRaw(true);
        httpSamplerProxy.setProperty(TestElement.TEST_CLASS, HTTPSamplerProxy.class.getName());
        httpSamplerProxy.setProperty(TestElement.GUI_CLASS, HttpTestSampleGui.class.getName());

    }

    private void createCsvConfig(AutomationPerformanceTestingCsvConfigDto csvConfig) {
        if (csvConfig != null) {
            csvDataSet = new CSVDataSet();
            csvDataSet.setProperty("filename", csvConfig.getInputCsvFilename());
            csvDataSet.setComment("List of query params");
            csvDataSet.setName("MarshalQueryParams");

            csvDataSet.setProperty("delimiter", csvConfig.getDelimiter());

            csvDataSet.setProperty("variableNames", csvConfig.getVariableNames());
            csvDataSet.setProperty("recycle", csvConfig.getRecycle());// Recycle input on end of file (set to false)
            csvDataSet.setProperty("ignoreFirstLine", csvConfig.getIgnoreFirstline());// Ignore first line of file
            csvDataSet.setProperty("stopThread", csvConfig.getStopThread());// Stops thread on EOF
            csvDataSet.setProperty("shareMode", csvConfig.getShareMode());
            // csvConfig.
            csvDataSet.setProperty(TestElement.TEST_CLASS, CSVDataSet.class.getName());
            csvDataSet.setProperty(TestElement.GUI_CLASS, TestBeanGUI.class.getName());
        }
    }

    public void configureTestEnvironment() {
        JMeterUtils.setJMeterHome(automationProperties.getProperty("fastest.jmeterhome"));

        // import the jmeter properties, as is provided
        JMeterUtils.loadJMeterProperties(automationProperties.getProperty("fastest.jmeterpropertiespath"));
        // Set locale
        JMeterUtils.initLocale();

    }

    public void runTestCase(HashTree hashTree) {
        // Create the Jmeter engine to be used
        StandardJMeterEngine jEngine = new StandardJMeterEngine();

        jEngine.configure(hashTree);

        jEngine.run();
    }

}

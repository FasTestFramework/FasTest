package com.infogain.automation.properties;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.infogain.automation.exception.AutomationException;
import com.infogain.automation.service.AutomationApplicationRestartService;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class is for reading properties from Peripheral Properties file externally with single
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Nov 27, 2019
 */
@Component
public class AutomationProperties {

    private static final Logger logger = LogManager.getLogger(AutomationProperties.class);
    
    public static final String SERVER_PORT = "server.port";
    public static final String DATABASE_HOST = "db-host";
    public static final String DATABASE_PORT = "db-port";
    public static final String INSTALLATION_DRIVE = "installation-drive";

    public static String propertyFilePath;
    private PropertiesConfiguration propertiesConfiguration;
    
    @Autowired
    private final AutomationApplicationRestartService automationApplicationRestartService;

    /**
     * This method reads the properties from Automation Properties file and throws Exception if any error occurred
     */
    public AutomationProperties(final AutomationApplicationRestartService automationApplicationRestartService) {
		this.automationApplicationRestartService = automationApplicationRestartService;
		try (InputStream input = StringUtils.isNotBlank(propertyFilePath) ? new FileInputStream(propertyFilePath)
                        : this.getClass().getClassLoader().getResourceAsStream("application.properties");) {
           // props = new Properties();
            propertiesConfiguration = new PropertiesConfiguration();
            propertiesConfiguration.setDelimiterParsingDisabled(true);
            propertiesConfiguration.load(input);
            //props.load(input);
        } catch (ConfigurationException | IOException ex) {
            logger.debug("Exception Occured While Reading Properties File With Path {} : {} ", propertyFilePath,
                            ExceptionUtils.getStackTrace(ex));
            throw new AutomationException(
                            "Exception Occured While Reading Properties File With Path " + propertyFilePath, ex);
        }
    }

    public String getPropertyAsString(String key) {
    	Object property = propertiesConfiguration.getProperty(key);
    	if(property instanceof String) {
    		return (String) property;
    	}
    	return null;
    }
    
    public Object getProperty(String key) {
        return propertiesConfiguration.getProperty(key);
    }

    public String getKeyNameByValue(String value) {
    	String urlParameterKey = null;
    	for (final Iterator<String> itr = propertiesConfiguration.getKeys(); itr.hasNext();) {
    		String key = itr.next();
    		List valuesList = propertiesConfiguration.getList(key);
    		if (valuesList.get(0).equals(value)) {
    			urlParameterKey = key;
    			break;
    		}
    	}
    	return urlParameterKey;
    }

    public void reloadProperties() {
    	PropertiesConfiguration newPropertiesConfiguration;
    	try (InputStream input = new FileInputStream(propertyFilePath)) {

    		newPropertiesConfiguration = new PropertiesConfiguration();
    		newPropertiesConfiguration.load(input);

    		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
    		Document doc = docBuilder.parse("D:\\fasTest\\serviceInstaller\\fastest.xml");
    		// Get the root element
    		Node staff = doc.getElementsByTagName("service").item(0);
    		NodeList nodes = staff.getChildNodes();

    		if(!propertiesConfiguration.getProperty(SERVER_PORT).equals(newPropertiesConfiguration.getProperty(SERVER_PORT))) {
    			for (int i = 0; i < nodes.getLength(); i++) {
    				Node element = nodes.item(i);
    				if ("arguments".equals(element.getNodeName())) {
    					String propertyValue = element.getTextContent();
    					element.setTextContent(propertyValue.replace(propertiesConfiguration.getProperty(SERVER_PORT).toString(), newPropertiesConfiguration.getProperty(SERVER_PORT).toString()));
    				}
    			}
    		}
    		if(!propertiesConfiguration.getProperty(INSTALLATION_DRIVE).equals(newPropertiesConfiguration.getProperty(INSTALLATION_DRIVE))) {
    			xmlPropertyUpdation(newPropertiesConfiguration, nodes, INSTALLATION_DRIVE);
    		}
    		if(!propertiesConfiguration.getProperty(DATABASE_HOST).equals(newPropertiesConfiguration.getProperty(DATABASE_HOST))) {
    			xmlPropertyUpdation(newPropertiesConfiguration, nodes, DATABASE_HOST);
    		}
    		if(!propertiesConfiguration.getProperty(DATABASE_PORT).equals(newPropertiesConfiguration.getProperty(DATABASE_PORT))) {
    			xmlPropertyUpdation(newPropertiesConfiguration, nodes, DATABASE_PORT);
    		}
    		// write the content into xml file
    		TransformerFactory transformerFactory = TransformerFactory.newInstance();
    		Transformer transformer = transformerFactory.newTransformer();
    		DOMSource source = new DOMSource(doc);
    		StreamResult result = new StreamResult(new File("D:\\fasTest\\serviceInstaller\\fastest.xml"));
    		transformer.transform(source, result);

    		propertiesConfiguration = newPropertiesConfiguration;

    		automationApplicationRestartService.restartApp();
    	} catch (ConfigurationException | IOException | ParserConfigurationException | SAXException | TransformerException ex) {
    		logger.debug("Exception Occured While Reading Properties File With Path {} : {} ", propertyFilePath,
    				ExceptionUtils.getStackTrace(ex));
    	}
    }

	/**
	 * @param newPropertiesConfiguration
	 * @param nodes
	 */
    private void xmlPropertyUpdation(PropertiesConfiguration newPropertiesConfiguration, NodeList nodes, String property) {
    	for (int i = 0; i < nodes.getLength(); i++) {
    		Node element = nodes.item(i);
    		if ("env".equals(element.getNodeName())) {
    			NamedNodeMap attributes = element.getAttributes();
    			Node item = attributes.getNamedItem("name");
    			if(item.getTextContent().equals(property)){
    				Node valueNode = attributes.getNamedItem("value");
    				valueNode.setTextContent(newPropertiesConfiguration.getProperty(property).toString());
    			}
    		}
    	}
    }
}

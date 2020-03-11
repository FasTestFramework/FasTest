package com.infogain.automation.properties;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AutomationPropertiesChangeWatcher {

    @Autowired
    AutomationProperties properties;

    private static final Logger logger = LogManager.getLogger(AutomationPropertiesChangeWatcher.class);

    @Async
    public void startWatcher() {
        logger.traceEntry();
        String propertiesFilePath = AutomationProperties.propertyFilePath;
        if (StringUtils.isNotBlank(propertiesFilePath)) {
            String propertiesDirectoryPath = propertiesFilePath.substring(0, propertiesFilePath.lastIndexOf("\\"));
            String propertiesFileName = propertiesFilePath.substring(propertiesFilePath.lastIndexOf("\\")+1);
            try (WatchService fastestPropertiesWatchService = FileSystems.getDefault().newWatchService();) {

                Path fastestPropertiesDirectoryPath = Paths.get(propertiesDirectoryPath);
                fastestPropertiesDirectoryPath.register(fastestPropertiesWatchService,
                                StandardWatchEventKinds.ENTRY_MODIFY);
                WatchKey key;
                while ((key = fastestPropertiesWatchService.take()) != null) {
                    for (WatchEvent<?> event : key.pollEvents()) {
                        final Path changedPath = (Path) event.context();
                        if (changedPath.endsWith(propertiesFileName)) {
                            logger.info("property file changed externally updating properties");
                            properties.reloadProperties();
                        }
                    }
                    key.reset();
                }
            } catch (IOException | InterruptedException e) {
                logger.debug("Exception Occured While resetting Properties File With Path {} : {} ", propertiesFilePath,
                                ExceptionUtils.getStackTrace(e));
            }
        } else {
            logger.info("Properties not being read from outside");
        }
    }

}

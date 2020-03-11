package com.infogain.automation.properties;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AutomationPropertiesChangeWatcherStarter {

    @Autowired
    AutomationPropertiesChangeWatcher watcherService;
    @PostConstruct
    public void startWatcherService()
    {
        watcherService.startWatcher();
    }
}

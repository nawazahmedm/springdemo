package com.javalearnings.securitydemo.services;

import com.javalearnings.securitydemo.controllers.MasterLookupController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.annotations.Job;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InstallmentJobServiceImpl implements InstallmentJobService {

    @Value("${job.enable.cache.refresh}")
    private Boolean enableCacheRefresh;

    private final MasterLookupController masterLookupController;

    @Override
    @Job(name = "Job to Load the Cache")
    public void executeCache() {
        if (enableCacheRefresh) {
            log.info("I am in executeCache");
            masterLookupController.refreshCache();
        }
    }

}

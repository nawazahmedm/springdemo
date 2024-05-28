package com.javalearnings.securitydemo.configs;

import com.javalearnings.securitydemo.services.InstallmentJobService;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class JobConfigManager {

    private final JobScheduler jobScheduler;

    private final InstallmentJobService installmentJobService;

    @Value("${job.frequency.cache.refresh}")
    private String jobFrequencyCacheRefresh;

    public JobConfigManager(JobScheduler jobScheduler, InstallmentJobService installmentJobService) {
        this.jobScheduler = jobScheduler;
        this.installmentJobService = installmentJobService;
    }

    @PostConstruct
    public void scheduleRecurrently() {
        jobScheduler.scheduleRecurrently("cache-refresh", jobFrequencyCacheRefresh, installmentJobService::executeCache);
    }

}

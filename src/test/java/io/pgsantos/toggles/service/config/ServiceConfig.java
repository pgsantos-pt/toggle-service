package io.pgsantos.toggles.service.config;

import io.pgsantos.toggles.service.ToggleAssignmentSearchService;
import io.pgsantos.toggles.service.ToggleAssignmentService;
import io.pgsantos.toggles.service.ToggleService;
import io.pgsantos.toggles.service.impl.ToggleAssignmentSearchServiceImpl;
import io.pgsantos.toggles.service.impl.ToggleAssignmentServiceImpl;
import io.pgsantos.toggles.service.impl.ToggleServiceImpl;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ServiceConfig {
    @Bean
    public ToggleService toggleService() {
        return new ToggleServiceImpl();
    }

    @Bean
    public ToggleAssignmentService toggleAssignmentService() {
        return new ToggleAssignmentServiceImpl();
    }

    @Bean
    public ToggleAssignmentSearchService toggleAssignmentSearchService() {
        return new ToggleAssignmentSearchServiceImpl();
    }
}
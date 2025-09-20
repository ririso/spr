package com.spr.infrastructure.mapper;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@TestConfiguration
public class TestConfig {

    @Bean
    public TestDataManager testDataManager(DataSource dataSource) {
        TestDataManager manager = new TestDataManager();
        manager.setJdbcTemplate(new JdbcTemplate(dataSource));
        return manager;
    }
}
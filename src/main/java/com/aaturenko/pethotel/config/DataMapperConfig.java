package com.aaturenko.pethotel.config;

import com.aaturenko.pethotel.dao.DataMapperRegistry;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.beans.PropertyVetoException;

@Configuration
public class DataMapperConfig {

    @Value("${spring.datasource.username}")
    private String DB_USERNAME = "root";

    @Value("${spring.datasource.password}")
    private String DB_PASSWORD = "admin";

    @Value("${spring.datasource.url}")
    private String DB_URL;

    @Bean
    public DataMapperRegistry dataMapperRegistry() throws PropertyVetoException {

        DataMapperRegistry dataMapperRegistry = new DataMapperRegistry();
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.jdbc.Driver");
        cpds.setJdbcUrl(DB_URL);
        cpds.setUser(DB_USERNAME);
        cpds.setPassword(DB_PASSWORD);
        cpds.setMinPoolSize(1);
        cpds.setMaxPoolSize(5);
        cpds.setAcquireIncrement(1);
        cpds.setAutoCommitOnClose(true);
        DataMapperRegistry.initialize(cpds, false);
        return dataMapperRegistry;
    }
}

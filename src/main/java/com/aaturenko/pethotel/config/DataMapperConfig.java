package com.aaturenko.pethotel.config;

import com.aaturenko.pethotel.dao.DBConnection;
import com.aaturenko.pethotel.dao.DataMapperRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class DataMapperConfig {
    @Bean
    public DataMapperRegistry dataMapperRegistry() throws SQLException {
        Connection conn = DBConnection.getConnection();
        conn.setAutoCommit(true);
        DataMapperRegistry.initialize(conn, false);
        return new DataMapperRegistry();
    }
}

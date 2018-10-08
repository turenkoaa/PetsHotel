package com.aaturenko.pethotel.old.dao.mapper;

import com.aaturenko.pethotel.old.entities.Entity;
import com.aaturenko.pethotel.old.entities.Sitter;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SitterMapper extends UserMapper {

    public SitterMapper(ComboPooledDataSource dbConnection, boolean useEntitiesCache) {
        super(dbConnection, useEntitiesCache);
    }

    @Override
    protected Entity doLoad(long id, ResultSet rs) throws SQLException {
        return Sitter.builder()
                .id(id)
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .email(rs.getString("email"))
                .address(rs.getString("address"))
                .active(rs.getBoolean("active"))
                .build();
    }

}
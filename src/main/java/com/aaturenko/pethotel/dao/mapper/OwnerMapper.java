package com.aaturenko.pethotel.dao.mapper;

import com.aaturenko.pethotel.entities.Entity;
import com.aaturenko.pethotel.entities.Owner;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnerMapper extends UserMapper{
    public OwnerMapper(Connection dbConnection, boolean useEntitiesCache) {
        super(dbConnection, useEntitiesCache);
    }

    @Override
    protected Entity doLoad(long id, ResultSet rs) throws SQLException {
        return Owner.builder()
                .id(id)
                .firstName(rs.getString("first_name"))
                .lastName(rs.getString("last_name"))
                .email(rs.getString("email"))
                .address(rs.getString("address"))
                .active(rs.getBoolean("active"))
                .build();
    }
}

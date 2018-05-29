package com.aaturenko.pethotel.dao.mapper;

import com.aaturenko.pethotel.entities.Entity;
import com.aaturenko.pethotel.entities.Owner;
import com.aaturenko.pethotel.entities.Request;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnerMapper extends UserMapper{

    public OwnerMapper(Connection dbConnection, boolean useEntitiesCache) {
        super(dbConnection, useEntitiesCache);
    }

    public Owner findByRequest(Request request) {
        return (Owner) findOneByCustomWhereJoin("user, request", "user.user_id = request.user_id and request.request_id = ?", request.getId());
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

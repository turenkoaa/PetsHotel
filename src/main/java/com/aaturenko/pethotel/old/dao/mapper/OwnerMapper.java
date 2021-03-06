package com.aaturenko.pethotel.old.dao.mapper;

import com.aaturenko.pethotel.old.entities.Entity;
import com.aaturenko.pethotel.old.entities.Owner;
import com.aaturenko.pethotel.old.entities.Request;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OwnerMapper extends UserMapper{

    public OwnerMapper(ComboPooledDataSource dbConnection, boolean useEntitiesCache) {
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

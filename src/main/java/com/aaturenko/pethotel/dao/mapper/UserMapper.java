package com.aaturenko.pethotel.dao.mapper;

import com.aaturenko.pethotel.dao.DataMapper;
import com.aaturenko.pethotel.entities.Entity;
import com.aaturenko.pethotel.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class UserMapper extends DataMapper {
    private static final String TABLE_NAME = "user";
    private static final String PK_COLUMN_NAME = "user_id";
    private static final String COLUMNS = "first_name, last_name, email, address, active";
    private static final String DDL = "(?, ?, ?, ?, ?, ?)";

    public UserMapper(Connection dbConnection, boolean useEntitiesCache) {
        super(dbConnection, useEntitiesCache);
    }

    @Override
    protected void doInsert(Entity entity, PreparedStatement st) throws SQLException {
        setColumns(st, (User) entity, 0);
    }

    @Override
    protected void doUpdate(Entity entity, PreparedStatement st) throws SQLException {
        int i = 0;
        User user = (User) entity;
        i = setColumns(st, user, i);
        st.setLong(++i, user.getId());
    }

    private int setColumns(PreparedStatement st, User user, int i) throws SQLException {
        st.setLong(++i, user.getId());
        st.setString(++i, user.getFirstName());
        st.setString(++i, user.getLastName());
        st.setString(++i, user.getEmail());
        st.setString(++i, user.getAddress());
        st.setBoolean(++i, user.getActive());
        st.setLong(++i, user.getId());
        return i;
    }

    public Entity findByEmail(String email) {
        return findOneByCustomWhere("email=?", email);
    }

    protected abstract Entity doLoad(long id, ResultSet rs) throws SQLException;

    @Override
    protected String getDDL() {
        return DDL;
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    @Override
    protected String getPrimaryKeyColumnName() {
        return PK_COLUMN_NAME;
    }

    @Override
    protected String getColumnNames() {
        return COLUMNS;
    }

    @Override
    protected String getUpdateColumns() {
        return "user_id=?, first_name=?, last_name=?, email=?, address=?, active=?";
    }

}

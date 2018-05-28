package com.aaturenko.pethotel.dao.mapper;

import com.aaturenko.pethotel.dao.DataMapperRegistry;
import com.aaturenko.pethotel.entities.Entity;
import com.aaturenko.pethotel.entities.Response;
import com.aaturenko.pethotel.entities.User;
import com.aaturenko.pethotel.enums.ResponseStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ResponseMapper extends DataMapper {
    private static final String TABLE_NAME = "response";
    private static final String PK_COLUMN_NAME = "response_id";
    private static final String COLUMNS = PK_COLUMN_NAME + ", status, details, request_id, user_id, cost";
    private static final String DDL = "(?, ?, ?, ?, ?, ?)";

    public ResponseMapper(Connection connection, boolean useCache) {
        super(connection, useCache);
    }

    @Override
    protected void doInsert(Entity entity, PreparedStatement st) throws SQLException {
        setColumns((Response) entity, st);
    }

    @Override
    protected void doUpdate(Entity entity, PreparedStatement st) throws SQLException {
        setColumns((Response) entity, st);
    }

    private void setColumns(Response response, PreparedStatement st) throws SQLException {
        int i = 0;
        st.setLong(++i, response.getId());
        st.setString(++i, response.getStatus().toString());
        st.setString(++i, response.getDetails());
        st.setLong(++i, response.getRequest().getId());
        st.setLong(++i, response.getUser().getId());
        st.setInt(++i, response.getCost());
    }

    @Override
    protected Entity doLoad(long id, ResultSet rs) throws SQLException {
        UserMapper userMapper = (UserMapper) DataMapperRegistry.getMapper(User.class);
        User user = (User) userMapper.findById(rs.getLong("userId"));

        return Response.builder()
                .id(id)
                .status(ResponseStatus.valueOf(rs.getString("status")))
                .details(rs.getString("details"))
                .user(user)
                .build();
    }

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
}

package com.aaturenko.pethotel.old.dao.mapper;

import com.aaturenko.pethotel.old.dao.DataMapper;
import com.aaturenko.pethotel.old.dao.DataMapperRegistry;
import com.aaturenko.pethotel.old.entities.*;
import com.aaturenko.pethotel.enums.ResponseStatus;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ResponseMapper extends DataMapper {
    private static final String TABLE_NAME = "response";
    private static final String PK_COLUMN_NAME = "response_id";
    private static final String COLUMNS = "status, details, request_id, user_id, cost";
    private static final String DDL = "(?, ?, ?, ?, ?, ?)";

    public ResponseMapper(ComboPooledDataSource connection, boolean useCache) {
        super(connection, useCache);
    }

    @Override
    protected void doInsert(Entity entity, PreparedStatement st) throws SQLException {
        setColumns(st, (Response) entity, 0);
    }

    @Override
    protected void doUpdate(Entity entity, PreparedStatement st) throws SQLException {
        int i = 0;
        Response response = (Response) entity;
        i = setColumns(st, response, i);
        st.setLong(++i, response.getId());
    }

    private int setColumns(PreparedStatement st, Response response, int i) throws SQLException {
        st.setLong(++i, response.getId());
        st.setString(++i, response.getStatus().toString());
        st.setString(++i, response.getDetails());
        st.setLong(++i, response.request().getId());
        st.setLong(++i, response.getUser().getId());
        st.setInt(++i, response.getCost());
        return i;
    }

    public List<Response> findAllByRequest(Request request) {
        return findAllByCustomWhere("request_id = ?", request.getId());
    }

    public List<Response> findAllByUser(User user) {
        return findAllByCustomWhere("user_id = ?", user.getId());
    }

    @Override
    protected Entity doLoad(long id, ResultSet rs) throws SQLException {
        UserMapper userMapper = (UserMapper) DataMapperRegistry.getMapper(Sitter.class);
        User user = (User) userMapper.findById(rs.getLong("user_id"));

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

    @Override
    protected String getUpdateColumns() {
        return "response_id=?, status=?, details=?, request_id=?, user_id=?, cost=?";
    }

}

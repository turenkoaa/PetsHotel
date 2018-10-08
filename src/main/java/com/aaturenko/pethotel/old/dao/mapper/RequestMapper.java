package com.aaturenko.pethotel.old.dao.mapper;

import com.aaturenko.pethotel.old.dao.DataMapper;
import com.aaturenko.pethotel.old.dao.DataMapperRegistry;
import com.aaturenko.pethotel.old.entities.*;
import com.aaturenko.pethotel.enums.RequestStatus;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RequestMapper extends DataMapper {
    private static final String TABLE_NAME = "request";
    private static final String PK_COLUMN_NAME = "request_id";
    private static final String COLUMNS = "start_date, end_date, status, pet_id, user_id, paid, cost";
    private static final String DDL = "(?, ?, ?, ?, ?, ?, ?, ?)";

    public RequestMapper(ComboPooledDataSource connection, boolean useCache) {
        super(connection, useCache);
    }

    @Override
    protected void doInsert(Entity entity, PreparedStatement st) throws SQLException {
        int i = 0;
        setColumns(st, (Request) entity, i);
    }

    @Override
    protected void doUpdate(Entity entity, PreparedStatement st) throws SQLException {
        int i = 0;
        Request request = (Request) entity;
        i = setColumns(st, request, i);
        st.setLong(++i, request.getId());
    }

    private int setColumns(PreparedStatement st, Request request, int i) throws SQLException {
        st.setLong(++i, request.getId());
        st.setDate(++i, Date.valueOf(request.getStartDate()));
        st.setDate(++i, Date.valueOf(request.getEndDate()));
        st.setString(++i, request.getStatus().toString());
        st.setLong(++i, request.getPet().getId());
        st.setLong(++i, request.getUser().getId());
        st.setBoolean(++i, request.isPaid());
        st.setInt(++i, request.getCost());
        return i;
    }

    @Override
    protected Entity doLoad(long id, ResultSet rs) throws SQLException {
        PetMapper mapper = (PetMapper) DataMapperRegistry.getMapper(Pet.class);
        Pet pet = (Pet) mapper.findById(rs.getLong("pet_id"));

        return Request.builder()
                .id(id)
                .startDate(rs.getDate("start_date").toLocalDate())
                .endDate(rs.getDate("end_date").toLocalDate())
                .status(RequestStatus.valueOf(rs.getString("status")))
                .pet(pet)
                .paid(rs.getBoolean("paid"))
                .cost(rs.getInt("cost"))
                .build();
    }

    public List<Request> findAllByUser(User user) {
        return findAllByCustomWhere("user_id = ?", user.getId());
    }

    public List<Request> findAllByStatus(RequestStatus requestStatus) {
        return findAllByCustomWhere("status = ?", requestStatus.toString());
    }

    public Request findByResponse(Response response) {
        return (Request) findOneByCustomWhereJoin("request, response", "request.request_id = response.request_id and response_id = ?", response.getId()); // findOneByCustomWhereJoin("status = ?", requestStatus.toString());
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
        return "request_id=?, start_date=?, end_date=?, status=?, pet_id=?, user_id=?, paid=?, cost=?";
    }
}

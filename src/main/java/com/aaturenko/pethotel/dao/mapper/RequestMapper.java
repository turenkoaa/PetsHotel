package com.aaturenko.pethotel.dao.mapper;

import com.aaturenko.pethotel.dao.DataMapperRegistry;
import com.aaturenko.pethotel.entities.*;
import com.aaturenko.pethotel.enums.RequestStatus;
import com.sun.org.apache.bcel.internal.generic.NEW;

import java.sql.*;
import java.sql.Date;
import java.util.List;

public class RequestMapper extends DataMapper {
    private static final String TABLE_NAME = "request";
    private static final String PK_COLUMN_NAME = "request_id";
    private static final String COLUMNS = PK_COLUMN_NAME + ", start_date, end_date, status, pet_id, user_id, cost";
    private static final String DDL = "(?, ?, ?, ?, ?, ?, ?)";

    public RequestMapper(Connection connection, boolean useCache) {
        super(connection, useCache);
    }

    @Override
    protected void doInsert(Entity entity, PreparedStatement st) throws SQLException {
        Request request = (Request) entity;

        int i = 0;
        st.setLong(++i, request.getId());
        st.setDate(++i, Date.valueOf(request.getStartDate()));
        st.setDate(++i, Date.valueOf(request.getEndDate()));
        st.setString(++i, request.getStatus().toString());
        st.setLong(++i, request.getPet().getId());
        st.setLong(++i, request.getUser().getId());
        st.setInt(++i, request.getCost());
    }

    @Override
    protected void doUpdate(Entity entity, PreparedStatement st) {
        throw new UnsupportedOperationException("Request is immutable");
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
        return (Request) findOneByCustomWhere("request rq, response rs", "rq.request_id = rs.request_id and response_id = ?", response.getId()); // findOneByCustomWhere("status = ?", requestStatus.toString());
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
    protected String getUpdateQuery() {
        throw new UnsupportedOperationException("Request is immutable");
    }
}

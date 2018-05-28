package com.aaturenko.pethotel.dao.mapper;

import com.aaturenko.pethotel.entities.Entity;
import com.aaturenko.pethotel.entities.Pet;
import com.aaturenko.pethotel.entities.User;
import com.aaturenko.pethotel.enums.PetType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PetMapper extends DataMapper {
    private static final String TABLE_NAME = "pet";
    private static final String PK_COLUMN_NAME = "pet_id";
    private static final String COLUMNS = PK_COLUMN_NAME + ", pet_type, name, age, passport, user_id";
    private static final String DDL = "(?, ?, ?, ?, ?, ?)";

    public PetMapper(Connection dbConnection, boolean useEntitiesCache) {
        super(dbConnection, useEntitiesCache);
    }

    @Override
    protected void doInsert(Entity entity, PreparedStatement st) throws SQLException {
        Pet pet = (Pet) entity;

        int i = 0;
        st.setLong(++i, pet.getId());
        st.setString(++i, pet.getPetType().toString());
        st.setString(++i, pet.getName());
        st.setInt(++i, pet.getAge());
        st.setString(++i, pet.getPassport());
        st.setLong(++i, pet.getUser().getId());
    }

    @Override
    protected void doUpdate(Entity entity, PreparedStatement st) {
        throw new UnsupportedOperationException("Pet is immutable");
    }

    @Override
    protected Entity doLoad(long id, ResultSet rs) throws SQLException {
        return Pet.builder()
                .id(id)
                .petType(PetType.valueOf(rs.getString("pet_type")))
                .name(rs.getString("name"))
                .age(rs.getInt("age"))
                .passport(rs.getString("passport"))
                .build();
    }

    @SuppressWarnings("unchecked")
    public List<Pet> findAllByUser(User user) {
        return findAllByCustomWhere("user_id = ?", user.getId());
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

package com.aaturenko.pethotel.dao.mapper;

import com.aaturenko.pethotel.dao.DataMapperRegistry;
import com.aaturenko.pethotel.entities.Entity;
import com.aaturenko.pethotel.entities.Review;
import com.aaturenko.pethotel.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewMapper extends DataMapper {
    private static final String TABLE_NAME = "review";
    private static final String PK_COLUMN_NAME = "review_id";
    private static final String COLUMNS = PK_COLUMN_NAME + ", like, comment, user_id";
    private static final String DDL = "(?, ?, ?, ?)";


    public ReviewMapper(Connection connection, boolean useCache) {
        super(connection, useCache);
    }

    @Override
    protected void doInsert(Entity entity, PreparedStatement st) throws SQLException {
        setColumns((Review) entity, st);
    }

    @Override
    protected void doUpdate(Entity entity, PreparedStatement st) {
        throw new UnsupportedOperationException("Reviews are immutable");
    }

    private void setColumns(Review review, PreparedStatement st) throws SQLException{
        st.setLong(1, review.getId());
        st.setBoolean(2, review.isLike());
        st.setString(3, review.getComment());
        st.setLong(4, review.getUser().getId());
    }

    @Override
    protected Entity doLoad(long id, ResultSet rs) throws SQLException {
        UserMapper userMapper = (UserMapper) DataMapperRegistry.getMapper(User.class);
        User user = (User) userMapper.findById(rs.getLong("userId"));

        return Review.builder()
                .id(id)
                .like(rs.getBoolean("like"))
                .comment(rs.getString("comment"))
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

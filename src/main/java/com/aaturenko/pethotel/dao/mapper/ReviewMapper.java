package com.aaturenko.pethotel.dao.mapper;

import com.aaturenko.pethotel.dao.DataMapper;
import com.aaturenko.pethotel.dao.DataMapperRegistry;
import com.aaturenko.pethotel.entities.Entity;
import com.aaturenko.pethotel.entities.Review;
import com.aaturenko.pethotel.entities.User;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ReviewMapper extends DataMapper {
    private static final String TABLE_NAME = "review";
    private static final String PK_COLUMN_NAME = "review_id";
    private static final String COLUMNS = "review.like, comment, user_id";
    private static final String DDL = "(?, ?, ?, ?)";


    public ReviewMapper(ComboPooledDataSource connection, boolean useCache) {
        super(connection, useCache);
    }

    @Override
    protected void doInsert(Entity entity, PreparedStatement st) throws SQLException {
        Review review = (Review) entity;
        int i = 0;
        st.setLong(++i, review.getId());
        st.setBoolean(++i, review.isLike());
        st.setString(++i, review.getComment());
        st.setLong(++i, review.getUser().getId());
    }

    @Override
    protected void doUpdate(Entity entity, PreparedStatement st) {
        throw new UnsupportedOperationException("Reviews are immutable");
    }

    private void setColumns(Review review, PreparedStatement st) throws SQLException{
        st.setLong(1, review.getId());

    }

    @Override
    protected Entity doLoad(long id, ResultSet rs) throws SQLException {
        UserMapper userMapper = (UserMapper) DataMapperRegistry.getMapper(User.class);
        User user = (User) userMapper.findById(rs.getLong("user_id"));

        return Review.builder()
                .id(id)
                .like(rs.getBoolean("like"))
                .comment(rs.getString("comment"))
                .user(user)
                .build();
    }

    public List<Review> findAllByLike(Boolean like) {
        return findAllByCustomWhere("review.like = ?", like.toString());
    }

    public List<Review> findAllByUser(User user) {
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

    @Override
    protected String getUpdateColumns() {
        throw new UnsupportedOperationException("Reviews are immutable");
    }
}

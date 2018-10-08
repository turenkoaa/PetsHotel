package com.aaturenko.pethotel.old.dao;

import com.aaturenko.pethotel.old.entities.Entity;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public abstract class DataMapper {

    private final ComboPooledDataSource connectionPool;
    private final boolean useEntitiesCache;
    private Map<Long, Entity> entitiesCache = new HashMap<>();

    protected abstract void doInsert(Entity entity, PreparedStatement st) throws SQLException;

    protected abstract void doUpdate(Entity entity, PreparedStatement st) throws SQLException;

    protected abstract Entity doLoad(long id, ResultSet rs) throws SQLException;

    protected abstract String getDDL();

    protected String getCreateQuery() {
        return "INSERT INTO " + getTableName() + " (" + getPrimaryKeyColumnName() + ", " + getColumnNames() + ") " + " VALUES " + getDDL();
    }

    protected abstract String getTableName();

    protected abstract String getPrimaryKeyColumnName();

    protected abstract String getColumnNames();

    protected String getUpdateQuery() {
        return "UPDATE " + getTableName() + " SET "+ getUpdateColumns() +" WHERE "
                + getPrimaryKeyColumnName() + "=?";
    }

    protected abstract String getUpdateColumns();


    protected String getReadQuery() {
        String pk = getPrimaryKeyColumnName();
        return "SELECT " + pk + ", " + getColumnNames() + " FROM "
            + getTableName() + " WHERE " + pk + "=?";
    }

    protected String getDeleteQuery() { return "DELETE FROM " + getTableName() + " WHERE " + getPrimaryKeyColumnName() + "=?"; }

    public void clearCache() {
        entitiesCache.clear();
    }

    public Entity findById(final Long id) {
        return useEntitiesCache && entitiesCache.containsKey(id)
                    ? entitiesCache.get(id)
                    : tryToFindById(id);
    }

    private Entity tryToFindById(Long id) {
        Entity object;
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement findStatement = conn.prepareStatement(getReadQuery())) {
            findStatement.setLong(1, id);
            ResultSet rs = findStatement.executeQuery();
            object = load(rs);
            rs.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return object;
    }

    private Entity load(final ResultSet rs) throws SQLException {
        Entity result = null;
        if (rs.next()) {
            long id = rs.getLong(getPrimaryKeyColumnName());
            result = doLoad(id, rs);
            if (useEntitiesCache) entitiesCache.put(id, result);
        }
        return result;
    }

    private void insert(final Entity entity) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement insertStatement = conn.prepareStatement(getCreateQuery())){
            log.info("QUERY: {}", getCreateQuery());
            doInsert(entity, insertStatement);
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Entity save(final Entity entity) {
        final Entity existingObject = tryToFindById(entity.getId());
        if (existingObject == null) {
            Entity newEntity = null;
            long maxId = getMaxId() + 1;
            if (maxId > 1) {
                entity.setId(maxId);
                insert(entity);
                newEntity = findById(maxId);
                if (useEntitiesCache) entitiesCache.put(maxId, newEntity);
            }
            return newEntity;
        } else {
            return update(entity);
        }
    }

    private long getMaxId()  {
        long maxId = 0;
        String sql = "select max(" + getPrimaryKeyColumnName() + ") as max from " + getTableName();
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                maxId = resultSet.getLong("max");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return maxId;
    }

    private Entity update(final Entity entity) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement updateStatement = conn.prepareStatement(getUpdateQuery())) {
            doUpdate(entity, updateStatement);
            updateStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (useEntitiesCache) entitiesCache.put(entity.getId(), entity);

        return findById(entity.getId());
    }

    public void delete(final Entity object) {
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement deleteStatement = conn.prepareStatement(getDeleteQuery())) {
            deleteStatement.setLong(1, object.getId());
            deleteStatement.execute();
            if (useEntitiesCache) entitiesCache.remove(object.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List findAllByCustomWhere(String whereClause, Object... args) {
        final String query = "SELECT " + getPrimaryKeyColumnName() + " FROM " + getTableName() + " WHERE " + whereClause;
        final List<Entity> results = new ArrayList<>();

        try (Connection conn = connectionPool.getConnection()) {
            PreparedStatement findStatement = prepareCustomStatement(conn, query, args);
            final ResultSet rs = findStatement.executeQuery();
            while (rs.next()) {
                results.add(findById(rs.getLong(1)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    public List findAll() {
        final String query = "SELECT " + getPrimaryKeyColumnName() + " FROM " + getTableName();
        final List<Entity> results = new ArrayList<>();

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement findStatement = conn.prepareStatement(query)) {
            final ResultSet rs = findStatement.executeQuery();
            while (rs.next()) {
                results.add(findById(rs.getLong(1)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return results;
    }

    private PreparedStatement prepareCustomStatement(Connection conn, String query, Object... args) throws SQLException {
        final PreparedStatement findStatement = conn.prepareStatement(query);
        for (int i = 0; i < args.length; i++) {
            findStatement.setObject(i+1, args[i]);
        }
        return findStatement;
    }

    protected Entity findOneByCustomWhereJoin(String tableName, String whereClause, Object... args) {
        String query =
                "SELECT " + getTableName() + "." + getPrimaryKeyColumnName() + " FROM " + tableName + " WHERE " + whereClause;

        log.info("QUERY: {}", query);
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement findStatement = prepareCustomStatement(conn, query, args)) {
            ResultSet rs = findStatement.executeQuery();
            if (rs.next()) {
                return findById(rs.getLong(1));
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Entity findOneByCustomWhere(String whereClause, Object... args) {
        return findOneByCustomWhereJoin(getTableName(), whereClause, args);
    }

}

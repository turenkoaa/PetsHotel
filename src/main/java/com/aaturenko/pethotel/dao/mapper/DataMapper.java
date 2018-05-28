package com.aaturenko.pethotel.dao.mapper;

import com.aaturenko.pethotel.dao.DBConnection;
import com.aaturenko.pethotel.entities.Entity;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.*;

@RequiredArgsConstructor
public abstract class DataMapper {

    private final Connection dbConnection;
    private final boolean useEntitiesCache;
    private Map<Long, Entity> entitiesCache = new HashMap<>();

    protected abstract void doInsert(Entity entity, PreparedStatement st) throws SQLException;

    protected abstract void doUpdate(Entity entity, PreparedStatement st) throws SQLException;

    protected abstract Entity doLoad(long id, ResultSet rs) throws SQLException;

    protected abstract String getDDL();

    protected String getCreateQuery() {
        return "INSERT INTO " + getTableName() + " VALUES " + getDDL();
    }

    protected abstract String getTableName();

    protected abstract String getPrimaryKeyColumnName();

    protected abstract String getColumnNames();

    protected String getUpdateQuery() {
        return "UPDATE " + getTableName() + " SET (" + getColumnNames() + ") = " + getDDL() +" WHERE "
                + getPrimaryKeyColumnName() + "=?";
    };

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
                    : tryToFindById(dbConnection, id);
    }

    private Entity tryToFindById(final Connection conn, final Long id) {
        Entity object;
        try (PreparedStatement findStatement = conn.prepareStatement(getReadQuery())) {
            findStatement.setLong(1, id);
            ResultSet rs = findStatement.executeQuery();
            object = load(rs);
            rs.close();
        } catch (SQLException e) {
            DBConnection.closeConnection();
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

    public Entity insert(final Entity entity) {
        try (PreparedStatement insertStatement = dbConnection.prepareStatement(getCreateQuery())){
//            insertStatement.setLong(1, entity.getId());
            doInsert(entity, insertStatement);
            insertStatement.execute();
            if (useEntitiesCache) entitiesCache.put(entity.getId(), entity);
        } catch (SQLException e) {
            DBConnection.closeConnection();
            throw new RuntimeException(e);
        }

        return entitiesCache.get(entity.getId());
    }

    public Entity save(final Entity entity) {
        final Entity existingObject = findById(entity.getId());
        if (existingObject == null) {
            return insert(entity);
        } else {
            return update(entity);
        }
    }

    public Entity update(final Entity entity) {
        try (PreparedStatement updateStatement = dbConnection.prepareStatement(getUpdateQuery())) {
            doUpdate(entity, updateStatement);
            updateStatement.execute();
        } catch (SQLException e) {
            DBConnection.closeConnection();
            throw new RuntimeException(e);
        }
        if (useEntitiesCache) entitiesCache.put(entity.getId(), entity);

        return findById(entity.getId());
    }

    public void delete(final Entity object) {
        try (final PreparedStatement deleteStatement = dbConnection.prepareStatement(getDeleteQuery())) {
            deleteStatement.setLong(1, object.getId());
            deleteStatement.execute();
            if (useEntitiesCache) entitiesCache.remove(object.getId());
        } catch (SQLException e) {
            DBConnection.closeConnection();
            throw new RuntimeException(e);
        }
    }

    public List findAllByCustomWhere(String whereClause, Object... args) {
        final String query = "SELECT " + getPrimaryKeyColumnName() + " FROM " + getTableName() + " WHERE " + whereClause;
        final List<Entity> results = new ArrayList<>();

        try (PreparedStatement findStatement = prepareCustomStatement(dbConnection, query, args)) {
            final ResultSet rs = findStatement.executeQuery();
            while (rs.next()) {
                results.add(findById(rs.getLong(1)));
            }
        } catch (SQLException e) {
            DBConnection.closeConnection();
            throw new RuntimeException(e);
        }
        return results;
    }

    public List findAll() {
        final String query = "SELECT " + getPrimaryKeyColumnName() + " FROM " + getTableName();
        final List<Entity> results = new ArrayList<>();

        try (PreparedStatement findStatement = dbConnection.prepareStatement(query)) {
            final ResultSet rs = findStatement.executeQuery();
            while (rs.next()) {
                results.add(findById(rs.getLong(1)));
            }
        } catch (SQLException e) {
            DBConnection.closeConnection();
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

    protected Entity findOneByCustomWhere(String whereClause, Object... args) {
        String query =
                "SELECT " + getPrimaryKeyColumnName() + " FROM " + getTableName() + " WHERE " + whereClause;
        try (PreparedStatement findStatement = prepareCustomStatement(dbConnection, query, args)) {
            ResultSet rs = findStatement.executeQuery();
            if (rs.next()) {
                return findById(rs.getLong(1));
            } else {
                return null;
            }
        } catch (SQLException e) {
            DBConnection.closeConnection();
            throw new RuntimeException(e);
        }
    }

}

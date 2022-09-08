package ru.otus.jdbc.mapper;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import ru.otus.jdbc.core.repository.DataTemplate;
import ru.otus.jdbc.core.repository.DataTemplateException;
import ru.otus.jdbc.core.repository.executor.DbExecutor;
import ru.otus.jdbc.core.sessionmanager.DataBaseOperationException;

/**
 * Сохраняет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        try (var pst = connection.prepareStatement(entitySQLMetaData.getSelectByIdSql())) {
            pst.setDouble(1, id);
            try (var rs = pst.executeQuery()) {
                try {
                    if (rs.next()) {
                        return Optional.of(getInstance(rs));
                    }
                } catch (SQLException e) {
                    throw new DataTemplateException(e);
                } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException ex) {
            throw new DataBaseOperationException("executeSelect error", ex);
        }
        return Optional.empty();
    }

    @Override
    public List<T> findAll(Connection connection) {
        try (var pst = connection.prepareStatement(entitySQLMetaData.getSelectAllSql())) {
            try (var rs = pst.executeQuery()) {
                final List<T> resultList = new LinkedList<>();
                try {
                    while (rs.next()) {resultList.add(getInstance(rs));
                    }
                } catch (SQLException e) {
                    throw new DataTemplateException(e);
                } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
                return resultList;
            }
        } catch (SQLException ex) {
            throw new DataBaseOperationException("executeSelect error", ex);
        }
    }

    @Override
    public long insert(Connection connection, T client) {
        try (var pst = connection.prepareStatement(entitySQLMetaData.getInsertSql())) {
            var fields = entitySQLMetaData
                .getEntityClassMetaData()
                .getFieldsWithoutId();
            for (var i = 1; i <= fields.size(); i++) {
                try {
                    var field = fields.get(i - 1);
                    field.setAccessible(true);
                    pst.setObject(i, field.get(client));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            pst.execute();

            ResultSet rs = pst.getResultSet();
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                throw new SQLException("Id has not been received");
            }
        } catch (SQLException ex) {
            throw new DataBaseOperationException("executeSelect error", ex);
        }
    }

    @Override
    public void update(Connection connection, T client) {
        try (var pst = connection.prepareStatement(entitySQLMetaData.getUpdateSql())) {
            var fields = entitySQLMetaData
                .getEntityClassMetaData()
                .getFieldsWithoutId();
            var lastIndex = 0;
            for (var i = 1; i <= fields.size(); i++) {
                try {
                    var field = fields.get(i - 1);
                    field.setAccessible(true);
                    pst.setObject(i, field.get(client));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                lastIndex = i;
            }
            pst.setObject(lastIndex, entitySQLMetaData.getEntityClassMetaData().getIdField().get(client));

            pst.executeUpdate();
        } catch (SQLException ex) {
            throw new DataBaseOperationException("executeSelect error", ex);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private T getInstance(ResultSet rs) throws SQLException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        final ResultSetMetaData meta = rs.getMetaData();
        final int columnCount = meta.getColumnCount();

        final List<Object> columnValues = new LinkedList<>();
        for (int column = 1; column <= columnCount; ++column) {
            columnValues.add(rs.getObject(column));
        }

        Constructor<T> constructor = (Constructor<T>) entitySQLMetaData.getEntityClassMetaData().getConstructor();
        return constructor.newInstance(columnValues.toArray());
    }
}

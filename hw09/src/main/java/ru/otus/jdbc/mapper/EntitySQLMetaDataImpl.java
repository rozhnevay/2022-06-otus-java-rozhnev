package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData{

    public EntityClassMetaData<?> getEntityClassMetaData() {
        return entityClassMetaData;
    }

    private final EntityClassMetaData<?> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return String.format("select " +
            convertFieldsToCommaDelimited(entityClassMetaData.getAllFields()) +
            " from %s", entityClassMetaData.getName().toLowerCase());
    }

    @Override
    public String getSelectByIdSql() {
        return getSelectAllSql() +
            " where " +
            entityClassMetaData.getIdField().getName().toLowerCase() +
            " = ?";
    }

    @Override
    public String getInsertSql() {
        return "insert into " + 
            entityClassMetaData.getName().toLowerCase() +
            " (" +
            convertFieldsToCommaDelimited(entityClassMetaData.getFieldsWithoutId()) +
            ") values ( " +
            convertStringsToCommaDelimited(entityClassMetaData.getFieldsWithoutId().stream().map(field -> "?").collect(Collectors.toList())) +
            ")" +
            " returning " +
            entityClassMetaData.getIdField().getName().toLowerCase()
            ;
    }

    @Override
    public String getUpdateSql() {
        return "update " +
            entityClassMetaData.getName().toLowerCase() +
            " " +
            convertStringsToCommaDelimited(entityClassMetaData.getFieldsWithoutId().stream().map(field -> "set " + field.getName() + " = ?").collect(Collectors.toList())) +
            " where " +
            entityClassMetaData.getIdField().getName().toLowerCase() +
            " = ?"
            ;
    }

    private String convertFieldsToCommaDelimited(List<Field> fields) {
        return
            convertStringsToCommaDelimited(
                fields
                    .stream()
                    .map(Field::getName)
                    .map(String::toLowerCase)
                    .collect(Collectors.toList())
            );
    }

    private String convertStringsToCommaDelimited(List<String> fieldNames) {
        return fieldNames
            .stream()
            .map(String::toLowerCase)
            .reduce((x, y) -> String.join(",", x, y))
            .orElse("");
    }
}

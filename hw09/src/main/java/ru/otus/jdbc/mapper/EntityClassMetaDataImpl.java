package ru.otus.jdbc.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import ru.otus.jdbc.annotation.Entity;
import ru.otus.jdbc.annotation.Id;

public class EntityClassMetaDataImpl implements EntityClassMetaData{

    private final Class<?> clazz;

    public EntityClassMetaDataImpl(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String getName() {
         Optional.ofNullable(clazz.getDeclaredAnnotation(Entity.class))
            .orElseThrow(() -> new RuntimeException("Entity annotation not found"));

        return clazz.getSimpleName();
    }

    @Override
    public Constructor<?> getConstructor() {
        var constructorsSorted = Arrays.stream(clazz.getDeclaredConstructors())
            .sorted(Comparator.comparingInt(Constructor::getParameterCount))
            .collect(Collectors.toList());

        return constructorsSorted.get(constructorsSorted.size() - 1);
    }

    @Override
    public Field getIdField() {
        return Arrays.stream(clazz.getDeclaredFields())
            .filter(field -> field.getDeclaredAnnotation(Id.class) != null)
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Id field not found"));
    }

    @Override
    public List<Field> getAllFields() {
        return Arrays.asList(clazz.getDeclaredFields());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return getAllFields()
            .stream()
            .filter(field -> !field.equals(getIdField()))
            .collect(Collectors.toList());
    }
}

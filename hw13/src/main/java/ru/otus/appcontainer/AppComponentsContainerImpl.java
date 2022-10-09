package ru.otus.appcontainer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Collectors;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();
    private final Map<Class<?>, Object> appComponentsByClass = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        try {
            processConfig(initialConfigClass);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void processConfig(Class<?> configClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        checkConfigClass(configClass);
        Constructor configClassConstructor = configClass.getConstructor();
        Object configClassInstance = configClassConstructor.newInstance();
        Method[] methods = configClass.getDeclaredMethods();
        List<Method> appComponentMethods = filterMethodsByAnnotation(methods, AppComponent.class);
        appComponentMethods.stream()
           .sorted(Comparator.comparing(method -> method.getAnnotation(AppComponent.class).order()))
            .forEach(method -> {
                try {
                    var parameters = new ArrayList<>();
                    Arrays.stream(method.getParameterTypes()).forEach(parType -> {
                        parameters.add(appComponentsByClass.get(parType));
                    });
                    Object methodInvocationResult = method.invoke(configClassInstance, parameters.toArray());
                    appComponentsByClass.put(methodInvocationResult.getClass(), methodInvocationResult);
                    appComponentsByClass.put(methodInvocationResult.getClass().getInterfaces()[0], methodInvocationResult);
                    appComponentsByName.put(method.getAnnotation(AppComponent.class).name(), methodInvocationResult);
                    appComponents.add(methodInvocationResult);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) appComponentsByClass.get(componentClass);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }

    private List<Method> filterMethodsByAnnotation(Method[] methods, Class annotationClass) {
        return Arrays.stream(methods)
            .filter(
                method ->
                    Arrays.stream(method.getDeclaredAnnotations())
                        .anyMatch(annotation -> annotation.annotationType().equals(annotationClass))
            ).collect(Collectors.toList());
    }
}

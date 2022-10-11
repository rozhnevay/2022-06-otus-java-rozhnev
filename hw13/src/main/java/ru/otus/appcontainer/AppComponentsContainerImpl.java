package ru.otus.appcontainer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;
import ru.otus.appcontainer.exceptions.AppComponentsContainerInitException;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();
    private final Map<Class<?>, List<Object>> appComponentsByClass = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        try {
            processConfig(initialConfigClass);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new AppComponentsContainerInitException("Cannot init container", e);
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
            .forEach(method -> initAppComponentByMethod(method, configClassInstance));
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        if (!appComponentsByClass.containsKey(componentClass)){
            throw new IllegalArgumentException("Component with class " + componentClass + " not found");
        }
        if (appComponentsByClass.get(componentClass).size() > 1) {
            throw new IllegalArgumentException("Component with class " + componentClass + " found multiple times");
        }
        return (C) appComponentsByClass.get(componentClass).get(0);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        if (!appComponentsByName.containsKey(componentName)){
            throw new IllegalArgumentException("Component with name " + componentName + " not found");
        }
        return (C) appComponentsByName.get(componentName);
    }

    private List<Method> filterMethodsByAnnotation(Method[] methods, Class annotationClass) {
        return Arrays.stream(methods)
            .filter(
                method -> method.isAnnotationPresent(annotationClass)
            ).collect(Collectors.toList());
    }

    private void initAppComponentByMethod(Method method, Object configClassInstance) {
        try {
            var parameters = new ArrayList<>();
            Arrays.stream(method.getParameterTypes()).forEach(parType -> {
                parameters.add(getAppComponent(parType));
            });
            Object methodInvocationResult = method.invoke(configClassInstance, parameters.toArray());

            String componentQualifier = method.getAnnotation(AppComponent.class).name();

            if (componentQualifier.length() > 0) {
                putResultComponentByName(componentQualifier, methodInvocationResult);
            }
            putResultComponentByClass(methodInvocationResult.getClass(), methodInvocationResult);

            appComponents.add(methodInvocationResult);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new AppComponentsContainerInitException("Cannot init component by method", e);
        }
    }

    private void putResultComponentByClass(Class resultClass, Object methodInvocationResult) {
        var componentsList = appComponentsByClass.getOrDefault(resultClass, new ArrayList<>());
        componentsList.add(methodInvocationResult);
        appComponentsByClass.put(resultClass, componentsList);
        if (resultClass.getSuperclass() != null && !resultClass.getSuperclass().equals(Object.class)) {
            putResultComponentByClass(resultClass.getAnnotatedSuperclass().getClass(), methodInvocationResult);
        }
        for (Class interf: resultClass.getInterfaces()) {
            putResultComponentByClass(interf, methodInvocationResult);
        }
    }

    private void putResultComponentByName(String componentQualifier, Object methodInvocationResult) {
        if (appComponentsByName.containsKey(componentQualifier)) {
            throw new AppComponentsContainerInitException("Component with name " + componentQualifier + " already initialized");
        }
        appComponentsByName.put(componentQualifier, methodInvocationResult);
    }
}

package ru.otus.dataprocessor;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;
import ru.otus.model.Measurement;

import java.util.List;
import java.util.Map;

public class ProcessorAggregator implements Processor {

    @Override
    public Map<String, Double> process(List<Measurement> data) {
        //группирует выходящий список по name, при этом суммирует поля value
        return data.stream()
            .sorted(Comparator.comparing(Measurement::getName))
            .collect(Collectors.groupingBy(Measurement::getName, LinkedHashMap::new, Collectors.summingDouble(Measurement::getValue)));
    }
}

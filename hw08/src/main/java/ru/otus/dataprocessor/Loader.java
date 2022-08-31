package ru.otus.dataprocessor;

import java.io.FileNotFoundException;
import java.io.IOException;
import ru.otus.model.Measurement;

import java.util.List;

public interface Loader {

    List<Measurement> load() throws IOException;
}

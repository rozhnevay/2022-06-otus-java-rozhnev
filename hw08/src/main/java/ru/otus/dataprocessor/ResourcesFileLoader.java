package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import ru.otus.model.Measurement;

public class ResourcesFileLoader implements Loader {

    private final String filename;

    public ResourcesFileLoader(String filename) {
        this.filename = filename;
    }

    @Override
    public List<Measurement> load() throws IOException {
        try (JsonReader reader = new JsonReader(new InputStreamReader(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream(filename))));) {
            var gson = new Gson();

            return gson.fromJson(reader, new TypeToken<ArrayList<Measurement>>(){}.getType());
        }
    }
}

package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.otus.model.Measurement;
import java.util.List;

public class ResourcesFileLoader implements Loader {

    private final String filename;

    public ResourcesFileLoader(String filename) {
        this.filename = filename;
    }

    @Override
    public List<Measurement> load() throws IOException {
        try (JsonReader reader = new JsonReader(new FileReader(filename));) {
            var gson = new Gson();

            return gson.fromJson(reader, new TypeToken<ArrayList<Measurement>>(){}.getType());
        }
    }
}

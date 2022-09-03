package ru.otus.dataprocessor;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class FileSerializer implements Serializer {

    private final String filename;

    private final ObjectMapper mapper = new ObjectMapper();

    public FileSerializer(String filename) {
        this.filename = filename;
    }

    @Override
    public void serialize(Map<String, Double> data) throws IOException {
        //формирует результирующий json и сохраняет его в файл
        var file = new File(filename);
        mapper.writeValue(file, data);
    }
}

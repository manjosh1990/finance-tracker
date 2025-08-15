package com.manjosh.labs.backend.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class TestUtils {

    private final ObjectMapper objectMapper;

    public TestUtils() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
    /**
     * Reads a JSON file from the specified file path and converts it to the specified class
     * @param filePath Path to the JSON file (can be absolute or relative to the working directory)
     * @param clazz The class to convert the JSON to
     * @return An instance of the specified class populated with data from the JSON file
     * @throws IOException If there's an error reading the file or parsing the JSON
     */
    public <T> T readJsonFromFile(final String fileName, final String path, final Class<T> clazz) {
        try {
            // Construct the full file path
            final File file = Paths.get(path, fileName).toFile();
            // Read and deserialize the JSON file to the specified POJO class
            return objectMapper.readValue(file, clazz);
        } catch (IOException e) {
            // Handle the exception (you can customize this based on your needs)
            throw new RuntimeException("Error reading JSON file: " + fileName + " from path: " + path, e);
        }
    }
}

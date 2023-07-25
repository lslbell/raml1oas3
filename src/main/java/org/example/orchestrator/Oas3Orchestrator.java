package org.example.orchestrator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import lombok.Getter;
import lombok.Setter;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutionException;

//webapi-parser stuff
import webapi.Raml10;
import webapi.WebApiBaseUnit;
import webapi.WebApiDocument;

@Getter
@Setter
public class Oas3Orchestrator {

    static LinkedHashMap<Object, Object> raml1Data;
    static LinkedHashMap<String, Object> oas3Template;
    static String mediaType;
    static Yaml yaml = new Yaml();

    public static void createOas3(String sourceRaml1FilePath, String destOasFilePath) throws IOException, ExecutionException, InterruptedException { // need to think about optimisation
        final String oas3FilePath = "src/main/resources/templates/Oas3Template1.yaml";

        oas3Template = loadOas3Template(oas3FilePath);
//        readRaml1File(sourceRaml1FilePath);
//        writeOas3ToFile(destOasFilePath);'

    }

    public static LinkedHashMap<String, Object> loadOas3Template(String filePath) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(filePath);
        LinkedHashMap<String, Object> template = yaml.load(inputStream);

        return template;
    }

    public static void readRaml1File(String filePath) throws ExecutionException, InterruptedException {
        try {
//            File ramlFile = new File(filePath);

            final WebApiBaseUnit result = Raml10.parse(filePath).get();

            // Log parsed model API
            System.out.println("Parsed Raml10 file. Expected unit encoding webapi: " + ((WebApiDocument) result).encodes());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeOas3MapToFile(String outputPath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER) // Disable writing document start marker
                .enable(YAMLGenerator.Feature.MINIMIZE_QUOTES) // Minimize quotes
                .enable(YAMLGenerator.Feature.ALWAYS_QUOTE_NUMBERS_AS_STRINGS) // Quote numbers as strings
                .enable(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID) // Use native type ID
                .configure(YAMLGenerator.Feature.INDENT_ARRAYS_WITH_INDICATOR, true)
        );

        try {
            String yamlString = objectMapper.writeValueAsString(oas3Template);

            try (FileWriter fileWriter = new FileWriter(outputPath)) {
                fileWriter.write(yamlString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

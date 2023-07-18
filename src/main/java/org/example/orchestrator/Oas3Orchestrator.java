package org.example.orchestrator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;
import org.raml.v2.api.model.common.ValidationResult;
import org.raml.v2.api.model.v10.api.Api;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class Oas3Orchestrator {

    static LinkedHashMap<Object, Object> raml1Data;
    static LinkedHashMap<String, Object> oas3Template;

    static Yaml yaml = new Yaml();

    public static void createOas3() throws IOException { // need to think about optimisation
        String oas3FilePath = "src/main/resources/templates/Oas3Template1.yaml";
        String raml1FilePath = "C:\\Users\\Lewis B\\Github_projs\\raml1oas3\\src\\main\\resources\\examples\\source.raml";
        String destFilePath = "C:\\Users\\Lewis B\\Github_projs\\raml1oas3\\src\\main\\resources\\templates\\destTarget.yaml";

        oas3Template = (LinkedHashMap<String, Object>) loadOas3Template(oas3FilePath);
        readRaml1File(raml1FilePath);
        writeOas3ToFile(destFilePath);
    }

    public static Map<String, Object> loadOas3Template(String filePath) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(filePath);
        Map<String, Object> template = yaml.load(inputStream);

        return template;
    }

    public static void readRaml1File(String filePath) {

        RamlModelResult ramlModelResult = new RamlModelBuilder().buildApi(filePath);

        if (ramlModelResult.hasErrors()) {
            for (ValidationResult validationResult : ramlModelResult.getValidationResults()) {
                System.out.println("Error parsing raml v1: " + validationResult.getMessage());
            }
        } else {
            Api api = ramlModelResult.getApiV10();

            //info -- cannot be null! -- needs a null check
            LinkedHashMap<String, Object> info = (LinkedHashMap<String, Object>) oas3Template.get("info");
            info.put("title", api.title().value().toString().trim());
            info.put("version", api.version().value().toString().trim());
            info.put("description", api.description().value().toString().trim());
            oas3Template.put("info", info);

            //servers
            String baseUri = api.baseUri().value().toString().trim();
            Object[] servers = new Object[]{baseUri};
            oas3Template.put("servers", servers);
        }
    }

    public static void writeOas3ToFile(String filePath) throws IOException {
        // Create an instance of ObjectMapper with YAMLFactory
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));

        // Configure YAML generator settings
        YAMLGenerator yamlGenerator = ((YAMLFactory) objectMapper.getFactory()).createGenerator(System.out);
        yamlGenerator.useDefaultPrettyPrinter();  // Enable pretty printing

        try {
            String yamlString = objectMapper.writeValueAsString(oas3Template);

            try (FileWriter fileWriter = new FileWriter(filePath)) {
                fileWriter.write(yamlString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

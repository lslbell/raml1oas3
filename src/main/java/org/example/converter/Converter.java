package org.example.converter;

import amf.Core;
import amf.client.model.document.BaseUnit;
import amf.client.parse.Raml10Parser;
import amf.client.render.Oas30Renderer;
import amf.client.resolve.Oas30Resolver;
import amf.core.resolution.pipelines.ResolutionPipeline;
import com.fasterxml.jackson.core.PrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;

public class Converter {

    public static void convertRaml10ToOas30(String inputRamlPath, String outputOasPath) throws IOException, ExecutionException, InterruptedException {
        Core.init().get();

        final Raml10Parser parser = new Raml10Parser();
        final Oas30Resolver resolver = new Oas30Resolver();
        final Oas30Renderer renderer = new Oas30Renderer();

        final BaseUnit ramlApi = parser.parseFileAsync("file://src//main//resources//examples//source_top_level_example.raml").get();
//        final BaseUnit ramlApi = parser.parseFileAsync("file://src//main//resources//examples//source_github_example.raml").get();
        final BaseUnit convertedOasApi = resolver.resolve(ramlApi, ResolutionPipeline.COMPATIBILITY_PIPELINE());

        final String result = renderer.generateString(convertedOasApi).get().trim();
        System.out.println(result);

        Path path = Paths.get("C:\\Users\\Lewis B\\Github_projs\\raml1oas3\\src\\main\\resources\\generated\\destOasJson.json");
        Files.write(path, result.getBytes());

//        writeOas3ToFileUsingString("C://Users//Lewis B//Github_projs//raml1oas3//src//main//resources//generated//destOasJson.json", "C://Users//Lewis B//Github_projs//raml1oas3//src//main//resources//generated//destTarget.yaml");
        convertJsonToYaml("C://Users//Lewis B//Github_projs//raml1oas3//src//main//resources//generated//destOasJson.json",  "C://Users//Lewis B//Github_projs//raml1oas3//src//main//resources//generated//destTarget.yaml");
    }

    public static void convertJsonToYaml(String jsonFilePath, String yamlFilePath) throws IOException {
        // Read the JSON-formatted OAS3 from the input file
        FileReader fileReader = new FileReader(jsonFilePath);
        Object oas3Json = new Yaml().load(fileReader);

        // Configure the DumperOptions for prettifying YAML
        DumperOptions options = new DumperOptions();
        options.setIndent(2); // Set the indentation to 2 spaces
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK); // Use block style for sequences and mappings

        // Write the prettified YAML to the output file
        try (FileWriter fileWriter = new FileWriter(yamlFilePath)) {
            Yaml yaml = new Yaml(options);
            yaml.dump(oas3Json, fileWriter);
        }
    }

    public static void writeOas3ToFileUsingString(String inputFilePath, String outputPath) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        FileReader fileReader = new FileReader(new File(inputFilePath));
        Object oas3Json = objectMapper.readValue(fileReader, Object.class);

        String oas3Yaml = new Yaml().dump(oas3Json);

        DumperOptions options = new DumperOptions();
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Yaml yaml = new Yaml(options);
        Object yamlObject = yaml.load(oas3Yaml);
        String prettifiedYaml = yaml.dump(yamlObject);

        try (FileWriter fileWriter = new FileWriter(outputPath)) {
            fileWriter.write(prettifiedYaml);
        }
    }
}

package org.example;

import org.example.converter.Converter;
import org.example.orchestrator.Oas3Orchestrator;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {

//        Scanner scanner = new Scanner(System.in);
//        System.out.printf("Enter the ramlv1 source file path:");
//        String sourceFilePath = scanner.nextLine();
//        System.out.printf("Enter the swagger v3 destination file path:");
//        String destFilePath = scanner.nextLine();

        final String sourceFilePath = "C://Users//Lewis B//Github_projs//raml1oas3//src//main//resources//examples//source.raml";
        final String destFilePath = "C://Users//Lewis B//Github_projs//raml1oas3//src//main//resources//generated//destTarget.yaml";

//        Oas3Orchestrator.createOas3(sourceFilePath, destFilePath);

        Converter.convertRaml10ToOas30(sourceFilePath, destFilePath);
    }
}

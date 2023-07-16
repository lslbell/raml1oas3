package org.example;

import org.example.orchestrator.Oas3Orchestrator;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

//        Scanner scanner = new Scanner(System.in);
//        System.out.printf("Enter the ramlv1 source file path:");
//        String sourceFilePath = scanner.nextLine();
//        System.out.printf("Enter the swagger v3 destination file path:");
//        String destFilePath = scanner.nextLine();

        String sourceFilePath = "C:\\Users\\Lewis B\\Github_projs\\raml1oas3\\src\\main\\resources\\examples\\source.raml";
        String destFilePath = "C:\\Users\\Lewis B\\Github_projs\\raml1oas3\\src\\main\\resources\\examples\\dest.yaml";

        try (BufferedReader br = new BufferedReader(new FileReader(sourceFilePath.trim()));
             BufferedWriter bw = new BufferedWriter(new FileWriter(destFilePath.trim()))) {

            String line;
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
                bw.flush();
            }

            System.out.println("File written successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }

        Oas3Orchestrator.createOas3();
    }
}

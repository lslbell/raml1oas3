package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please provide a file path.");
            return;
        }

        String filePath = args[0];

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {

            String line;
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }

            System.out.println("File written successfully.");

        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}

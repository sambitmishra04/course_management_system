package io;

//Writing and Reading Text Files

import java.io.*;

public class TextFileIO {
 public static void main(String[] args) {
     String filename = "sample.txt";

     // Writing to a file
     try (FileWriter writer = new FileWriter(filename)) {
         writer.write("Hello, this is a text file.\n");
         writer.write("Java File Handling Example.");
         System.out.println("Text written successfully!");
     } catch (IOException e) {
         e.printStackTrace();
     }

     // Reading from a file
     try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
         String line;
         System.out.println("\nReading file:");
         while ((line = reader.readLine()) != null) {
             System.out.println(line);
         }
     } catch (IOException e) {
         e.printStackTrace();
     }
 }
}


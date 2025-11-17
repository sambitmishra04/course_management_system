package io;

//Writing and Reading Binary Files

import java.io.*;

public class BinaryFileIO {
 public static void main(String[] args) {
     String filename = "data.bin";

     // Writing binary data
     try (FileOutputStream fos = new FileOutputStream(filename)) {
         fos.write(100);  // writing a single byte
         fos.write(200);
         System.out.println("Binary data written successfully!");
     } catch (IOException e) {
         e.printStackTrace();
     }

     // Reading binary data
     try (FileInputStream fis = new FileInputStream(filename)) {
         System.out.println("\nReading binary file:");
         int data;
         while ((data = fis.read()) != -1) {
             System.out.println("Byte value: " + data);
         }
     } catch (IOException e) {
         e.printStackTrace();
     }
 }
}

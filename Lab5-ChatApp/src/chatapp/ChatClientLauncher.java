package chatapp;

import java.io.File;
import java.io.IOException;

public class ChatClientLauncher {

    public static void main(String[] args) {

        try {
            // Detect Eclipse's output folder (bin or build/classes)
            String classPath = new File(ChatClientLauncher.class
                    .getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI())
                    .getPath();

            System.out.println("Launching ChatClient from classpath: " + classPath);

            ProcessBuilder pb = new ProcessBuilder(
                    System.getProperty("java.home") + File.separator + "bin" + File.separator + "java",
                    "-cp",
                    classPath,
                    "ChatClient"
            );

            pb.inheritIO();   // give it its own console
            pb.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

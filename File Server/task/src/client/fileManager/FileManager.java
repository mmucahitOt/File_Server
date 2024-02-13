package client.fileManager;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileManager {
    static String path = "C:/Users/mmuca/OneDrive/Masaüstü/File Server/File Server/task/src/client/data/";

    public String getFileByName(String filename) {
        try {
            return new String(Files.readAllBytes(Paths.get(path + filename)));
        } catch (IOException e) {
            return null;
        }
    }

    public void createFile(String filename, String content) {
        File file = new File(path + filename);

        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        } catch (IOException e) {
            System.out.printf("An exception occurred %s", e.getMessage());
        }
    }
}

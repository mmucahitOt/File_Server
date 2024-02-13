package server;

import server.idManager.IdManager;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

public class FileManager {

    IdManager idManager;
    static String path = "C:/Users/mmuca/OneDrive/Masaüstü/File Server/File Server/task/src/server/data/";

    FileManager(IdManager idManager) {
        this.idManager = idManager;
    }

    synchronized public Integer createFile(String filename, String content) {
        int id = idManager.getId();
        if (Objects.equals(filename, "__")) {
            filename = "file" + id;
        }
        File file = new File(path + filename);

        if (file.exists()) {
            return null;
        }
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
            idManager.addToFileIds(id, filename);
            return id;
        } catch (IOException e) {
            System.out.printf("An exception occurred %s", e.getMessage());
            return null;
        }
    }
    synchronized public String getFileByName(String filename) {
        try {
            return new String(Files.readAllBytes(Paths.get(path + filename)));
        } catch (IOException e) {
            return null;
        }
    }

    synchronized public String getFileById(int id) {
        try {
            String filename = idManager.getFilename(id);
            if (filename == null) {
                return null;
            }
            return new String(Files.readAllBytes(Paths.get(path + filename)));
        } catch (IOException e) {
            return null;
        }
    }

    synchronized public boolean deleteFileByName(String filename) {
        File file = new File(path + filename);
        if (!file.exists()) {
            return false;
        }
        boolean result = file.delete();
        idManager.deleteFromFileIdsByFilename(filename);
        return result;
    }

    synchronized public boolean deleteFileById(int id) {
        String filename = idManager.getFilename(id);
        if (filename == null) {
            return false;
        }
        File file = new File(path + filename);
        if (!file.exists()) {
            return false;
        }

        boolean result = file.delete();
        idManager.deleteFromFileIdsById(id);
        return result;
    }


    public static boolean fileExists(String filename) {

        File file = new File(path + filename);

        return file.exists();
    }


    synchronized public void serializeIdManager() {
        try {
            IdManager.serialize(idManager);
        } catch (IOException e) {
            System.out.println("IdManager Serialization failed!!");
        }
    }

}

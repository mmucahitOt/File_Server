package server;

import server.enums.ByNAME_ID;
import server.enums.Request;
import server.idManager.IdManager;

import java.io.DataOutputStream;
import java.util.function.Function;

public class Service {

    FileManager fileManager;

    Service(IdManager idManager) {
        this.fileManager = new FileManager(idManager);
    }

    public String handleRequest(Request request, String filename, String content, ByNAME_ID filter, Function<String, String> shutDown) {
        switch (request) {
            case GET:
                return this.getFile(filename, filter);
            case PUT:
                return this.createFile(filename, content);
            case DELETE:
                return this.deleteFile(filename, filter);
            case EXIT:
                this.exit( shutDown);
                return "";

        }
        return "error";
    }

    synchronized private String getFile(String filename, ByNAME_ID filter) {
        if (filter == ByNAME_ID.BY_ID) {
            return getFileById(Integer.parseInt(filename));
        }
        return getFileByName(filename);
    }

    synchronized private String getFileByName(String filename) {
        String result = fileManager.getFileByName(filename);

        if (result == null) {
            return "404";
        }
        return "200 " + result;
    }

    synchronized private String getFileById(int filename) {
        String result = fileManager.getFileById(filename);

        if (result == null) {
            return "404";
        }
        return "200 " + result;
    }

    synchronized private String createFile(String filename, String content) {
        Integer result = fileManager.createFile(filename, content);

        if (result == null) {
            return "403";
        }
        return "200 " + result;
    }

    synchronized private String deleteFile(String filename, ByNAME_ID filter) {
        if (filter == ByNAME_ID.BY_ID) {
            return  deleteFileById(Integer.parseInt(filename));
        }

        return deleteFileByName(filename);
    }

    synchronized private String deleteFileById(int id) {
        boolean result = fileManager.deleteFileById(id);

        if (!result) {
            return "404";
        }
        return "200";
    }

    synchronized private String deleteFileByName(String filename) {
        boolean result = fileManager.deleteFileByName(filename);

        if (!result) {
            return "404";
        }
        return "200";
    }

    synchronized private String exit(Function<String, String> shutDown) {
        shutDown.apply("");
        return "";
    }

}

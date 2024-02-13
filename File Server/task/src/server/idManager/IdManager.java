package server.idManager;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class IdManager implements Serializable {
    static String path = "C:/Users/mmuca/OneDrive/Masaüstü/File Server/File Server/task/src/server/";
    volatile HashMap<Integer, String> fileIds = new HashMap<>();
    private final static String filename = "idManager"; // "C:/Users/mmuca/OneDrive/Masaüstü/File Server/File Server/task/src/server/" +
    private static final long serialVersionUID = 7L;
    volatile int id = 0;

    synchronized public int getId() {
        int fileId = id;
        id = id + 1;
        return fileId;
    }

    synchronized public String getFilename(int id) {
        return this.fileIds.get(id);
    }
    synchronized public void addToFileIds(int id, String filename) {
        fileIds.put(id, filename);
    }

    synchronized public void deleteFromFileIdsById(int id) {
        fileIds.remove(id);
    }

    synchronized public void deleteFromFileIdsByFilename(String filename) {
        for (int id: fileIds.keySet()) {
            if (filename.equals(fileIds.get(id))) {
                fileIds.remove(id);
            }
        }
    }

    synchronized public static void serialize(IdManager obj) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(obj);
        oos.close();
    }

    /**
     * Deserialize to an object from the file
     */
    synchronized public static IdManager deserialize() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(filename);
        BufferedInputStream bis = new BufferedInputStream(fis);
        ObjectInputStream ois = new ObjectInputStream(bis);
        Object obj = ois.readObject();
        ois.close();
        return (IdManager) obj;
    }
}

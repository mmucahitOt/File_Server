package server;

import server.idManager.IdManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public ExecutorService executor = Executors.newFixedThreadPool(10);
    public int PORT = 23456;

    static ServerSocket server;
    volatile Service service;
    volatile IdManager idManager;
    public void start() {
        System.out.println("Server started!");
        try {
            try {
                idManager = IdManager.deserialize();
            } catch (IOException | ClassNotFoundException e) {
                idManager = new IdManager();
            }
            service = new Service(idManager);
            server = new ServerSocket(PORT);
            while (true) {
                Session session = new Session(server.accept(), service, idManager, this::shutDown);
                this.executor.submit(session);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    synchronized public  String shutDown(String a){
        try {
            IdManager.serialize(idManager);
            executor.shutdownNow();
            server.close();
            System.exit(0);
            return "";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
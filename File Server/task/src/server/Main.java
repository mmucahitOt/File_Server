package server;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static ExecutorService executor = Executors.newFixedThreadPool(10);
    public static int PORT = 23456;


    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

}
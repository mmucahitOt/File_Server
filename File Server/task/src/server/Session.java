package server;

import server.enums.ByNAME_ID;
import server.enums.Request;
import server.idManager.IdManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.function.Function;

public class Session implements Runnable{
    Socket socket;
    Service service;

    Function<String, String> shutDown;
    DataOutputStream output;
    Session(Socket socket, Service service, IdManager idManager, Function<String, String> shutDown) {
        this.socket = socket;
        this.service = service;
        this.shutDown = shutDown;
    }

    @Override
    public void run() {
        try (
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            this.output = output;
            int length = input.readInt();                // read length of incoming message
            byte[] message = new byte[length];
            input.readFully(message, 0, message.length);
            String msg = new String(message); // read a message from the client
            String[] inputs = msg.split(" ");
            String requestInput = inputs[0];
            Request request = Request.toRequest(requestInput);
            ByNAME_ID filter;
            String filename;
            String content;
            if (request == Request.GET) {
                content = null;
                filename = inputs[2];
                filter = ByNAME_ID.toRequest(inputs[1]);
            } else if (request == Request.DELETE) {
                content = null;
                filename = inputs[2];
                filter = ByNAME_ID.toRequest(inputs[1]);
            }
            else {
                filter = null;
                if (request == Request.PUT) {
                    filename = inputs[1];
                    content = inputs[2];
                } else {
                    content = null;
                    filename = null;
                }
            }
            String result = service.handleRequest(request, filename, content, filter, this::shutdown);
            output.writeUTF(result);
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    synchronized public  String shutdown(String a){
        try {
            output.writeUTF("server closed!!");
            this.shutDown.apply("");
            socket.close();
            System.exit(0);
            return "";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

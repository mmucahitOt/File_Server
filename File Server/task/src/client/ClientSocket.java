package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientSocket {
    String address = "127.0.0.1";
    private final int PORT = 23456;

    public String  sendMessage(String msg) {
        try (
                Socket socket = new Socket(address, PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output  = new DataOutputStream(socket.getOutputStream())
        ) {

            output.writeUTF(msg); // send a message to the server
            String receivedMsg = input.readUTF(); // read the reply from the server

            return receivedMsg;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public String sendBytes(byte[] message) {
        try (
                Socket socket = new Socket(address, PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output  = new DataOutputStream(socket.getOutputStream())
        ) {

            output.writeInt(message.length); // write length of the message
            output.write(message);           // write the message
            String receivedMsg = input.readUTF();
            return receivedMsg;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";
    }
}

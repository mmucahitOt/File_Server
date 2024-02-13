package client;


import client.userInterface.UserInterface;

public class Main {
    public static void main(String[] args) {
        UserInterface userInterface = new UserInterface();
        userInterface.commandLoop();
    }
}
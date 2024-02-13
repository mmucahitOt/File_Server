package client.userInterface;



import client.ClientSocket;
import client.enums.ByName_Id;
import client.enums.Command;
import client.fileManager.FileManager;

import java.util.Scanner;

public class UserInterface {
    ClientSocket socket = new ClientSocket();
    Scanner scanner = new Scanner(System.in);

    FileManager fileManager = new FileManager();

    public void commandLoop() {
            System.out.print("Enter action (1 - get a file, 2 - save a file, 3 - delete a file): ");
            String input = scanner.nextLine();
            Command command = Command.toCommand(input);

            switch (command) {
                case GET:
                    getFile();
                    break;
                case SAVE:
                    saveFile();
                    break;
                case DELETE:
                    deleteFile();
                    break;
                case EXIT:
                    exit();
                    break;
            }
    }

    private void getFile() {
        System.out.print("Do you want to get the file by name or by id (1 - name, 2 - id): ");
        String filterInput = scanner.nextLine();
        ByName_Id filter = ByName_Id.toByName_Id(filterInput);

        if (filter == ByName_Id.BY_ID) {
            getFileById();
            return;
        }

        if (filter == ByName_Id.BY_NAME) {
            getFileByName();
            return;
        }
    }

    private void getFileById() {
        System.out.print("Enter id: ");
        String filename = scanner.nextLine();
        String message = "get " + ByName_Id.BY_ID.name + " " + filename;
        byte [] bytes = message.getBytes();
        String result = socket.sendBytes(bytes);
        System.out.println("The request was sent.");

        if (result.equals("404")) {
            System.out.println("The response says that this file is not found!");
            return;
        }
        if (result.split(" ")[0].equals("200")) {
            String content = result.split(" ")[1];
            System.out.print("The file was downloaded! Specify a name for it: ");
            filename = scanner.nextLine();
            fileManager.createFile(filename, content);
            System.out.println("File saved on the hard drive!");
            return;
        }
        System.out.println("Something is wrong");
    }

    private void getFileByName() {
        System.out.print("Enter filename: ");
        String filename = scanner.nextLine();
        String message = "get " + ByName_Id.BY_NAME.name + " " + filename;
        byte [] bytes = message.getBytes();
        String result = socket.sendBytes(bytes);
        System.out.println("The request was sent.");

        if (result.equals("404")) {
            System.out.println("The response says that this file is not found!");
            return;
        }
        if (result.split(" ")[0].equals("200")) {
            String content = result.split(" ")[1];
            System.out.print("The file was downloaded! Specify a name for it: ");
            filename = scanner.nextLine();
            fileManager.createFile(filename, content);
            System.out.println("File saved on the hard drive!");
            return;
        }
        System.out.println("Something is wrong");
    }
    private void saveFile() {
        System.out.print("Enter name of the file: ");
        String filename = scanner.nextLine();
        String content = fileManager.getFileByName(filename);
        System.out.print("Enter name of the file to be saved on server: ");
        String sendFile = scanner.nextLine();
        String message;
        if (sendFile != null && !sendFile.isEmpty() && !sendFile.equals("\n")) {
            message = "put " + sendFile + " " + content;
        } else {
            message = "put " + "__" + " " +  content;
        }
        byte [] bytes = message.getBytes();
        String result = socket.sendBytes(bytes);

        String[] results = result.split(" ");

        System.out.println("The request was sent.");

        if (results[0].equals("403")) {
            System.out.println("The response says that creating the file was forbidden!");
            return;
        }
        if (results[0].equals("200")) {
            System.out.println("Response says that file is saved! ID = " + results[1]);
            return;
        }
        System.out.println("Something is wrong");
    }

    private void deleteFile() {
        System.out.print("Do you want to delete the file by name or by id (1 - name, 2 - id): ");
        String filterInput = scanner.nextLine();
        ByName_Id filter = ByName_Id.toByName_Id(filterInput);

        if (filter == ByName_Id.BY_ID) {
            deleteFileById();
            return;
        }

        if (filter == ByName_Id.BY_NAME) {
            deleteFileByName();
            return;
        }
    }
    private void deleteFileById() {
        System.out.print("Enter id: ");
        String filename = scanner.nextLine();
        String message = "delete " + ByName_Id.BY_ID.name + " " + filename;
        byte [] bytes = message.getBytes();
        String result = socket.sendBytes(bytes);
        System.out.println("The request was sent.");

        if (result.equals("404")) {
            System.out.println("The response says that the file was not found!");
            return;
        }
        if (result.equals("200")) {
            System.out.println("The response says that the file was successfully deleted!");
            return;
        }
        System.out.println("Something is wrong");
    }

    private void deleteFileByName() {
        System.out.print("Enter filename: ");
        String filename = scanner.nextLine();
        String message = "delete " + ByName_Id.BY_NAME.name + " " + filename;
        byte [] bytes = message.getBytes();
        String result = socket.sendBytes(bytes);
        System.out.println("The request was sent.");

        if (result.equals("404")) {
            System.out.println("The response says that the file was not found!");
            return;
        }
        if (result.equals("200")) {
            System.out.println("The response says that the file was successfully deleted!");
            return;
        }
        System.out.println("Something is wrong");
    }

    private void exit() {
        String message = "exit";
        byte [] bytes = message.getBytes();
        String result = socket.sendBytes(bytes);
        System.out.println("The request was sent.");
        System.exit(0);
    }
}

package client.enums;

import java.util.InputMismatchException;

public enum Command {
    GET("1"),
    SAVE("2"),
    DELETE("3"),
    EXIT("exit");
    final String value;

    Command(String value) {
        this.value = value;
    }



    static public Command toCommand(String value) {
        switch (value) {
            case "1":
                return GET;
            case "2":
                return SAVE;
            case "3":
                return DELETE;
            case "exit":
                return EXIT;
            default:
                throw new InputMismatchException();
        }
    }
}

package server.enums;

import java.util.InputMismatchException;

public enum Request {
    PUT("put"),
    GET("get"),
    DELETE("delete"),
    EXIT("exit");
    String name;

    Request(String name) {
        this.name = name;
    }



    static public Request toRequest(String name) {
        switch (name) {
            case "put":
                return PUT;
            case "get":
                return GET;
            case "delete":
                return DELETE;
            case "exit":
                return EXIT;
            default:
                throw new InputMismatchException();
        }
    }
}

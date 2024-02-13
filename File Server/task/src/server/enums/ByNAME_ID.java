package server.enums;

import java.util.InputMismatchException;

public enum ByNAME_ID {
    BY_ID("by_id"),
    BY_NAME("by_name");
    String name;

    ByNAME_ID(String name) {
        this.name = name;
    }



    static public ByNAME_ID toRequest(String name) {
        switch (name) {
            case "by_id":
                return BY_ID;
            case "by_name":
                return BY_NAME;

            default:
                throw new InputMismatchException();
        }
    }
}

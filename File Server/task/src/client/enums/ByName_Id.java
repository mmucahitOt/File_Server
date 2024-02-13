package client.enums;

import java.util.InputMismatchException;

public enum ByName_Id {
    BY_NAME("1", "by_name"),
    BY_ID("2", "by_id");
    final String value;

    public final String name;

    ByName_Id(String value, String name) {
        this.value = value;
        this.name = name;
    }



    static public ByName_Id toByName_Id(String value) {
        switch (value) {
            case "1":
                return BY_NAME;
            case "2":
                return BY_ID;
            default:
                throw new InputMismatchException();
        }
    }
}

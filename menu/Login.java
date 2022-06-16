package banking.menu;

import java.util.Arrays;

public enum Login {
    CREAT_AN_ACCOUNT(1, "1. Create an account"),
    LOG_INTO_ACCOUNT(2, "2. Log into account"),
    EXIT(0, "0. Exit");

    final int number;
    final String item;

    Login(int number, String item) {
        this.number = number;
        this.item = item;
    }

    public static Login getLogin(int number) {
        for (Login login : Login.values())
            if (login.number == number)
                return login;
        return null;
    }

    public static void printMenu() {
        Arrays.stream(Login.values()).map(login -> login.item).forEach(System.out::println);
    }

}

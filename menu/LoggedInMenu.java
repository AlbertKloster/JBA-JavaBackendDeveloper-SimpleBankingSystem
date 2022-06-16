package banking.menu;

import java.util.Arrays;

public enum LoggedInMenu {
    BALANCE(1, "1. Balance"),
    ADD_INCOME(2, "2. Add income"),
    DO_TRANSFER(3, "3. Do transfer"),
    CLOSE_ACCOUNT(4, "4. Close account"),
    LOG_OUT(5, "5. Log out"),
    EXIT(0, "0. Exit");

    final int number;
    final String item;

    LoggedInMenu(int number, String item) {
        this.number = number;
        this.item = item;
    }

    public static LoggedInMenu getLoggedInMenu(int number) {
        for (LoggedInMenu loggedInMenu : LoggedInMenu.values())
            if (loggedInMenu.number == number)
                return loggedInMenu;
        return null;
    }

    public static void printMenu() {
        Arrays.stream(LoggedInMenu.values()).map(loggedInMenu -> loggedInMenu.item).forEach(System.out::println);
    }
}

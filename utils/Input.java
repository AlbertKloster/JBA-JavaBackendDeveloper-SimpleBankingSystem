package banking.utils;

import banking.menu.LoggedInMenu;
import banking.menu.Login;

import java.util.Scanner;

public class Input {

    private static final Scanner SC = new Scanner(System.in);

    public static Login getLogin() {
        String input = SC.nextLine();
        if (input.matches("[0-2]"))
            return Login.getLogin(Integer.parseInt(input));
        throw new RuntimeException();
    }

    public static String getCardNumber() {
        String input = SC.nextLine();
        if (input.matches("\\d{16}"))
            return input;
        return null;
    }

    public static String getPin() {
        String input = SC.nextLine();
        if (input.matches("\\d{4}"))
            return input;
        return null;
    }

    public static LoggedInMenu getLoggedInMenu() {
        String input = SC.nextLine();
        if (input.matches("[0-5]"))
            return LoggedInMenu.getLoggedInMenu(Integer.parseInt(input));
        throw new RuntimeException();
    }

    public static String getIncomeToAdd() {
        String input = SC.nextLine();
        if (input.matches("\\d+"))
            return input;
        throw new RuntimeException();
    }
}

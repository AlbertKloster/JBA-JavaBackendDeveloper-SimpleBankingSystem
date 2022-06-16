package banking.controller;

import banking.menu.Login;
import banking.utils.Input;

import static banking.menu.Login.*;

public class LoginController {
    public static void run() throws Exception {
        boolean exit = false;
        while (!exit) {
            Login.printMenu();
            Login login = Input.getLogin();

            if (login == CREAT_AN_ACCOUNT)
                CreateAnAccountController.run();

            if (login == LOG_INTO_ACCOUNT)
                exit = LogIntoAccountController.run();

            if (login == EXIT || exit) {
                System.out.println("Bye!");
                return;
            }
        }
    }

}

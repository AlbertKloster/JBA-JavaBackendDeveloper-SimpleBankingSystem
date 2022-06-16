package banking.controller;

import banking.data.Repository;
import banking.utils.Input;

public class LogIntoAccountController {
    public static boolean run() {
        Repository repository = Repository.getInstance();

        System.out.println("Enter your card number:");
        String number = Input.getCardNumber();

        System.out.println("Enter your PIN:");
        String pin = Input.getPin();

        if (number != null && pin != null)
            if (repository.getPin(number).equals(pin))
                return LoggedInController.run(number);

        System.out.println("Wrong card number or PIN!");
        return false;
    }
}

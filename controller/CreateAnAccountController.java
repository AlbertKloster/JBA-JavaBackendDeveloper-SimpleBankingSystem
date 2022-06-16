package banking.controller;

import banking.data.Account;
import banking.data.Repository;
import banking.utils.NumberGenerator;

public class CreateAnAccountController {

    public static void run() {
        Repository repository = Repository.getInstance();
        String cardNumber = NumberGenerator.generateCardNumber();
        String pin = NumberGenerator.generatePin();
        repository.save(new Account(cardNumber, pin));
        System.out.printf("Your card has been created\nYour card number:\n%s\nYour card PIN:\n%s\n", cardNumber, pin);
    }
}

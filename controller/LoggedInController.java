package banking.controller;

import banking.data.Repository;
import banking.menu.LoggedInMenu;
import banking.utils.Input;
import banking.utils.LuhnAlgorithm;

public class LoggedInController {

    private static final Repository REPOSITORY = Repository.getInstance();

    public static boolean run(String cardNumber) {
        System.out.println("You have successfully logged in!");
        while (true) {
            LoggedInMenu.printMenu();
            LoggedInMenu loggedInMenu = Input.getLoggedInMenu();

            if (loggedInMenu.equals(LoggedInMenu.BALANCE))
                showBalance(cardNumber);

            if (loggedInMenu.equals(LoggedInMenu.ADD_INCOME))
                addIncome(cardNumber);

            if (loggedInMenu.equals(LoggedInMenu.DO_TRANSFER))
                doTransfer(cardNumber);

            if (loggedInMenu.equals(LoggedInMenu.CLOSE_ACCOUNT))
                closeAccount(cardNumber);

            if (loggedInMenu.equals(LoggedInMenu.LOG_OUT)) {
                System.out.println("You have successfully logged out!");
                return false;
            }
            if (loggedInMenu.equals(LoggedInMenu.EXIT))
                return true;
        }

    }

    private static void showBalance(String cardNumber) {
        System.out.printf("Balance: %s\n", REPOSITORY.getBalance(cardNumber));
    }

    private static void addIncome(String cardNumber) {
        REPOSITORY.addIncome(Integer.parseInt(Input.getIncomeToAdd()), cardNumber);
    }

    private static void closeAccount(String cardNumber) {
        REPOSITORY.delete(cardNumber);
    }

    private static void doTransfer(String cardNumber) {
        System.out.println("Transfer");
        System.out.println("Enter card number:");
        String cardNumberToTransfer = Input.getCardNumber();

        if (cardNumberToTransfer == null) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
            return;
        }

        if (LuhnAlgorithm.isNotLuhnNumber(cardNumberToTransfer)) {
            System.out.println("Probably you made a mistake in the card number. Please try again!");
            return;
        }


        if (notExists(cardNumberToTransfer)) {
            System.out.println("Such a card does not exist.");
            return;
        }

        System.out.println("Enter how much money you want to transfer:");
        String moneyToTransfer = Input.getIncomeToAdd();

        if (isNotEnoughMoney(cardNumber, moneyToTransfer)) {
            System.out.println("Not enough money!");
            return;
        }

        performTransfer(cardNumber, cardNumberToTransfer, moneyToTransfer);
   }

    private static int getRemainingMoney(String cardNumber, String moneyToTransfer) {
        return Integer.parseInt(REPOSITORY.getBalance(cardNumber)) - Integer.parseInt(moneyToTransfer);
    }

    private static void performTransfer(String cardNumber, String cardNumberToTransfer, String moneyToTransfer) {
        REPOSITORY.transfer(cardNumber, cardNumberToTransfer, moneyToTransfer);
    }

    private static boolean isNotEnoughMoney(String cardNumber, String moneyToTransfer) {
        return getRemainingMoney(cardNumber, moneyToTransfer) < 0;
    }

    private static boolean notExists(String cardNumberToTransfer) {
        return !REPOSITORY.getAllCardNumbers().contains(cardNumberToTransfer);
    }

}

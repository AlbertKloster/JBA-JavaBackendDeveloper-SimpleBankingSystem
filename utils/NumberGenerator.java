package banking.utils;

import banking.data.Repository;

import java.util.List;
import java.util.Random;

public class NumberGenerator {

    private static final Repository repository = Repository.getInstance();

    public static String generateCardNumber() {
        if (repository.getAllCardNumbers().isEmpty()) {
            String firstAccountNumber = "400000844943340";
            return firstAccountNumber + LuhnAlgorithm.getChecksum(firstAccountNumber);
        } else {
            String cardNumber = "0";
            for (String cardNumberDb : getCardNumbers()) {
                String temp = cardNumberDb.substring(0, cardNumberDb.length() - 1);
                if (temp.compareTo(cardNumber) > 0) {
                    cardNumber = temp;
                }
            }
            cardNumber = String.valueOf(Long.parseLong(cardNumber) + 1);
            return cardNumber + LuhnAlgorithm.getChecksum(cardNumber);
        }
    }

    public static String generatePin() {
        String pin;
        while (true) {
            pin = getRandom();
            if (!checkPin(pin)) {
                return pin;
            }
        }
    }

    private static String getRandom() {
        Random random = new Random();
        StringBuilder randomNumber = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            randomNumber.append(random.nextInt(10));
        }
        return randomNumber.toString();
    }

    private static boolean checkPin(String pin) {
        for (String pinDb : getCardPins()) {
            if (pinDb.compareTo(pin) == 0) {
                return true;
            }
        }
        return false;
    }

    private static List<String> getCardNumbers() {
        return repository.getAllCardNumbers();
    }

    private static List<String> getCardPins() {
        return repository.getAllPins();
    }
}

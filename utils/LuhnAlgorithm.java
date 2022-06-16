package banking.utils;

public class LuhnAlgorithm {
    public static String getChecksum(String number) {
        int sum = 0;
        for (int i = 0; i < number.length(); i++) {

            int digit = Integer.parseInt(String.valueOf(number.charAt(i)));
            if (i % 2 == 0) {
                digit *= 2;
            }
            if (digit > 9) {
                digit -= 9;
                System.out.println();
            }
            sum += digit;

        }
        return String.valueOf(Math.min(10 - (sum % 10 == 0 ? 10 : sum % 10), 9));
    }

    public static boolean isNotLuhnNumber(String number) {
        String numberToCheck = number.substring(0, number.length() - 1);
        String lastDigit = number.substring(number.length() - 1);
        return !lastDigit.equals(getChecksum(numberToCheck));
    }
}

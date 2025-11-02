package lotto.util;

public class Validator {
    private static final int Lotto_Price = 1000;
    private static final String ERROR_HEADER = "[ERROR] ";

    private static final String ERROR_NOT_NUMERIC = ERROR_HEADER + "숫자를 입력해야 합니다.";
    private static final String ERROR_NOT_POSITIVE = ERROR_HEADER + "구입 금액은 1,000원 이상이어야 합니다.";
    private static final String ERROR_NOT_DIVISIBLE = ERROR_HEADER + "구입 금액은 1,000원 단위로 입력해야 합니다.";

    public static void validatePurchaseAmount(String input) {
        int amount = validateIsNumeric(input);
        validateIsPositive(amount);
        validateDivisibleByUnit(amount);
    }

    private static int validateIsNumeric(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(ERROR_NOT_NUMERIC);
        }
    }

    private static void validateIsPositive(int amount) {
        if (amount < Lotto_Price) {
            throw new NumberFormatException(ERROR_NOT_POSITIVE);
        }
    }

    private static void validateDivisibleByUnit(int amount) {
        if (amount % Lotto_Price != 0) {
            throw new NumberFormatException(ERROR_NOT_DIVISIBLE);
        }
    }
}

package lotto.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Validator {
    private static final int Lotto_Price = 1000;
    private static final String ERROR_HEADER = "[ERROR] ";

    private static final String ERROR_NOT_NUMERIC = ERROR_HEADER + "숫자를 입력해야 합니다.";
    private static final String ERROR_NOT_POSITIVE = ERROR_HEADER + "구입 금액은 1,000원 이상이어야 합니다.";
    private static final String ERROR_NOT_DIVISIBLE = ERROR_HEADER + "구입 금액은 1,000원 단위로 입력해야 합니다.";
    private static final String ERROR_INVALID_WINNING_NUMBER_COUNT = ERROR_HEADER + "당첨 번호는 6개여야 합니다.";
    private static final String ERROR_INVALID_NUMBER_RANGE = ERROR_HEADER + "로또 번호는 1부터 45 사이의 숫자여야 합니다.";
    private static final String ERROR_DUPLICATE_WINNING_NUMBERS = ERROR_HEADER + "당첨 번호는 중복될 수 없습니다.";
    private static final String ERROR_BONUS_NUMBER_DUPLICATE = ERROR_HEADER + "보너스 번호는 당첨 번호와 중복될 수 없습니다.";


    public static void validatePurchaseAmount(String input) {
        int amount = validateIsNumeric(input);
        validateIsPositive(amount);
        validateDivisibleByUnit(amount);
    }

    public static List<Integer> validateWinningNumbers(String input) {
        List<String> stringNumbers = Arrays.asList(input.split(","));

        if (stringNumbers.size() != 6) {
            throw new NumberFormatException(ERROR_INVALID_WINNING_NUMBER_COUNT);
        }

        List<Integer> numbers = stringNumbers.stream()
                .map(Validator::validateIsNumeric)
                .toList();

        validateNumbersRange(numbers);

        validateNoDuplicateNumbers(numbers);

        return numbers;
    }

    public static int validateBonusNumber(String input, List<Integer> winningNumbers) {
        // 1. 숫자인지 검증
        int bonusNumber = validateIsNumeric(input);

        // 2. 1~45 범위 검증
        validateNumberRange(bonusNumber);

        // 3. 당첨 번호와 중복되는지 검증
        if (winningNumbers.contains(bonusNumber)) {
            throw new IllegalArgumentException(ERROR_BONUS_NUMBER_DUPLICATE);
        }

        return bonusNumber;
    }

    private static int validateIsNumeric(String input) {
        try {
            return Integer.parseInt(input.trim());
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


    private static void validateNumbersRange(List<Integer> numbers) {
        for (int number : numbers) {
            validateNumberRange(number); // 2. 아래의 '숫자 하나' 검증 메서드를 호출
        }
    }

    private static void validateNumberRange(int number) {
        if (number < 1 || number > 45) {
            throw new IllegalArgumentException(ERROR_INVALID_NUMBER_RANGE);
        }
    }

    private static void validateNoDuplicateNumbers(List<Integer> numbers) {
        Set<Integer> uniqueNumbers = new HashSet<>(numbers);
        if (uniqueNumbers.size() != numbers.size()) {
            throw new IllegalArgumentException(ERROR_DUPLICATE_WINNING_NUMBERS);
        }
    }

}

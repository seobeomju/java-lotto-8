package lotto;
import java.util.List;
import lotto.util.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test; // 기본 @Test 어노테이션
import org.junit.jupiter.params.ParameterizedTest; // 파라미터화 테스트
import org.junit.jupiter.params.provider.ValueSource; // 파라미터 값 제공
import static org.assertj.core.api.Assertions.assertThat;
// AssertJ의 static import
import static org.assertj.core.api.Assertions.assertThatThrownBy; // 예외 테스트용
import static org.assertj.core.api.Assertions.assertThatNoException;

public class ValidatorTest {

    @DisplayName("구입 금액이 숫자가 아니면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"1000a", "abc", " ", ""})
    void validatePurchaseAmount_NotNumeric(String input) {
        assertThatThrownBy(() -> Validator.validatePurchaseAmount(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 숫자를 입력해야 합니다.");
    }

    @DisplayName("구입 금액이 1,000원 미만이면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"0", "500", "999", "-1000"})
    void validatePurchaseAmount_NotPositive(String input) {
        assertThatThrownBy(() -> Validator.validatePurchaseAmount(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 구입 금액은 1,000원 이상이어야 합니다.");
    }

    @DisplayName("구입 금액이 1,000원 단위가 아니면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"1500", "2001", "1001"})
    void validatePurchaseAmount_NotDivisible(String input) {
        assertThatThrownBy(() -> Validator.validatePurchaseAmount(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 구입 금액은 1,000원 단위로 입력해야 합니다.");
    }

    @DisplayName("유효한 구입 금액은 예외를 발생시키지 않는다.")
    @ParameterizedTest
    @ValueSource(strings = {"1000", "8000", "15000"})
    void validatePurchaseAmount_Success(String input) {
        assertThatNoException()
                .isThrownBy(() -> Validator.validatePurchaseAmount(input));
    }
    @DisplayName("당첨 번호가 쉼표(,)로 구분되지 않거나 6개가 아니면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "1,2,3,4,5",     // 5개
            "1,2,3,4,5,6,7", // 7개
            "1.2.3.4.5.6"    // 쉼표 구분 아님
    })
    void validateWinningNumbers_InvalidCountOrFormat(String input) {
        assertThatThrownBy(() -> Validator.validateWinningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 당첨 번호는 6개여야 합니다.");
    }

    @DisplayName("당첨 번호에 숫자가 아닌 값이 포함되면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"1,2,3,4,5,a", "1,2,3,4, ,6"})
    void validateWinningNumbers_NotNumeric(String input) {
        assertThatThrownBy(() -> Validator.validateWinningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 숫자를 입력해야 합니다.");
    }

    @DisplayName("당첨 번호에 1~45 범위를 벗어난 숫자가 있으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"1,2,3,4,5,46", "1,2,3,4,5,0"})
    void validateWinningNumbers_InvalidRange(String input) {
        assertThatThrownBy(() -> Validator.validateWinningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.");
    }

    @DisplayName("당첨 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    @Test
    void validateWinningNumbers_DuplicateNumbers() {
        String input = "1,2,3,4,5,5";
        assertThatThrownBy(() -> Validator.validateWinningNumbers(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 당첨 번호는 중복될 수 없습니다.");
    }

    @DisplayName("유효한 당첨 번호(공백 포함)는 검증을 통과하고 숫자 리스트를 반환한다.")
    @Test
    void validateWinningNumbers_SuccessWithSpaces() {
        String input = " 1, 2, 3, 4, 5, 6 ";
        List<Integer> expected = List.of(1, 2, 3, 4, 5, 6);

        assertThat(Validator.validateWinningNumbers(input)).isEqualTo(expected);
    }

    // --- ⬇️ 4단계: 보너스 번호 검증 테스트 ⬇️ ---

    @DisplayName("보너스 번호가 숫자가 아니거나 1~45 범위를 벗어나면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"a", "0", "46"})
    void validateBonusNumber_InvalidFormatOrRange(String input) {
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);

        assertThatThrownBy(() -> Validator.validateBonusNumber(input, winningNumbers))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("보너스 번호가 당첨 번호와 중복되면 예외가 발생한다.")
    @Test
    void validateBonusNumber_DuplicateWithWinningNumbers() {
        String input = "6"; // 당첨 번호 6과 중복
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);

        assertThatThrownBy(() -> Validator.validateBonusNumber(input, winningNumbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 보너스 번호는 당첨 번호와 중복될 수 없습니다.");
    }

    @DisplayName("유효한 보너스 번호는 검증을 통과하고 숫자를 반환한다.")
    @Test
    void validateBonusNumber_Success() {
        String input = "7";
        List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);

        assertThat(Validator.validateBonusNumber(input, winningNumbers)).isEqualTo(7);
    }

}

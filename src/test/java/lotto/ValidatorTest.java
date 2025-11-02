package lotto;
import lotto.util.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test; // 기본 @Test 어노테이션
import org.junit.jupiter.params.ParameterizedTest; // 파라미터화 테스트
import org.junit.jupiter.params.provider.ValueSource; // 파라미터 값 제공

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

}

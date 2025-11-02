package lotto;

import java.util.stream.Stream;
import lotto.domain.Lotto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LottoTest {
    @Test
    void 로또_번호의_개수가_6개가_넘어가면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 6, 7)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호에 중복된 숫자가 있으면 예외가 발생한다.")
    @Test
    void 로또_번호에_중복된_숫자가_있으면_예외가_발생한다() {
        assertThatThrownBy(() -> new Lotto(List.of(1, 2, 3, 4, 5, 5)))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("로또 번호가 1~45 범위를 벗어나면 예외가 발생한다.")
    @ParameterizedTest
    @MethodSource("invalidRangeNumbers") // 아래 static 메서드에서 테스트 케이스를 가져옴
    void createLotto_InvalidRange(List<Integer> numbers) {
        assertThatThrownBy(() -> new Lotto(numbers))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("[ERROR] 로또 번호는 1부터 45 사이의 숫자여야 합니다.");
    }

    // createLotto_InvalidRange 테스트를 위한 데이터
    private static Stream<Arguments> invalidRangeNumbers() {
        return Stream.of(
                Arguments.of(List.of(1, 2, 3, 4, 5, 0)),  // 0 포함
                Arguments.of(List.of(1, 2, 3, 4, 5, 46)), // 46 포함
                Arguments.of(List.of(1, 2, 3, 4, 5, -1))  // 음수 포함
        );
    }
    // TODO: 추가 기능 구현에 따른 테스트 코드 작성
}

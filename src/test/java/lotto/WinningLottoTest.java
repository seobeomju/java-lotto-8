package lotto;

import lotto.domain.Lotto;
import lotto.domain.Rank;
import lotto.domain.WinningLotto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class WinningLottoTest {

    private WinningLotto winningLotto;
    private final List<Integer> winningNumbers = List.of(1, 2, 3, 4, 5, 6);
    private final int bonusNumber = 7;

    @BeforeEach
    void setUp() {
        winningLotto = new WinningLotto(winningNumbers, bonusNumber);
    }

    @Test
    @DisplayName("1등(6개 일치)을 정확히 판별한다.")
    void calculateRank_First() {
        Lotto userLotto = new Lotto(List.of(1, 2, 3, 4, 5, 6));
        assertThat(winningLotto.calculateRank(userLotto)).isEqualTo(Rank.FIRST);
    }

    @Test
    @DisplayName("2등(5개 + 보너스 일치)을 정확히 판별한다.")
    void calculateRank_Second() {
        Lotto userLotto = new Lotto(List.of(1, 2, 3, 4, 5, 7)); // 보너스(7) 포함
        assertThat(winningLotto.calculateRank(userLotto)).isEqualTo(Rank.SECOND);
    }

    @Test
    @DisplayName("3등(5개 일치)을 정확히 판별한다.")
    void calculateRank_Third() {
        Lotto userLotto = new Lotto(List.of(1, 2, 3, 4, 5, 8)); // 보너스(7) 미포함
        assertThat(winningLotto.calculateRank(userLotto)).isEqualTo(Rank.THIRD);
    }

    @Test
    @DisplayName("보너스 번호가 당첨 번호와 중복되면 예외가 발생한다.")
    void createWinningLotto_DuplicateBonus() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6);
        int invalidBonus = 6; // 당첨 번호와 중복

        assertThatThrownBy(() -> new WinningLotto(numbers, invalidBonus))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
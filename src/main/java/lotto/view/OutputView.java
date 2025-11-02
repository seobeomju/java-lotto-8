package lotto.view;


import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import lotto.domain.Lotto;
import lotto.domain.Rank;
import lotto.service.Statistics;

public class OutputView {
    private static final List<Rank> DISPLAY_RANKS_ORDER = List.of(
            Rank.FIFTH,
            Rank.FOURTH,
            Rank.THIRD,
            Rank.SECOND,
            Rank.FIRST
    );
    private static final Map<Rank, String> RANK_DESCRIPTIONS = Map.of(
            Rank.FIFTH, "3개 일치",
            Rank.FOURTH, "4개 일치",
            Rank.THIRD, "5개 일치",
            Rank.SECOND, "5개 일치, 보너스 볼 일치",
            Rank.FIRST, "6개 일치"
    );

    public void printError(String message) {
        System.out.println(message);
    }

    public void printLottos(List<Lotto> lottos) {
        System.out.println();
        System.out.println(lottos.size() + "개를 구매했습니다.");
        for (Lotto lotto : lottos) {
            System.out.println(lotto.getNumbers());
        }
        System.out.println();
    }

    public void printStatistics(Statistics statistics, double profitRate) {
        System.out.println("당첨 통계");
        System.out.println("---");

        printRankDetails(statistics, NumberFormat.getInstance());

        System.out.printf("총 수익률은 %.1f%%입니다.\n", profitRate);
    }

    private void printRankDetails(Statistics statistics, NumberFormat formatter) {
        // 2. List 선언이 사라지고 클래스 상수를 사용
        for (Rank rank : DISPLAY_RANKS_ORDER) {
            String description = RANK_DESCRIPTIONS.get(rank);
            System.out.printf("%s (%s원) - %d개\n",
                    description,
                    formatter.format(rank.getPrizeMoney()),
                    statistics.getCount(rank)
            );
        }
    }
}

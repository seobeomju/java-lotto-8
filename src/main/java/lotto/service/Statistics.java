package lotto.service;

import lotto.domain.Rank;
import java.util.EnumMap;
import java.util.Map;

public class Statistics {
    private final Map<Rank, Integer> rankCounts;

    public Statistics() {
        this.rankCounts = new EnumMap<>(Rank.class);
        for (Rank rank : Rank.values()) {
            rankCounts.put(rank, 0);
        }
    }

    public void record(Rank rank) {
        rankCounts.put(rank, rankCounts.get(rank) + 1);
    }

    public int getCount(Rank rank) {
        return rankCounts.get(rank);
    }

    public long getTotalPrize() {
        long totalPrize = 0L;
        for (Map.Entry<Rank, Integer> entry : rankCounts.entrySet()) {
            Rank rank = entry.getKey();
            int count = entry.getValue();
            totalPrize += rank.getPrizeMoney() * count;
        }
        return totalPrize;
    }

    public double calculateProfitRate(int purchaseAmount) {
        long totalPrize = getTotalPrize();
        if (purchaseAmount == 0) {
            return 0.0;
        }

        double rate = (double) totalPrize / purchaseAmount * 100.0;
        return Math.round(rate * 10.0) / 10.0;
    }
}

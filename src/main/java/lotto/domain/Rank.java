package lotto.domain;

import java.util.Arrays;

public enum Rank {
    MISS(0, 0L),
    FIFTH(3, 5_000L),
    FOURTH(4, 50_000L),
    THIRD(5, 1_500_000L),
    SECOND(5, 30_000_000L),
    FIRST(6, 2_000_000_000);

    private final int matchCount;
    private final long prizeMoney;


    Rank(int matchCount, long prizeMoney) {
        this.matchCount = matchCount;
        this.prizeMoney = prizeMoney;

    }

    public long getPrizeMoney() {
        return prizeMoney;
    }

    public static Rank valueOf(int matchCount, boolean hasBonus) {
        if (matchCount == SECOND.matchCount && hasBonus) {
            return SECOND;
        }
        return Arrays.stream(Rank.values())
                .filter(rank -> rank != SECOND)
                .filter(rank -> rank.matchCount == matchCount)
                .findFirst()
                .orElse(MISS);
    }

    public int getMatchCount() {
        return matchCount;
    }
}

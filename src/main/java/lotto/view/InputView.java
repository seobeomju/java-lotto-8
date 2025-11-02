package lotto.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    private static final String REQuest_PURCHASE_AMOUNT_MESSAGE = "구입금액을 입력해주세요.";
    private static final String REQUEST_WINNING_NUMBERS_MESSAGE = "당첨 번호를 입력해 주세요.";
    private static final String REQUEST_BONUS_NUMBER_MESSAGE = "보너스 번호를 입력해 주세요.";

    public String readPurchaseAmount() {
        System.out.println(REQuest_PURCHASE_AMOUNT_MESSAGE);
        return Console.readLine();
    }

    public String readWinningNumbers() {
        System.out.println(REQUEST_WINNING_NUMBERS_MESSAGE);
        return Console.readLine();
    }

    public String readBonusNumbers() {
        System.out.println(REQUEST_BONUS_NUMBER_MESSAGE);
        return Console.readLine();
    }
}

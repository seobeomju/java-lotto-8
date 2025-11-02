package lotto.view;

import camp.nextstep.edu.missionutils.Console;

public class InputView {

    private static final String REQuest_PURCHASE_AMOUNT_MESSAGE = "구입금액을 입력해주세요.";

    public String readPurchaseAmount(){
        System.out.println(REQuest_PURCHASE_AMOUNT_MESSAGE);
        return Console.readLine();
    }
}

package lotto.controller;

import lotto.util.Validator;
import lotto.view.InputView;
import lotto.view.OutputView;

public class LottoGameController {
    private final InputView inputView;
    private final OutputView outputView;

    public LottoGameController(){
        this.inputView = new InputView();
        this.outputView = new OutputView();
    }

    public void run() {
        int purchaseAmount = getValidPurchaseAmount();
    }

    private int getValidPurchaseAmount(){
        while(true){
            try{
                String input = inputView.readPurchaseAmount();

                Validator.validatePurchaseAmount(input);
                return Integer.parseInt(input);

            }catch (IllegalArgumentException e){
                outputView.printError(e.getMessage());
            }
        }
    }

}

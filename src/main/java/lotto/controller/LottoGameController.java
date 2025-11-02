package lotto.controller;

import java.util.List;
import lotto.domain.Lotto;
import lotto.domain.Rank;
import lotto.domain.WinningLotto;
import lotto.service.LottoMachine;
import lotto.util.Validator;
import lotto.view.InputView;
import lotto.view.OutputView;
import lotto.service.Statistics;

public class LottoGameController {
    private final InputView inputView;
    private final OutputView outputView;
    private final LottoMachine lottoMachine;

    public LottoGameController() {
        this.inputView = new InputView();
        this.outputView = new OutputView();
        this.lottoMachine = new LottoMachine();
    }

    public void run() {
        int purchaseAmount = getValidPurchaseAmount();

        List<Lotto> lottos = lottoMachine.issueLottos(purchaseAmount);
        outputView.printLottos(lottos);

        List<Integer> winningNumbers = getValidWinningNumbers();

        int bonusNumber = getValidBonusNumber(winningNumbers);
        WinningLotto winningLotto = new WinningLotto(winningNumbers, bonusNumber);
        Statistics statistics = new Statistics();

        for (Lotto lotto : lottos) {
            Rank rank = winningLotto.calculateRank(lotto);
            statistics.record(rank);
        }

        double profitRate = statistics.calculateProfitRate(purchaseAmount);
        outputView.printStatistics(statistics, profitRate);
    }

    private int getValidPurchaseAmount() {
        while (true) {
            try {
                String input = inputView.readPurchaseAmount();

                Validator.validatePurchaseAmount(input);
                return Integer.parseInt(input);

            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private List<Integer> getValidWinningNumbers() {
        while (true) {
            try {
                String input = inputView.readWinningNumbers();
                return Validator.validateWinningNumbers(input);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

    private int getValidBonusNumber(List<Integer> winningNumbers) {
        while (true) {
            try {
                String input = inputView.readBonusNumbers();
                return Validator.validateBonusNumber(input, winningNumbers);
            } catch (IllegalArgumentException e) {
                outputView.printError(e.getMessage());
            }
        }
    }

}

package lotto.view;

import lotto.Number;
import lotto.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OutputView {

    private static final String OUTPUT_MESSAGE_HOW_MANY_LOTTOS_BOUGHT = "%d개를 구매했습니다.\n";
    private static final String OUTPUT_MESSAGE_LOTTO_RESULT_ANNOUNCE = "\n당첨 통계\n" + "---------";
    private static final String OUTPUT_MESSAGE_PROFIT_ANNOUNCE = "\n총 수익률은 %.3f 입니다. \n";
    private static final String OUTPUT_MESSAGE_X_NUMBERS_MATCH = "%d개 일치 (%d원)- %d개\n";
    private static final String OUTPUT_MESSAGE_X_NUMBERS_AND_BONUS_MATCH = "%d개 일치, 보너스 볼 일치(%d원)- %d개\n";
    private static final String NUMBER_CONNECTOR = ", ";
    private static final String NUMBER_START_SIGNAL = "[";
    private static final String NUMBER_END_SIGNAL = "]";
    private static final String END_LINE_SIGNAL = "\n";

    public void showHowManyLottosBoughtWithMoney(Lottos lottos) {
        int count = 0;
        if (Objects.nonNull(lottos)) {
            count = lottos.count();
        }
        System.out.printf(OUTPUT_MESSAGE_HOW_MANY_LOTTOS_BOUGHT, count);
    }

    public void showLottos(Lottos lottos) {
        StringBuilder result = new StringBuilder();
        for (Lotto lotto : lottos.getLottos()) {
            Numbers numbers = lotto.getLottoNumbers();
            result.append(buildNumbers(numbers)).append(END_LINE_SIGNAL);
        }

        System.out.println(result);
    }

    public StringBuilder buildNumbers(Numbers numbers) {
        StringBuilder result = new StringBuilder();
        List<Number> numberList = numbers.getNumbers();
        List<String> stringNumbers = new ArrayList<>();

        for (Number number : numberList) {
            stringNumbers.add(String.valueOf(number.getNumber()));
        }

        String numbersToString = String.join(NUMBER_CONNECTOR, stringNumbers);
        result.append(NUMBER_START_SIGNAL);
        result.append(numbersToString);
        result.append(NUMBER_END_SIGNAL);

        return result;
    }

    public void showMatchCount(Map<Rank, Integer> matchCounts) {
        System.out.println(OUTPUT_MESSAGE_LOTTO_RESULT_ANNOUNCE);

        for(Rank rank : Rank.LOTTO_WINS) {
            showMatchCount(rank, matchCounts.get(rank));
        }
    }

    private void showMatchCount(Rank rank, Integer matchCount) {
        if (rank == Rank.SECOND) {
            System.out.printf(
                    OUTPUT_MESSAGE_X_NUMBERS_AND_BONUS_MATCH,
                    rank.numberOfMatch(),
                    rank.winPrice().getMoney().longValue(),
                    matchCount
            );
            return;
        }

        System.out.printf(
                OUTPUT_MESSAGE_X_NUMBERS_MATCH,
                rank.numberOfMatch(),
                rank.winPrice().getMoney().longValue(),
                matchCount
        );
    }

    public void showLottoProfit(BigDecimal profit) {
        System.out.printf(OUTPUT_MESSAGE_PROFIT_ANNOUNCE, profit.doubleValue());
    }

}

package yoo.study.refactoring2nd.statement;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class Statement {
	public String statement(Invoice invoice, PlaysMap plays) {
		int totalAmount = 0;
		int volumeCredits = 0;
		String result = "청구 내역 (고객명: " + invoice.getCustomer() + ")\n";
		Currency currency = Currency.getInstance("USD");
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		format.setCurrency(currency);
		format.setMinimumFractionDigits(2);

		for (Performance perf : invoice.getPerformances()) {
			Play play = plays.getPlayById(perf.getPlayID());
			int thisAmount = amountFor(perf, play);

			// 포인트를 적립한다.
			volumeCredits += Math.max(perf.getAudience() - 30, 0);
			// 희극 관객 5명마다 추가 포인트를 적립한다.
			if ("comedy".equals(play.getType())) {
				volumeCredits += Math.floor(perf.getAudience() / 5);
			}

			// 청구 내역을 출력한다.
			result += String.format("%s: %s (%d석)\n", play.getName(), format.format(thisAmount / 100), perf.getAudience());
			totalAmount += thisAmount;
		}
		result += String.format("총액: %s\n", format.format(totalAmount / 100));
		result += String.format("적립 포인트: %d점\n", volumeCredits);

		return result;
	}

	private int amountFor(Performance perf, Play play) {
		int thisAmount = 0;
		switch (play.getType()) {
			case "tragedy":
				thisAmount = 40000;
				if (perf.getAudience() > 30) {
					thisAmount += 1000 * (perf.getAudience() - 30);
				}
				break;
			case "comedy":
				thisAmount = 30000;
				if (perf.getAudience() > 20) {
					thisAmount += 10000 + 500 * (perf.getAudience() - 20);
				}
				thisAmount += 300 * perf.getAudience();
				break;
			default:
				throw new RuntimeException("알 수 없는 장르: " + play.getType());
		}
		return thisAmount;
	}
}

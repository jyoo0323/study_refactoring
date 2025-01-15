package yoo.study.refactoring2nd.statement;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class Statement {
	private final Invoice invoice;
	private final PlaysMap plays;

	public Statement(Invoice invoice, PlaysMap plays) {
		this.invoice = invoice;
		this.plays = plays;
	}

	public String statement() {
		int totalAmount = 0;
		int volumeCredits = 0;
		String result = "청구 내역 (고객명: " + invoice.getCustomer() + ")\n";
		Currency currency = Currency.getInstance("USD");
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		format.setCurrency(currency);
		format.setMinimumFractionDigits(2);

		for (Performance perf : invoice.getPerformances()) {

			// 포인트를 적립한다.
			volumeCredits += Math.max(perf.getAudience() - 30, 0);
			// 희극 관객 5명마다 추가 포인트를 적립한다.
			if ("comedy".equals(playFor(perf).getType())) {
				volumeCredits += Math.floor(perf.getAudience() / 5);
			}

			// 청구 내역을 출력한다.
			result += String.format("%s: %s (%d석)\n", playFor(perf).getName(), format.format(amountFor(perf) / 100), perf.getAudience());
			totalAmount += amountFor(perf);
		}
		result += String.format("총액: %s\n", format.format(totalAmount / 100));
		result += String.format("적립 포인트: %d점\n", volumeCredits);

		return result;
	}

	private int amountFor(Performance aPerformance) {
		int result = 0;
		switch (playFor(aPerformance).getType()) {
			case "tragedy":
				result = 40000;
				if (aPerformance.getAudience() > 30) {
					result += 1000 * (aPerformance.getAudience() - 30);
				}
				break;
			case "comedy":
				result = 30000;
				if (aPerformance.getAudience() > 20) {
					result += 10000 + 500 * (aPerformance.getAudience() - 20);
				}
				result += 300 * aPerformance.getAudience();
				break;
			default:
				throw new RuntimeException("알 수 없는 장르: " + playFor(aPerformance).getType());
		}
		return result;
	}

	private Play playFor(Performance aPerformance) {
		return plays.getPlayById(aPerformance.getPlayID());
	}
}

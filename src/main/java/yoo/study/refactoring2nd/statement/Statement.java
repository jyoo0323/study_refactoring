package yoo.study.refactoring2nd.statement;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Statement {
	private final Invoice invoice;
	private final Map<String, Play> plays;

	public Statement(Invoice invoice, Map<String, Play> plays) {
		this.invoice = invoice;
		this.plays = plays;
	}

	public String statement() {
		StatementData statementData = new StatementData();
		statementData.setCustomer(invoice.getCustomer());
		statementData.setPerformances(invoice.getPerformances());
		return renderPlainText(statementData);
	}

	private String renderPlainText(StatementData data) {
		String result = "청구 내역 (고객명: " + data.getCustomer() + ")\n";
		for (Performance perf : data.getPerformances()) {
			// 청구 내역을 출력한다.
			result += String.format("%s: %s (%d석)\n", playFor(perf).getName(), usd(amountFor(perf)), perf.getAudience());
		}

		result += String.format("총액: %s\n", usd(totalAmount(data.getPerformances())));
		result += String.format("적립 포인트: %d점\n", totalVolumeCredits(data.getPerformances()));
		return result;
	}

	private int totalAmount(List<Performance> performances) {
		int result = 0;
		for (Performance perf : performances) {
			result += amountFor(perf);
		}
		return result;
	}

	private int totalVolumeCredits(List<Performance> performances) {
		int result = 0;
		for (Performance perf : performances) {
			result += volumeCreditsFor(perf);
		}
		return result;
	}

	private int amountFor(Performance aPerformance) {
		int result;
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
		return plays.get(aPerformance.getPlayID());
	}

	private int volumeCreditsFor(Performance aPerformance) {
		int result = 0;
		result += Math.max(aPerformance.getAudience() - 30, 0);
		if ("comedy".equals(playFor(aPerformance).getType())) {
			result += Math.floor(aPerformance.getAudience() / 5);
		}
		return result;
	}

	private String usd(int aNumber) {
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		Currency currency = Currency.getInstance("USD");
		format.setCurrency(currency);
		format.setMinimumFractionDigits(2);
		return format.format(aNumber / 100); //단위 변환 로직도 usd 함수로 이동
	}
}

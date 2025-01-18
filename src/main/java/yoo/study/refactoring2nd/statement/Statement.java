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
		return renderPlainText(createStatementData(invoice, plays));
	}

	public StatementData createStatementData(Invoice invoice, Map<String, Play> plays) {
		StatementData statementData = new StatementData();
		statementData.setCustomer(invoice.getCustomer());
		statementData.setPerformances(invoice.getPerformances().stream().map(this::enrichPerformance).toList());
		statementData.setTotalAmount(totalAmount(statementData.getPerformances()));
		statementData.setTotalVolumeCredits(totalVolumeCredits(statementData.getPerformances()));
		return statementData;
	}

	private EnrichedPerformance enrichPerformance(Performance aPerformance) {
		return EnrichedPerformance.create(aPerformance, plays.get(aPerformance.getPlayID()));
	}

	private String renderPlainText(StatementData data) {
		String result = "청구 내역 (고객명: " + data.getCustomer() + ")\n";
		for (EnrichedPerformance perf : data.getPerformances()) {
			// 청구 내역을 출력한다.
			result += String.format("%s: %s (%d석)\n", perf.getPlay().getName(), usd(perf.getAmount()), perf.getAudience());
		}

		result += String.format("총액: %s\n", usd(data.getTotalAmount()));
		result += String.format("적립 포인트: %d점\n", data.getTotalVolumeCredits());
		return result;
	}

	private int totalAmount(List<EnrichedPerformance> performances) {
		return performances.stream().reduce(0, (total, perf) -> total + perf.getAmount(), Integer::sum);
	}

	private int totalVolumeCredits(List<EnrichedPerformance> performances) {
		return performances.stream().reduce(0, (total, perf) -> total + perf.getVolumeCredits(), Integer::sum);
	}

	private String usd(int aNumber) {
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		Currency currency = Currency.getInstance("USD");
		format.setCurrency(currency);
		format.setMinimumFractionDigits(2);
		return format.format(aNumber / 100); //단위 변환 로직도 usd 함수로 이동
	}
}

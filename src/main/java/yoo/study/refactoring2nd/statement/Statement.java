package yoo.study.refactoring2nd.statement;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Map;

public class Statement {
	private final Invoice invoice;
	private final Map<String, Play> plays;

	public Statement(Invoice invoice, Map<String, Play> plays) {
		this.invoice = invoice;
		this.plays = plays;
	}

	public String htmlStatement() {
		return renderHtml(
			StatementData.of(
				invoice,
				invoice.getPerformances()
					.stream()
					.map(
						perf -> EnrichedPerformance.create(perf, plays.get(perf.getPlayID()))
					).toList()
			));
	}

	private String renderHtml(StatementData data) {
		String result = "<h1>청구 내역 (고객명: " + data.getCustomer() + ")</h1>\n";
		result += "<table>\n";
		result += "<tr><th>연극</th><th>좌석 수</th><th>금액</th></tr>";
		for (EnrichedPerformance perf : data.getPerformances()) {
			result += String.format("<tr><td>%s</td><td>%d석</td>", perf.getPlay().getName(), perf.getAudience());
			result += String.format("<td>%s</td></tr>\n", usd(perf.getAmount()));
		}
		result += "</table>\n";
		result += String.format("<p>총액: <em>%s</em></p>\n", usd(data.getTotalAmount()));
		result += String.format("<p>적립 포인트: <em>%d</em>점</p>\n", data.getTotalVolumeCredits());

		return result;
	}

	public String statement() {
		return renderPlainText(
			StatementData.of(
				invoice,
				invoice.getPerformances()
					.stream()
					.map(
						perf -> EnrichedPerformance.create(perf, plays.get(perf.getPlayID()))
					).toList()
			));
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

	private String usd(int aNumber) {
		NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
		Currency currency = Currency.getInstance("USD");
		format.setCurrency(currency);
		format.setMinimumFractionDigits(2);
		return format.format(aNumber / 100); //단위 변환 로직도 usd 함수로 이동
	}
}

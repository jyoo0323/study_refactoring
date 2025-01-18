package yoo.study.refactoring2nd.statement;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StatementData {
	private String customer;
	private List<EnrichedPerformance> performances;
	private int totalAmount;
	private int totalVolumeCredits;

	public static StatementData of(Invoice invoice, List<EnrichedPerformance> performances) {
		return StatementData.builder()
			.customer(invoice.getCustomer())
			.performances(performances)
			.totalAmount(totalAmount(performances))
			.totalVolumeCredits(totalVolumeCredits(performances))
			.build();
	}

	private static int totalAmount(List<EnrichedPerformance> performances) {
		return performances.stream().reduce(0, (total, perf) -> total + perf.getAmount(), Integer::sum);
	}

	private static int totalVolumeCredits(List<EnrichedPerformance> performances) {
		return performances.stream().reduce(0, (total, perf) -> total + perf.getVolumeCredits(), Integer::sum);
	}
}

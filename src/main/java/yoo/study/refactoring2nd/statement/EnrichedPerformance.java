package yoo.study.refactoring2nd.statement;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EnrichedPerformance {
	private String playId;
	private Play play;
	private int audience;
	private int amount;
	private int volumeCredits;

	public static EnrichedPerformance create(Performance perf, Play play) {
		PerformanceCalculator calculator = PerformanceCalculator.create(perf, play);
		return EnrichedPerformance.builder()
			.playId(perf.getPlayID())
			.play(play)
			.audience(perf.getAudience())
			.amount(calculator.amountFor())
			.volumeCredits(calculator.volumeCreditsFor())
			.build();
	}
}

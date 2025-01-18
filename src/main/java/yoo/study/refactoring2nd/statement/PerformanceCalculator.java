package yoo.study.refactoring2nd.statement;

import lombok.Builder;

@Builder
public class PerformanceCalculator {
	private Performance performance;
	private Play play;

	public static PerformanceCalculator create(Performance perf, Play play) {
		return PerformanceCalculator.builder()
			.performance(perf)
			.play(play)
			.build();
	}
}

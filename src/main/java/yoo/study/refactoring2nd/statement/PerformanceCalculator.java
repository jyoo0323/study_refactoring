package yoo.study.refactoring2nd.statement;

import lombok.Builder;

@Builder
public class PerformanceCalculator {
	protected Performance performance;
	protected Play play;

	public static PerformanceCalculator create(Performance perf, Play play) {
		return switch (play.getType()) {
			case "tragedy" -> new TragedyCalculator(perf, play);
			case "comedy" -> new ComedyCalculator(perf, play);
			default -> throw new RuntimeException("알 수 없는 장르: " + play.getType());
		};
	}

	public int amountFor() {
		throw new UnsupportedOperationException("서브클래스에서 처리하도록 설계되었습니다.");
	}

	public int volumeCreditsFor() {
		return Math.max(performance.getAudience() - 30, 0);
	}
}

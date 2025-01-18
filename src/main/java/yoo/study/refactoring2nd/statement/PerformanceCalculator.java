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
		int result;
		switch (play.getType()) {
			case "tragedy":
				result = 40000;
				if (performance.getAudience() > 30) {
					result += 1000 * (performance.getAudience() - 30);
				}
				break;
			case "comedy":
				result = 30000;
				if (performance.getAudience() > 20) {
					result += 10000 + 500 * (performance.getAudience() - 20);
				}
				result += 300 * performance.getAudience();
				break;
			default:
				throw new RuntimeException("알 수 없는 장르: " + play.getType());
		}
		return result;
	}

	public int volumeCreditsFor() {
		int result = 0;
		result += Math.max(performance.getAudience() - 30, 0);
		if ("comedy".equals(play.getType())) {
			result += Math.floor(performance.getAudience() / 5);
		}
		return result;
	}
}

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
		return EnrichedPerformance.builder()
			.playId(perf.getPlayID())
			.play(play)
			.audience(perf.getAudience())
			.amount(amountFor(play, perf.getAudience()))
			.volumeCredits(volumeCreditsFor(play, perf.getAudience()))
			.build();
	}

	private static int amountFor(Play play, int audience) {
		int result;
		switch (play.getType()) {
			case "tragedy":
				result = 40000;
				if (audience > 30) {
					result += 1000 * (audience - 30);
				}
				break;
			case "comedy":
				result = 30000;
				if (audience > 20) {
					result += 10000 + 500 * (audience - 20);
				}
				result += 300 * audience;
				break;
			default:
				throw new RuntimeException("알 수 없는 장르: " + play.getType());
		}
		return result;
	}

	private static int volumeCreditsFor(Play play, int audience) {
		int result = 0;
		result += Math.max(audience - 30, 0);
		if ("comedy".equals(play.getType())) {
			result += Math.floor(audience / 5);
		}
		return result;
	}
}

package yoo.study.refactoring2nd.statement;

public class TragedyCalculator extends PerformanceCalculator {
	TragedyCalculator(Performance performance, Play play) {
		super(performance, play);
	}

	@Override
	public int amountFor() {
		int result = 40000;
		if (performance.getAudience() > 30) {
			result += 1000 * (performance.getAudience() - 30);
		}
		return result;
	}
}

package yoo.study.refactoring2nd.statement;

public class ComedyCalculator extends PerformanceCalculator {
	ComedyCalculator(Performance performance, Play play) {
		super(performance, play);
	}

	@Override
	public int amountFor() {
		int result = 30000;
		if (performance.getAudience() > 20) {
			result += 10000 + 500 * (performance.getAudience() - 20);
		}
		result += 300 * performance.getAudience();
		return result;
	}
}

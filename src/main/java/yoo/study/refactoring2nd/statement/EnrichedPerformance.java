package yoo.study.refactoring2nd.statement;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EnrichedPerformance {
	private String playId;
	private Play play;
	private int audience;
	private int amount;
}

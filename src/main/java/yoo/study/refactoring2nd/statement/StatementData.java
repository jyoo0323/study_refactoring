package yoo.study.refactoring2nd.statement;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatementData {
	private String customer;
	private List<EnrichedPerformance> performances;
	private int totalAmount;
}

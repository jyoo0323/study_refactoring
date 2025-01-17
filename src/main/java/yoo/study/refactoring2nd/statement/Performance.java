package yoo.study.refactoring2nd.statement;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class Performance implements Serializable {
	private String playID;
	private int audience;
}

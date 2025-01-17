package yoo.study.refactoring2nd.statement;

import java.io.Serializable;

import lombok.Getter;

@Getter
public class Play implements Serializable {
	private String name;
	private String type;
}

package yoo.study.refactoring2nd.statement;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Play implements Serializable {
	private String name;
	private String type;
}

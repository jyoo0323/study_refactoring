package yoo.study.refactoring2nd.statement;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice implements Serializable {
	private String customer;
	private List<Performance> performances;
}

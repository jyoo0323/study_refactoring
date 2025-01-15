package yoo.study.refactoring2nd.statement;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PlaysMap implements Serializable {
	@JsonProperty("hamlet")
	private Play hamlet;
	@JsonProperty("as-like")
	private Play asLike;
	@JsonProperty("othello")
	private Play othello;

	public Play getPlayById(String playId) {
		return switch (playId) {
			case "hamlet" -> hamlet;
			case "as-like" -> asLike;
			case "othello" -> othello;
			default -> null;
		};
	}
}

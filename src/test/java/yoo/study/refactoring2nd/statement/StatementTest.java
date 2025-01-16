package yoo.study.refactoring2nd.statement;

import static org.assertj.core.api.Assertions.*;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

class StatementTest {

	@Test
	void statementTest() {
		//given
		ObjectMapper objectMapper = new ObjectMapper();
		List<Invoice> invoice = null;
		Map<String, Play> plays = null;

		try {
			File invoiceFile = new File("src/main/resources/invoices.json");
			invoice = objectMapper.readValue(
				invoiceFile, new TypeReference<>() {
				}
			);

			File playsFile = new File("src/main/resources/plays.json");
			plays = objectMapper.readValue(
				playsFile, new TypeReference<>() {
				}
			);
		} catch (Exception e) {
			throw new RuntimeException("파일을 읽는 중 오류가 발생했습니다.", e);
		}

		//when
		Statement statement = new Statement(invoice.get(0), plays);
		String result = statement.statement();
		System.out.println(result);

		//then
		assertThat(result).isEqualTo("""
			청구 내역 (고객명: BigCo)
			Hamlet: $650.00 (55석)
			As You Like It: $580.00 (35석)
			Othello: $500.00 (40석)
			총액: $1,730.00
			적립 포인트: 47점
			""");
	}
}

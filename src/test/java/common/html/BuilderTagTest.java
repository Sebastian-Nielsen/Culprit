package common.html;

import org.junit.jupiter.api.Test;
import org.junit.rules.Timeout;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static common.fileOption.FileOption.KEY.ID;
import static common.html.HTML.*;
import static common.html.HTML.Tag.*;


class BuilderTagTest {

	@Test
	public void testing() {
		String result = new Builder()
				.open(HTML)
					.open(BODY, Map.of(Attribute.ID, "aside",   Attribute.HEIGHT, "20px"))
					.close(BODY)
					.open(DIV)
						.open(P)
						.close(P)
					.close(DIV)
				.close(HTML)
				.getResult();

		System.out.println(result);
	}

	@Test
	public void shouldWork() {
		new Builder()
				.open(HTML)
					.open(BODY)
					.close(BODY)
				.close(HTML);
	}

}
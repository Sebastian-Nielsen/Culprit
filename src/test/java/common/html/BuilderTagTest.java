package common.html;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static common.html.HTML.*;
import static common.html.HTML.Tag.*;


class BuilderTagTest {


	@Test
	public void testingHtmlBuilder() throws Exception {
		File contentFile = new File("content");
		String result = HtmlBuilder.buildDefaultIndexHtml(contentFile);
		System.out.println();
		System.out.println();
		System.out.println(result);
		System.out.println();
		System.out.println();
		System.out.println(formatHTML(result));
		System.out.println();
		System.out.println();
	}

	public static String formatHTML(String html) throws Exception {
		Document doc = Jsoup.parse(html, "", Parser.xmlParser());
		return doc.toString();
	}

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
				.toString();

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
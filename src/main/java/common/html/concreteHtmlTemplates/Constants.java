package common.html.concreteHtmlTemplates;

import common.html.HTML;
import common.html.HtmlBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static common.html.HTML.Attribute.*;
import static common.html.HTML.Tag.*;
import static common.html.HTML.Tag.LINK;
import static common.html.HTML.Tag.TITLE;
import static framework.Constants.Constants.CWD_NAME;

public class Constants {
	
	public static HtmlBuilder defaultHead = 
			new HtmlBuilder()
				.open(HEAD)
					.open(TITLE).setText("index").close(TITLE)
					.openSingle(META, Map.of(CHARSET,"utf-8"))
					.openSingle(LINK, defaultCssAttributes("global.css"))
				.close(HEAD);
	
	@org.jetbrains.annotations.Unmodifiable
	@Contract("_ -> new")
	public static @NotNull Map<common.html.HTML.Attribute, String> defaultCssAttributes(String hrefVal) {
		return Map.of(
				HREF, "/%s/%s".formatted(CWD_NAME, hrefVal),
				REL,  "stylesheet"
		);
	}

}
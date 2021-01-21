package common.htmlBuilderMacros;

import common.html.HTML;
import common.html.HtmlBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import static common.html.HTML.Attribute.*;
import static common.html.HTML.Tag.LINK;
import static common.html.HTML.Tag.SCRIPT;
import static common.html.htmlTemplatesStrategy.Helper.defaultCssAttributes;
import static framework.Main.RESOURCES_PATH;

public class HeadHtmlBuilderMacros {

	public static String preloadStylesheet(String hrefVal) {

		Map<HTML.Attribute, String> preloadAttributes =
				Map.of(REL,  "preload",
					   HREF, RESOURCES_PATH + hrefVal,
					   AS, "style"
				);

		return new HtmlBuilder()
							.openSingle(LINK, preloadAttributes)
							.openSingle(LINK, defaultCssAttributes(hrefVal))
							.toString();
	}

	public static String preloadJS(String srcVal) {

		Map<HTML.Attribute, String> preloadAttributes =
				Map.of( REL, "preload",
						SRC, RESOURCES_PATH + srcVal,
						AS, "script");

		return new HtmlBuilder()
								.open(SCRIPT, preloadAttributes).close(SCRIPT)
								.toString();
	}

}

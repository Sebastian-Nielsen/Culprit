package common.html.concreteHtmlTemplates;

import common.compilerFacade.CompilerDataContainer;
import common.html.htmlBuilder.HtmlBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Map;

import static common.html.HTML.Attribute.*;
import static common.html.HTML.Tag.*;
import static common.html.HTML.Tag.LINK;
import static common.html.HTML.Tag.TITLE;
import static framework.Constants.Constants.CWD_NAME;


public class Helper {


	public static String buildDefaultPageHtmlTemplateUsing(File contentFile, String articleTag,
	                                                       CompilerDataContainer dataContainer) {
		return new DefaultPageHtmlTemplate()
				.buildUsing(contentFile, articleTag, dataContainer.getFOContainerOf(contentFile));
	}

	public static HtmlBuilder defaultHeadTags =
			new HtmlBuilder()
					.open(TITLE).setText("index").close(TITLE)
					.openSingle(META, Map.of(CHARSET, "utf-8"))
					.openSingle(LINK, defaultCssAttributes("global.css"));



	public static @NotNull Map<common.html.HTML.Attribute, String> defaultCssAttributes(String hrefVal) {
		return Map.of(
				HREF, "/%s/%s".formatted(CWD_NAME, hrefVal),
				REL,  "stylesheet"
		);
	}


}

package common.html.htmlTemplatesStrategy.concreteStrategy;

import common.fileOption.FileOptionContainer;
import common.html.HtmlBuilder;
import common.html.HtmlFactory;
import common.html.htmlTemplatesStrategy.HtmlTemplateStrategy;

import java.io.File;
import java.util.Map;

import static common.fileOption.FileOption.KEY.KATEX;
import static common.html.HTML.Attribute.*;
import static common.html.HTML.Tag.*;
import static common.html.HTML.Tag.LINK;
import static common.html.HTML.Tag.STYLE;
import static common.html.htmlTemplatesStrategy.Helper.*;

public class DefaultPageHtmlTemplate {

	private final HtmlFactory htmlFactory;

	public DefaultPageHtmlTemplate(HtmlFactory htmlFactory) {
		this.htmlFactory = htmlFactory;
	}


	public String buildUsing(File contentFile, String articleTag, FileOptionContainer foContainer) throws Exception {

		File dirOfContentFile = contentFile.getParentFile();

		return new HtmlBuilder()
				.insert("<!DOCTYPE html>\n")
				.open(HTML)
					.open(HEAD)
						.insert(defaultHeadTags)
						.openSingle(LINK, defaultCssAttributes("css/defaultPage.css"))
						.insert(htmlFactory.createKatexHtml(foContainer))
						.open(SCRIPT, defaultScriptAttributes("css/defaultPage.js")).close(SCRIPT)
					.close(HEAD)
					.open(BODY)
						.open(MAIN)
							.open(ASIDE, Map.of(ID,  "left-aside")).close(ASIDE)
//							.open(ASIDE, Map.of(ID, "right-aside")).close(ASIDE)
							.open(ARTICLE)
								.insert(articleTag)
							.close(ARTICLE)
						.close(MAIN)
						.open(NAV)
							.insert(htmlFactory.createNavigationHtml(dirOfContentFile))
						.close(NAV)
					.close(BODY)
				.close(HTML)
				.toString();
	}


}

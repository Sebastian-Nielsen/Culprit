package common.html.concreteHtmlTemplates;

import common.fileOption.FileOptionContainer;
import common.html.HtmlBuilderStrategy;
import common.html.htmlBuilder.HtmlBuilder;
import common.html.htmlBuilder.NullHtmlBuilderStrategy;

import java.io.File;
import java.util.Map;

import static common.fileOption.FileOption.KEY.KATEX;
import static common.html.HTML.Attribute.*;
import static common.html.HTML.Tag.*;
import static common.html.HTML.Tag.LINK;
import static common.html.concreteHtmlTemplates.Helper.defaultHeadTags;

public class DefaultPageHtmlTemplate {

	public String buildUsing(File contentFile, String articleTag, FileOptionContainer foContainer) {

		return new HtmlBuilder()
				.open(HTML)
					.open(HEAD)
						.insertBuilder(defaultHeadTags)
						.insertBuilder(getKatexHtmlBuilderStrategy(foContainer))
					.close(HEAD)
					.open(BODY)
						.open(MAIN)
							.open(ASIDE, Map.of(ID,  "left-aside")).close(ASIDE)
							.open(ASIDE, Map.of(ID, "right-aside")).close(ASIDE)
							.open(ARTICLE)
								.insertRaw(articleTag)
							.close(ARTICLE)
						.close(MAIN)
					.close(BODY)
				.close(HTML)
				.toString();
	}

	private HtmlBuilderStrategy getKatexHtmlBuilderStrategy(FileOptionContainer foContainer) {
		boolean shouldUseKatexBuilder = foContainer.getOrDefault(KATEX, KATEX.defaultVal).equals("true");

		if (!shouldUseKatexBuilder)
			return new NullHtmlBuilderStrategy();
		else
			return new HtmlBuilder()
					.openSingle(LINK,
							Map.of(
									HREF, "https://cdn.jsdelivr.net/npm/katex@0.12.0/dist/katex.min.css",
									REL, "stylesheet",
									CROSSORIGIN, "anonymous"
							)
					)
					.open(SCRIPT,
							Map.of(
									DEFER, "",
									SRC, "https://cdn.jsdelivr.net/npm/katex@0.12.0/dist/katex.min.js",
									CROSSORIGIN, "anonymous"
							)
					).close(SCRIPT)
					.open(SCRIPT,
							Map.of(
									DEFER, "",
									SRC, "https://cdn.jsdelivr.net/npm/katex@0.12.0/dist/contrib/auto-render.min.js",
									CROSSORIGIN, "anonymous",
									ONLOAD, "renderMathInElement(document.body);"
							)
					).close(SCRIPT);
	}


}

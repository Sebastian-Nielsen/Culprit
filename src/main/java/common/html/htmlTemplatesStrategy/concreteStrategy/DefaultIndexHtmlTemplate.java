package common.html.htmlTemplatesStrategy.concreteStrategy;

import common.html.HtmlBuilder;
import common.html.HtmlFactory;

import java.io.File;
import java.io.IOException;

import static common.html.HTML.Tag.*;
import static common.html.htmlTemplatesStrategy.Helper.*;
import static framework.utils.FileUtils.Lister.listNonDirsFrom;

public class DefaultIndexHtmlTemplate {

	private final HtmlFactory htmlFactory;

	public DefaultIndexHtmlTemplate(HtmlFactory htmlFactory) {
		this.htmlFactory = htmlFactory;
	}

	/**
	 * The default html of an index file is dynamic:
	 * for each file in the dir of the index file in question,
	 * insert a {@code li} element pointing to that file.
	 * @return {@code toString} of {@code ArticleTag}
	 */
	public String buildUsing(File folder) throws Exception {
		return new HtmlBuilder()
				.insert("<!DOCTYPE html>\n")
				.open(HTML)
					.open(HEAD)
						.insert(defaultHeadTags)
					.close(HEAD)
					.open(BODY)
						.insert(htmlFactory.createNavigationHtml(folder))
					.close(BODY)
				.close(HTML)
				.toString();
	}

	/* === PRIVATE METHODS */
	/* ============================================================ */
}

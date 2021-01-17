package common.html.htmlTemplatesStrategy;

import common.html.htmlBuilderStrategy.HtmlBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static common.html.HTML.Attribute.HREF;
import static common.html.HTML.Tag.*;
import static common.html.htmlTemplatesStrategy.Helper.defaultHeadTags;
import static common.html.htmlTemplatesStrategy.Helper.generateOlTagListingOfFilesIn;
import static framework.utils.FileUtils.Lister.RECURSION.NONRECURSIVE;
import static framework.utils.FileUtils.Lister.listDirsFrom;
import static framework.utils.FileUtils.Lister.listNonDirsFrom;

public class DefaultIndexHtmlTemplate {
	/**
	 * The default html of an index file is dynamic:
	 * for each file in the dir of the index file in question,
	 * insert a {@code li} element pointing to that file.
	 * @return {@code toString} of {@code ArticleTag}
	 */
	public String buildUsing(File folder) throws Exception {
		return new HtmlBuilder()
				.insertRaw("<!DOCTYPE html>\n")
				.open(HTML)
					.open(HEAD)
						.insertBuilder(defaultHeadTags)
					.close(HEAD)
					.open(BODY)
						.insertBuilder(generateOlTagListingOfFilesIn(folder))
					.close(BODY)
				.close(HTML)
				.toString();
	}

	/* === PRIVATE METHODS */
	/* ============================================================ */
}

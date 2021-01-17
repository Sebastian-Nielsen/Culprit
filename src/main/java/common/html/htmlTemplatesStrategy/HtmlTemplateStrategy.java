package common.html.htmlTemplatesStrategy;

import common.fileOption.FileOptionContainer;

import java.io.File;

public interface HtmlTemplateStrategy {

	/**
	 * Insert the values supplied in the constructor into the template and return the values.
	 * @return complete html document formatted as a {@code String}
	 */
	String buildUsing(File contentFile, String articleTag, FileOptionContainer foContainer);
}

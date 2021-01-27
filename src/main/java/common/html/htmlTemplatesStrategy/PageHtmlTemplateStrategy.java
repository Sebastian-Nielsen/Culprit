package common.html.htmlTemplatesStrategy;

import common.compilerFacade.CompilerDataContainer;
import common.fileOption.FileOptionContainer;

import java.io.File;

public interface PageHtmlTemplateStrategy {

	/**
	 * Insert the values supplied in the constructor into the template and return the values.
	 * @return complete html document formatted as a {@code String}
	 */
	public String buildUsing(File contentFile, String articleTag, CompilerDataContainer dataContainer);
//	String buildUsing(File contentFile, String articleTag, FileOptionContainer foContainer) throws Exception;
}

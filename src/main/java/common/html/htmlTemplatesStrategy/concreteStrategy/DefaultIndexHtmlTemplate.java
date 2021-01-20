package common.html.htmlTemplatesStrategy.concreteStrategy;

import common.PostEffectDataContainer;
import common.compilerFacade.CompilerDataContainer;
import common.html.HtmlBuilder;
import common.html.HtmlFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static common.html.HTML.Tag.*;
import static common.html.htmlTemplatesStrategy.Helper.*;
import static common.preparatorFacade.Deployer.getDeployEquivalentOf;
import static framework.utils.FileUtils.Filename.relativePath;
import static framework.utils.FileUtils.Lister.listNonDirsFrom;
import static org.apache.commons.io.FilenameUtils.removeExtension;

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
	public String buildUsing(File indexFile, PostEffectDataContainer dataContainer) {
		return new HtmlBuilder()
				.insert("<!DOCTYPE html>\n")
				.open(HTML)
					.open(HEAD)
						.insert(defaultHeadTags)
						.openSingle(LINK, defaultCssAttributes("css/defaultIndexFacade/INDEX.css"))
					.close(HEAD)
					.open(BODY)
						.open(NAV)
							.insert(getNavigationHtmlOf(indexFile, dataContainer))
						.close(NAV)
					.close(BODY)
				.close(HTML)
				.toString();
	}


	/* ============================================================ */

	/* === PRIVATE METHODS */

	private String getNavigationHtmlOf(File contentFile, PostEffectDataContainer dataContainer) {

		String filePath = relFilePathWithoutExt(contentFile, dataContainer);

		return dataContainer.getNavigationHtmlOf(filePath);
	}


	private String relFilePathWithoutExt(File contentFile, PostEffectDataContainer dataContainer) {

		File deployRootDir = dataContainer.getDeployRootDir();

		String relativeFilePath = relativePath(deployRootDir, contentFile);

		return removeExtension(relativeFilePath);
	}

}

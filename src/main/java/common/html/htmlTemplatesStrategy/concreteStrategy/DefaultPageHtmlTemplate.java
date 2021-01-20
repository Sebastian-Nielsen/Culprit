package common.html.htmlTemplatesStrategy.concreteStrategy;

import common.PostEffectDataContainer;
import common.PostEffectsFacade;
import common.compilerFacade.CompilerDataContainer;
import common.fileOption.FileOptionContainer;
import common.html.HtmlBuilder;
import common.html.HtmlFactory;

import java.io.File;
import java.util.Map;

import static common.html.HTML.Attribute.*;
import static common.html.HTML.Tag.*;
import static common.html.HTML.Tag.LINK;
import static common.html.htmlTemplatesStrategy.Helper.*;
import static common.preparatorFacade.Deployer.getDeployEquivalentOf;
import static framework.utils.FileUtils.Filename.relativePath;
import static org.apache.commons.io.FilenameUtils.removeExtension;

public class DefaultPageHtmlTemplate {

	private final HtmlFactory htmlFactory;

	public DefaultPageHtmlTemplate(HtmlFactory htmlFactory) {
		this.htmlFactory = htmlFactory;
	}


	public String buildUsing(File contentFile, String articleTag, CompilerDataContainer dataContainer) throws Exception {

		FileOptionContainer foContainer = dataContainer.getFOContainerOf(contentFile);

		return new HtmlBuilder()
				.insert("<!DOCTYPE html>\n")
				.open(HTML)
					.open(HEAD)
						.insert(defaultHeadTags)
						.openSingle(LINK, defaultCssAttributes("css/defaultPageFacade/INDEX.css"))
						.open(SCRIPT, defaultScriptAttributes( "js/defaultPageFacade/globalConstVariables.js")).close(SCRIPT)
						.open(SCRIPT, defaultScriptAttributes( "js/defaultPageFacade/INDEX_1/navOverlay_colorCurrentlySelected.js", Map.of(DEFER, ""))).close(SCRIPT)
						.insert(htmlFactory.createKatexHtml(foContainer))
					.close(HEAD)
					.open(BODY)
						.open(NAV)
							.insert(getNavigationHtmlOf(contentFile, dataContainer))
//							.insert(htmlFactory.createNavigationHtml(dirOfContentFile))
						.close(NAV)
						.open(SCRIPT, defaultScriptAttributes("js/defaultPageFacade/INDEX_2/navOverlay_hotKeyToggle.js")).close(SCRIPT) // should be loaded after NAV
						.open(MAIN)
							.open(ASIDE, Map.of(ID,  "left-aside")).close(ASIDE)
//							.open(ASIDE, Map.of(ID, "right-aside")).close(ASIDE)
							.open(ARTICLE)
								.insert(articleTag)
							.close(ARTICLE)
						.close(MAIN)
					.close(BODY)
				.close(HTML)
				.toString();
	}


	/* === PRIVATE METHODS */

	private String getNavigationHtmlOf(File contentFile, CompilerDataContainer dataContainer) {
		String relativeFilePath = relativePath(dataContainer.getContentRootFolder(), contentFile);
		return dataContainer.getNavigationHtmlOf(relativeFilePath);
	}

	private File deployEquivalentOf(File indexFile, CompilerDataContainer dataContainer) {
		return getDeployEquivalentOf(
				indexFile,
				dataContainer.getContentRootFolder(),
				dataContainer.getDeployRootFolder()
		);
	}

}

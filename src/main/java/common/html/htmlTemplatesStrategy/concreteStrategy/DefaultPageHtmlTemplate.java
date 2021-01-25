package common.html.htmlTemplatesStrategy.concreteStrategy;

import common.compilerFacade.CompilerDataContainer;
import common.fileOption.FileOption;
import common.fileOption.FileOptionContainer;
import common.html.HtmlBuilder;
import common.html.HtmlFactory;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Map;

import static common.fileOption.FileOption.KEY.TOC;
import static common.html.HTML.Attribute.*;
import static common.html.HTML.Tag.*;
import static common.html.htmlTemplatesStrategy.Helper.*;
import static common.htmlBuilderMacros.HeadHtmlBuilderMacros.preloadStylesheet;
import static common.preparatorFacade.Deployer.getDeployEquivalentOf;
import static framework.utils.FileUtils.Filename.relativePath;

public class DefaultPageHtmlTemplate {

	private final HtmlFactory htmlFactory;

	public DefaultPageHtmlTemplate(HtmlFactory htmlFactory) {
		this.htmlFactory = htmlFactory;
	}


	public String buildUsing(File contentFile, String articleTag, CompilerDataContainer dataContainer) {

		FileOptionContainer foContainer = dataContainer.getFOContainerOf(contentFile);

		return new HtmlBuilder()
				.insert("<!DOCTYPE html>\n")
				.open(HTML)
					.open(HEAD)
						.insert(defaultTrivialHeadTags)
						.insert(preloadStylesheet("css/defaultPageFacade/INDEX.css"))
						.open(SCRIPT, defaultScriptAttributes( "js/defaultPageFacade/INDEX_1/navOverlay_colorCurrentlySelected.js", Map.of(DEFER, ""))).close(SCRIPT)
						.insert(htmlFactory.createKatexHtml(foContainer))
					.close(HEAD)
					.open(BODY)
						.open(NAV)
							.insert(getNavigationHtmlUsing(contentFile, dataContainer))
						.close(NAV)
						.open(SCRIPT, defaultScriptAttributes("js/defaultPageFacade/INDEX_2/navOverlay_hotKeyToggle.js")).close(SCRIPT) // should be loaded after NAV
						.open(MAIN)
							.open(ASIDE, Map.of(ID,  "left-aside"))
								.insert(getLeftAsideHtmlUsing(contentFile, foContainer))
							.close(ASIDE)
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

	private String getLeftAsideHtmlUsing(File contentFile, FileOptionContainer foContainer) {
//		boolean shouldUseTOC = foContainer.getBoolVal(TOC);

//		if (shouldUseTOC)
//			return "";
//		else
//			return "";
		return "";
	}

	@NotNull
	private String getNavigationHtmlUsing(File contentFile, CompilerDataContainer dataContainer) {
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

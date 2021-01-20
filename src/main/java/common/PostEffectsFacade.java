package common;

import common.culpritFactory.DefaultPostEffectFactory;
import common.html.HtmlFactory;
import common.html.htmlTemplatesStrategy.concreteStrategy.DefaultIndexHtmlTemplate;
import common.preparatorFacade.Deployer;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static framework.utils.FileUtils.Lister.RECURSION.RECURSIVELY;
import static framework.utils.FileUtils.Lister.listNonDirsFrom;
import static framework.utils.FileUtils.Modifier.writeStringTo;

public class PostEffectsFacade {

	private final boolean shouldPrettifyHtml;

	private @NotNull final File contentRootFolder;
	private @NotNull final File deployRootFolder;
	private @NotNull final PostEffectDataContainer dataContainer;

	public PostEffectsFacade(@NotNull DefaultPostEffectFactory factory,
	                         @NotNull PostEffectDataContainer dataContainer) {

		this.shouldPrettifyHtml = factory.shouldPrettifyHtml();

		this.dataContainer = dataContainer;

		this.contentRootFolder = factory.getContentHierarchy().getRootDir();
		this.deployRootFolder  = factory.getDeployHierarchy().getRootDir();
	}

	/**
	 * Effects are applied in the following order:
	 *<ol>
	 * <li> [Optional] prettify html
	 * <li> write <em>html of content file</em> to its equivalent deploy file
	 *</ol>
	 */
	public void effectsFor(File contentFile, String htmlOfContentFile) throws IOException {

		if (shouldPrettifyHtml)
			htmlOfContentFile = prettifyHtml(htmlOfContentFile);

		writeStringTo(getDeployEquivalentOf(contentFile), htmlOfContentFile);
	}

	public void afterEffects() throws Exception {

		writeHtmlToIndexFiles();
	}



	/* === PRIVATE METHODS */

	private void writeHtmlToIndexFiles() throws Exception {

		for (File indexFile : listIndexFiles()) {

			String indexHtml = buildIndexHtmlUsing(indexFile);

			writeStringTo(indexFile, indexHtml);
		}

	}

	private String buildIndexHtmlUsing(File indexFile) {
		return new DefaultIndexHtmlTemplate(new HtmlFactory())
					.buildUsing(indexFile, dataContainer);
	}

	/**
	 * Extracts all `index.html` files from <em>deployment</em>
	 */
	private File[] listIndexFiles() throws Exception {
		return Arrays.stream(listNonDirsFrom(deployRootFolder, RECURSIVELY))
				.filter(file -> file.getName().equals("index.html"))
				.toArray(File[]::new);
	}

	private String prettifyHtml(String html) {
		return Jsoup.parse(html).toString();
	}

	@NotNull
	private File getDeployEquivalentOf(File contentFile) {
		return Deployer.getDeployEquivalentOf(contentFile, contentRootFolder, deployRootFolder);
	}
}

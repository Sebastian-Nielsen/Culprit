package common;

import common.culpritFactory.DefaultPostEffectFactory;
import common.preparatorFacade.Deployer;
import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;

import java.io.File;
import java.io.IOException;

import static framework.utils.FileUtils.Lister.listNonDirsFrom;
import static framework.utils.FileUtils.Modifier.writeStringTo;

public class PostEffectFacade {


	private final boolean shouldPrettifyHtml;
	private final File contentRootFolder;
	private final File deployRootFolder;

	public PostEffectFacade(DefaultPostEffectFactory factory) {
		this.shouldPrettifyHtml = factory.shouldPrettifyHtml();

		this.contentRootFolder = factory.getContentRootFolder();
		this.deployRootFolder  = factory.getDeployRootFolder();
	}

//	public void effectsFor(Map<File, String> contentFileToHtml) throws IOException {
//
//		Set<File> files = contentFileToHtml.keySet();
//		for (File contentFile : files) {
//
//			String htmlOfContentFile = contentFileToHtml.get(contentFile);
//			effectsFor(contentFile, htmlOfContentFile);
//		}
//
//	}

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


	/* === PRIVATE METHODS */

	private String prettifyHtml(String html) {
		return Jsoup.parse(html).toString();
	}
//
//	private void writeStringToAssociatedFile(Map<File, String> fileToContent) throws IOException {
//
//		for (File contentFile : listNonDirsFrom(contentRootFolder, RECURSIVE)) {
//
//			File deployFile = getDeployEquivalentOf(contentFile);
//
//			String content = fileToContent.get(contentFile);
//
//			writeStringTo(deployFile, content);
//		}
//
//	}

	@NotNull
	private File getDeployEquivalentOf(File contentFile) {
		return Deployer.getDeployEquivalentOf(contentFile, contentRootFolder, deployRootFolder);
	}
}

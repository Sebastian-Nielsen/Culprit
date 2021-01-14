package framework.singleClasses;

import common.FileHandlerImpl;
import common.fileOption.FileOptionContainer;
import common.fileOption.FileOptionExtractorImpl;
import common.preparatorClasses.Deployer;
import framework.Compiler;
import framework.*;
import framework.CulpritFactory.CompilerFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import static common.html.concreteHtmlTemplates.Helper.buildDefaultPageHtmlTemplateUsing;
import static framework.utils.FileUtils.Lister.RECURSION.RECURSIVE;
import static framework.utils.FileUtils.Lister.listNonDirsFrom;
import static framework.utils.FileUtils.Modifier.writeStringTo;

public class CompilerFacade {

	private final Precompiler precompiler;
	private final Compiler compiler;

	private final Deployer deployer;

	private final File contentRootFolder;

	public CompilerFacade(CompilerFactory factory) {
		this.contentRootFolder = factory.getContentRootFolder();
		this.precompiler       = factory.createPrecompiler();
		this.compiler          = factory.createCompiler();
		this.deployer = new Deployer(contentRootFolder, factory.getDeployRootFolder());
	}

	/**
	 * Compile all files in {@code contentRootfolder}
	 * @return a map of each content-file pointing to its html (=compiled markdown)
	 */
	public Map<File, String> compile(Map<File, FileOptionContainer> fileToFOContainer) throws Exception {

		Map<File, String> contentFileToMd   = precompiler.compileAllFiles(fileToFOContainer);

		Map<File, String> contentFileToHtml =    compiler.compileAllFiles(contentFileToMd);
		// TODO: this method should also take in {@code fileToFOContainer} ^^

		return contentFileToHtml;
	}

	/**
	 * Compile the specified file only
	 */
	public String compile(File contentFile) throws Exception {
		String md         = precompiler.compileSingleFile(contentFile, extractFoContainerFrom(contentFile));
		String articleTag = compiler.compile(md);
		String htmlTag    = buildDefaultPageHtmlTemplateUsing(contentFile, articleTag);

		return htmlTag;
	}


	/* === PRIVATE METHODS */

	private FileOptionContainer extractFoContainerFrom(File file) throws IOException {

		FileOptionExtractor foExtractor = FileOptionExtractorImpl.getInstance();
		return foExtractor.extractFOContainer(  new FileHandlerImpl(file)  );

	}

	private void writeStringToAssociatedFile(Map<File, String> fileToContent) throws IOException {

		for (File contentFile : listNonDirsFrom(contentRootFolder, RECURSIVE)) {

			File deployFile = deployer.getDeployEquivalentOf(contentFile);

			String content = fileToContent.get(contentFile);

			writeStringTo(deployFile, content);
		}

	}

}

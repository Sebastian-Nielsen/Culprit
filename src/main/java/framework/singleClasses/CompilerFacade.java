package framework.singleClasses;

import common.FileHandlerImpl;
import common.fileOption.FileOptionContainer;
import common.fileOption.FileOptionExtractorImpl;
import framework.Compiler;
import framework.*;
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

	private static final Validator validator = ValidatorImpl.getInstance();

	private final PreparatorFacade preparator;
	private final Precompiler precompiler;
	private final Compiler compiler;

	private final File contentRootFolder;
	private final boolean addIdToContentFilesWithoutOne;
	private final boolean addDefaultIndexes;
	private final boolean prettifyHtml;

	public CompilerFacade(Builder builder) {
		CompilerDependencyFactory compilerDepedencyFac = builder.compilerDependencyFac;
		this.preparator        = compilerDepedencyFac.createPreparator(builder);
		this.precompiler       = compilerDepedencyFac.createPrecompiler();
		this.compiler          = compilerDepedencyFac.createCompiler();
		this.contentRootFolder = compilerDepedencyFac.getContentRootFolder();

		this.addIdToContentFilesWithoutOne = builder.addIdToContentFilesWithoutOne;
		this.addDefaultIndexes             = builder.addDefaultIndexes;
		this.prettifyHtml                  = builder.prettifyHtml;
	}

	/**
	 * Compile all files in {@code contentRootfolder}
	 * output result to {@code deployRootFolder}
	 */
	public Map<File, String> compile() throws Exception {
		Map<File, FileOptionContainer> fileToFOContainer = preparator.extractFOContainerFromEachContentFile();

		Map<File, String> fileToMd   = precompiler.compileAllFiles(fileToFOContainer);

		Map<File, String> fileToHtml = compiler   .compileAllFiles(fileToMd);   // TODO: this method should also take in {@code fileToFOContainer}

		if (prettifyHtml)  // TODO: this should be put over into {@code PostEffects}
			prettifyHtml(fileToHtml);

		return fileToHtml;
	}

	/**
	 * Compile the specified file only
	 */
	public void compile(File contentFile) throws Exception {
		String md         = precompiler.compileSingleFile(contentFile, extractFoContainerFrom(contentFile));
		String articleTag = compiler.compile(md);
		String htmlTag    = buildDefaultPageHtmlTemplateUsing(contentFile, articleTag);

		File deployFile = preparator.getDeployEquivalentOf(contentFile);

		writeStringTo(deployFile, htmlTag);
	}


	/* === PRIVATE METHODS */

	private FileOptionContainer extractFoContainerFrom(File file) throws IOException {

		FileOptionExtractor foExtractor = FileOptionExtractorImpl.getInstance();
		return foExtractor.extractFOContainer(  new FileHandlerImpl(file)  );

	}

	private void prettifyHtml(Map<File, String> fileToHtml) {
		for (File file : fileToHtml.keySet()) {
			Document doc = Jsoup.parse(fileToHtml.get(file), "", Parser.xmlParser());
			fileToHtml.put(file, doc.toString());
		}
	}

	private void writeStringToAssociatedFile(Map<File, String> fileToContent) throws IOException {

		for (File contentFile : listNonDirsFrom(contentRootFolder, RECURSIVE)) {

			File deployFile = preparator.getDeployEquivalentOf(contentFile);

			String content = fileToContent.get(contentFile);

			writeStringTo(deployFile, content);
		}

	}

}

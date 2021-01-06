package framework.singleClasses;

import common.fileOption.FileOptionContainer;
import common.fileOption.FileOptionExtractorImpl;
import framework.Compiler;
import framework.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

import static common.html.HtmlBuilder.buildDefaultPageHtmlFrom;
import static framework.utils.FileUtils.Lister.RECURSION.RECURSIVE;
import static framework.utils.FileUtils.Lister.listNonDirsFrom;
import static framework.utils.FileUtils.Modifier.writeStringTo;

public class CompilerFacade {

	private static final Validator validator = ValidatorImpl.getInstance();

	private final Deployer deployer;
	private final Precompiler precompiler;
	private final Compiler compiler;

	private final File contentRootFolder;
	private final boolean addIdToContentFilesWithoutOne;
	private final boolean addDefaultIndexes;
	private final boolean prettifyHtml;

	public CompilerFacade(Builder builder) {

		CompilerDependencyFactory compilerDepedencyFac = builder.compilerDependencyFac;
		this.deployer          = compilerDepedencyFac.createDeployer();
		this.precompiler       = compilerDepedencyFac.createPrecompiler();
		this.compiler          = compilerDepedencyFac.createCompiler();
		this.contentRootFolder = compilerDepedencyFac.getContentRootFolder();

		this.addIdToContentFilesWithoutOne = builder.addIdToContentFilesWithoutOne;
		this.addDefaultIndexes             = builder.addDefaultIndexes;
		this.prettifyHtml                  = builder.prettifyHtml;
	}

	public void compile() throws Exception {
		prepare();

//		Map<File, FileOptionContainer> fileToFOContainer = extractFOContainerFromEachFile();

		Map<File, String> fileToMd   = precompiler.compileAllFiles(extractFOContainerFromEachFile());

		Map<File, String> fileToHtml = compiler   .compileAllFiles(fileToMd);

		if (prettifyHtml)
			prettifyHtml(fileToHtml);

		writeStringToAssociatedFile(fileToHtml);
	}

	private void prettifyHtml(Map<File, String> fileToHtml) {
		for (File file : fileToHtml.keySet()) {
			Document doc = Jsoup.parse(fileToHtml.get(file), "", Parser.xmlParser());
			fileToHtml.put(file, doc.toString());
		}
	}

	private Map<File, String> buildHtmlTagForEachFile(Map<File, String> fileToHtmlBody,
	                                Map<File, FileOptionContainer> fileToFOContainer) {

		Map<File, String> fileToHtml = new HashMap<>();

		Set<File> files = fileToHtmlBody.keySet();
		for (File file : files) {

//			FileOptionContainer foContainer = fileToFOContainer.get(file);
			String htmlBody = fileToHtmlBody   .get(file);
			String htmlTag  = buildDefaultPageHtmlFrom(htmlBody);

			fileToHtml.put(
					file,
					htmlTag
			);

		}
		return fileToHtml;
	}


	/* === PRIVATE METHODS */

	private void prepare() throws Exception {
		deployer.deploy();

		if (addDefaultIndexes)
			deployer.addDefaultIndexes();

		if (addIdToContentFilesWithoutOne)
			deployer.addIdToContentFilesWithoutOne();

	}

	private Map<File, FileOptionContainer> extractFOContainerFromEachFile() throws Exception {

		return FileOptionExtractorImpl.getInstance()
				.extractFOContainerFromEachFileIn(contentRootFolder);

	}

	private void writeStringToAssociatedFile(Map<File, String> fileToContent) throws IOException {

		for (File contentFile : listNonDirsFrom(contentRootFolder, RECURSIVE)) {

			File deployFile = deployer.getDeployEquivalentOf(contentFile);

			String content = fileToContent.get(contentFile);

			writeStringTo(deployFile, content);
		}

	}


	/* === Builder Pattern */

	public static class Builder {
		// === Required parameters
		/**
1		 * Factory that contains all necessary dependencies for {@link CompilerFacade}
		 */
		private final CompilerDependencyFactory compilerDependencyFac;

		// === Optional parameters
		/**
		 * Whether to add default index.html files to all
		 * directories that doesn't already have one
		 */
		private boolean addDefaultIndexes = true;
		/**
		 * Whether to add an ID FileOption to files that doesn't have one
		 */
		private boolean addIdToContentFilesWithoutOne = true;
		/**
		 * Whether to prettify html or simply output the semi-prettified html
		 */
		private boolean prettifyHtml = false;

		public Builder(CompilerDependencyFactory factory) {
			this.compilerDependencyFac = factory;
		}
		public Builder setAddIdToContentFilesWithoutOne(boolean addIdToContentFilesWithoutOne) {
			this.addIdToContentFilesWithoutOne = addIdToContentFilesWithoutOne;
			return this;
		}
		public Builder setAddDefaultIndexes(boolean shouldAddDefaultIndexes) {
			this.addDefaultIndexes = shouldAddDefaultIndexes;
			return this;
		}
		public CompilerFacade build() {
			return new CompilerFacade(this);
		}

		public Builder setPrettifyHtml(boolean bool) {
			this.prettifyHtml = bool;
			return this;
		}
	}
}

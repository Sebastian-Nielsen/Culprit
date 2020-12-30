package framework.singleClasses;

import common.fileOption.FileOptionContainer;
import common.fileOption.FileOptionExtractorImpl;
import common.html.HtmlBuilder;
import common.html.tags.Tag;
import framework.Compiler;
import framework.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static common.html.HtmlBuilder.buildHtmlTag;
import static framework.utils.FileUtils.listAllNonDirFilesFrom;
import static framework.utils.FileUtils.writeStringTo;

public class CompilerFacade {

	private static final Validator validator = ValidatorImpl.getInstance();

	private final Deployer deployer;
	private final Precompiler precompiler;
	private final Compiler compiler;

	private final File contentRootFolder;
	private final boolean addIdToContentFilesWithoutOne;
	private final boolean addDefaultIndexes;

	public CompilerFacade(Builder builder) {

		CompilerDependencyFactory compilerDepedencyFac = builder.compilerDependencyFac;
		this.deployer          = compilerDepedencyFac.createDeployer();
		this.precompiler       = compilerDepedencyFac.createPrecompiler();
		this.compiler          = compilerDepedencyFac.createCompiler();
		this.contentRootFolder = compilerDepedencyFac.getContentRootFolder();

		this.addIdToContentFilesWithoutOne = builder.addIdToContentFilesWithoutOne;
		this.addDefaultIndexes             = builder.addDefaultIndexes;

	}

	public void compile() throws Exception {
		prepare();

		Map<File, FileOptionContainer> fileToFOContainer = extractFOContainerFromEachFile();

		Map<File, String> fileToMd       = precompiler.compileAllFiles(fileToFOContainer);

		Map<File, String> fileToHtmlBody = compiler   .compileAllFiles(fileToMd);

		Map<File, String> fileToHtml = buildHtmlTagForEachFile(fileToHtmlBody, fileToFOContainer);

		writeStringToAssociatedFile(fileToHtml);
	}

	private Map<File, String> buildHtmlTagForEachFile(Map<File, String> fileToHtmlBody,
	                                Map<File, FileOptionContainer> fileToFOContainer) {

		Map<File, String> fileToHtml = new HashMap<>();

		Set<File> files = fileToHtmlBody.keySet();
		for (File file : files) {

			FileOptionContainer foContainer = fileToFOContainer.get(file);
			String              htmlBody    = fileToHtmlBody   .get(file);
			Tag                 htmlTag     = buildHtmlTag(htmlBody);

			fileToHtml.put(
					file,
					htmlTag.toString()
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

		for (File contentFile : listAllNonDirFilesFrom(contentRootFolder)) {

			File deployFile = deployer.getDeployEquivalentOf(contentFile);

			String content = fileToContent.get(contentFile);

			writeStringTo(deployFile, content);
		}

	}


	/* === Builder Pattern */

	public static class Builder {
		// === Required parameters
		private final CompilerDependencyFactory compilerDependencyFac;

		// === Optional parameters
		// === === Preparation related parameters, see {@link prepare}
		/**
		 * Whether to add default index.html files to all
		 * directories that doesn't already have one
		 */
		private boolean addDefaultIndexes = true;
		/**
		 * Whether to add an ID FileOption to files that doesn't have one
		 */
		private boolean addIdToContentFilesWithoutOne = true;


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
	}
}

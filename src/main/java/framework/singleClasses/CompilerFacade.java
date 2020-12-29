package framework.singleClasses;

import common.FileHandlerImpl;
import common.FileOptionExtractorImpl;
import framework.*;
import framework.Compiler;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static framework.utils.FileUtils.listAllNonDirFilesFrom;

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

		Map<File, String> fileToMd   = precompiler.compileAllFiles(extractFOContainerFromEachFile());

		Map<File, String> fileToHtml = compiler   .compileAllFiles(fileToMd);

		writeStringToAssociatedFile(fileToHtml);
	}

	private void prepare() throws IOException {
		deployer.deploy();

		if (addDefaultIndexes)
			deployer.addDefaultIndexes();

		if (addIdToContentFilesWithoutOne)
			deployer.addIdToContentFilesWithoutOne();


	}


	/* === PRIVATE METHODS */

	private void writeStringTo(File file, String content) throws IOException {
		FileUtils.writeStringToFile(file, content, Charset.defaultCharset());
	}

	private void writeStringToAssociatedFile(Map<File, String> fileToContent) throws IOException {

		for (File contentFile : listAllNonDirFilesFrom(contentRootFolder)) {

			File deployFile = deployer.getDeployEquivalentOf(contentFile);

			String content = fileToContent.get(contentFile);
			writeStringTo(deployFile, content);
		}

	}

	private Map<File, FileOptionContainer> extractFOContainerFromEachFile() throws Exception {

		Map<File, FileOptionContainer> fileToFOContainer = new HashMap<>();

		for (File file : listAllNonDirFilesFrom(contentRootFolder)) {

			FileOptionExtractor foExtractor = newFileOptionExtractor(file);

			fileToFOContainer.put(
					file,
					foExtractor.extractFOContainer()
			);

		}

		return fileToFOContainer;
	}


	/* === PRIVAT METHODS */

	private FileOptionExtractor newFileOptionExtractor(File fileToExtractFrom) throws IOException {
		return new FileOptionExtractorImpl(new FileHandlerImpl(fileToExtractFrom));
	}

	/* === Builder options-setter methods */

	public static class Builder {
		// Required parameters
		private final CompilerDependencyFactory compilerDependencyFac;

		// Optional parameters - initialized to default values
		/**
		 * Whether to add default index.html files to all
		 * directories that doesn't already have one
		 */
		private boolean addDefaultIndexes = true;
		/**
		 * If the fileOption
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

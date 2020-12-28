package framework.singleClasses;

import common.FileHandlerImpl;
import common.FileOptionExtractorImpl;
import framework.*;
import framework.Compiler;
import framework.singleClasses.FileOptionContainer;
import framework.singleClasses.ValidatorImpl;
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

	public CompilerFacade(CompilerSettingsFactory compilerSettingsFac) {
		this.precompiler = compilerSettingsFac.createPrecompiler();
		this.deployer    = compilerSettingsFac.createDeployer();
		this.compiler    = compilerSettingsFac.createCompiler();
		this.contentRootFolder = compilerSettingsFac.getContentRootFolder();
	}

	public void compile() throws Exception {
		deployer.deploy();

		Map<File, String> fileToMd   = precompiler.compileAllFiles(extractFOContainerFromEachFile());

		Map<File, String> fileToHtml = compiler   .compileAllFiles(fileToMd);

		writeStringToAssociatedFile(fileToHtml);
	}


	/* === Builder options-setter methods */

	public static class Builder {
		// Required parameters
		private final CompilerSettingsFactory compilerSettingsFac;

		// Optional parameters - initialized to default values
		private boolean shouldAddDefaultIndexes = true;

		public Builder(CompilerSettingsFactory compilerSettingsFac) {
			this.compilerSettingsFac = compilerSettingsFac;
		}
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

		FileOptionExtractor foExtractor;
		for (File file : listAllNonDirFilesFrom(contentRootFolder)) {

			foExtractor = new FileOptionExtractorImpl(new FileHandlerImpl(file),
														validator);

			fileToFOContainer.put(
					file,
					foExtractor.extractFOContainer()
			);

		}

		return fileToFOContainer;
	}

}

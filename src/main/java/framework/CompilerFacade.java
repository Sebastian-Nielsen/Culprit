package framework;

import common.FileHandlerImpl;
import common.FileOptionExtractorImpl;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static framework.Constants.Constants.CWD;
import static framework.utils.FileUtils.listAllNonDirFilesFrom;

public class CompilerFacade {

	private static final Validator validator = ValidatorImpl.getInstance();

	private final Deployer deployer;
	private final Precompiler precompiler;

	private final File contentRootFolder;

	public CompilerFacade(Deployer deployer, Precompiler precompiler,
	                      File contentRootFolder) {
		this.deployer = deployer;
		this.precompiler = precompiler;

		this.contentRootFolder = contentRootFolder;
	}

	public CompilerFacade(Deployer deployer, Precompiler precompiler) {
		this.deployer = deployer;
		this.precompiler = precompiler;

		this.contentRootFolder = new File(CWD + "content");
	}

	public void compile() throws Exception {
		deployer.deploy();

		Map<File, String> fileToPrecompiledContent
				= precompiler.compileAllFiles(extractAllFOContainer());

		for (File contentFile : listAllNonDirFilesFrom(contentRootFolder)) {
			File deployFile = deployer.getDeployEquivalentOf(contentFile);

			String precompiledContent = fileToPrecompiledContent.get(contentFile);
			String html = mdToHTMLAdapter.compile(precompiledContent);

			FileUtils.writeStringToFile(deployFile, html, Charset.defaultCharset());
		}
	}


	/* === PRIVATE METHODS */

	private Map<File, FileOptionContainer> extractAllFOContainer() throws Exception {
		Map<File, FileOptionContainer> fileToFOContainer = new HashMap<>();

		FileOptionExtractor foExtractor;
		for (File file : listAllNonDirFilesFrom(contentRootFolder)) {

			foExtractor = new FileOptionExtractorImpl(
								new FileHandlerImpl(file),
								validator);

			fileToFOContainer.put(
					file,
					foExtractor.extractFOContainer()
			);

		}

		return fileToFOContainer;
	}

}

package integrationTests;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import common.CompilerImpl;
import common.DeployerImpl;
import common.FileHandlerImpl;
import common.FileOptionExtractorImpl;
import framework.*;
import framework.Compiler;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import static framework.FileOption.KEY;

import static framework.utils.FileUtils.listAllNonDirFilesFrom;
import static testHelper.TestHelper.getResourceFile;

public class customerTest {
	private Compiler compiler;
	private FileOptionExtractor extractor;
	private File CONTENT_ROOT_FOLDER;
	private Validator validator;
	private Deployer deployer;
	private File DEPLOY_ROOT_FOLDER;
	private File DEPLOY_ROOT_FOLDER_2;

	@BeforeEach
	public void setup(@TempDir File tempDir) throws IOException {
		String CONTENT_ROOT_PATH = "customerTest_testFiles/input";
		CONTENT_ROOT_FOLDER = getResourceFile(CONTENT_ROOT_PATH);
		DEPLOY_ROOT_FOLDER = tempDir;

		DEPLOY_ROOT_FOLDER_2 = new File("src/test/java/tempOutput");

		deployer  = new DeployerImpl(CONTENT_ROOT_FOLDER, DEPLOY_ROOT_FOLDER_2);
		compiler  = new CompilerImpl(CONTENT_ROOT_FOLDER);
		validator = ValidatorImpl.getInstance();
	}

	@Test
	public void test_1_withoutFacade() throws IOException {
		deployer.deploy();

		Map<File, FileOptionContainer> fileToFOContainer = extractAllFOContainer();

		compiler.preprocess(fileToFOContainer);
		Map<File, String> fileToCompiledContent = compiler.compile(fileToFOContainer);

		for (File contentFile : listAllNonDirFilesFrom(CONTENT_ROOT_FOLDER)) {
			File deployFile = deployer.getDeployEquivalentOf(contentFile);
			System.out.println("\n\n\n==================");
			System.out.println("Writing to file: " + deployFile + "\ncontent: " + fileToCompiledContent.get(contentFile));

			String html = mdToHTMLAdapter.compile(fileToCompiledContent.get(contentFile));

			FileUtils.writeStringToFile(deployFile, html, Charset.defaultCharset());
		}
	}

	private Map<File, FileOptionContainer> extractAllFOContainer() throws IOException {
		Map<File, FileOptionContainer> fileToFOContainer = new HashMap<>();

		for (File file : listAllNonDirFilesFrom(CONTENT_ROOT_FOLDER)) {
			extractor = new FileOptionExtractorImpl(
							new FileHandlerImpl(file),
							validator);
			fileToFOContainer.put(
					file,
					extractor.extractFileOptions()
			);
		}

		return fileToFOContainer;
	}
}

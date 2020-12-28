package integrationTests;

import common.PrecompilerImpl;
import common.DeployerImpl;
import common.FileHandlerImpl;
import common.FileOptionExtractorImpl;
import framework.*;
import framework.Precompiler;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static framework.utils.FileUtils.listAllNonDirFilesFrom;
import static testHelper.TestHelper.getResourceFile;

public class customerTest {
	private Precompiler precompiler;
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

		deployer = new DeployerImpl(CONTENT_ROOT_FOLDER, DEPLOY_ROOT_FOLDER_2);
		precompiler = new PrecompilerImpl(CONTENT_ROOT_FOLDER);
		validator = ValidatorImpl.getInstance();
	}

	@Test
	public void test_1_withoutFacade() {
		CompilerFacade compiler = new CompilerFacade(deployer, precompiler, CONTENT_ROOT_FOLDER);
	}

}

package integrationTests;

import common.CompilerImpl;
import common.PrecompilerImpl;
import common.Preparator;
import common.compilerSettingsFactories.CustomCompilerDependencyFactory;
import framework.*;
import framework.Compiler;
import framework.Precompiler;
import framework.singleClasses.CompilerFacade;
import framework.singleClasses.ValidatorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;

import static testHelper.TestHelper.getResourceFile;

public class customerTest {
	private Precompiler precompiler;
	private FileOptionExtractor extractor;
	private File CONTENT_ROOT_FOLDER;
	private Validator validator;
	private PreparatorFacade preparator;
	private File DEPLOY_ROOT_FOLDER;
	private File DEPLOY_ROOT_FOLDER_2;
	private Compiler mdToHtmlCompiler;

	@BeforeEach
	public void setup(@TempDir File tempDir) throws IOException {
		String CONTENT_ROOT_PATH = "customerTest_testFiles/input";
		CONTENT_ROOT_FOLDER = getResourceFile(CONTENT_ROOT_PATH);
		DEPLOY_ROOT_FOLDER = tempDir;

		DEPLOY_ROOT_FOLDER_2 = new File("src/test/java/tempOutput");

		preparator = new Preparator(   CONTENT_ROOT_FOLDER, DEPLOY_ROOT_FOLDER_2);
		precompiler = new PrecompilerImpl(CONTENT_ROOT_FOLDER);
		validator        = ValidatorImpl.getInstance();
		mdToHtmlCompiler = CompilerImpl.getInstance();
	}

	@Test
	public void test_1() throws Exception {
		CompilerDependencyFactory factory =
				new CustomCompilerDependencyFactory(
						CONTENT_ROOT_FOLDER,
						DEPLOY_ROOT_FOLDER
				);

		CompilerFacade compiler = new CompilerFacade
				.Builder(factory)
				.setAddIdToContentFilesWithoutOne(false)
				.setAddDefaultIndexes(true)
				.build();

		compiler.compile();
	}

}

//package integrationTests;
//
//import common.CompilerImpl;
//import common.FileHandlerImpl;
//import common.FileOptionExtractorImpl;
//import framework.*;
//import framework.Compiler;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.io.TempDir;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//import static framework.FileOption.KEY;
//
//import static testHelper.TestHelper.getResourceFile;
//
//public class customerTest {
//	private Compiler compiler;
//	private FileOptionExtractor extractor;
//	private File CONTENT_ROOT_FOLDER;
//
//	@BeforeEach
//	public void setup(@TempDir File tempDir) throws IOException {
//		String CONTENT_ROOT_PATH = "customerTest_testFiles/input";
//		CONTENT_ROOT_FOLDER = getResourceFile(CONTENT_ROOT_PATH);
//		File  DEPLOY_ROOT_FOLDER = tempDir;
//
//		FileHandler filehandler = new FileHandlerImpl(CONTENT_ROOT_FOLDER);
//		Validator   validator   = ValidatorImpl.getInstance();
//
//		extractor = new FileOptionExtractorImpl(validator);
//		compiler  = new CompilerImpl(CONTENT_ROOT_FOLDER);
//	}
//
//	@Test
//	public void test_1_withoutFacade() throws IOException {
//		Map<KEY, String> keyToVal = extractor.extractKeyToValMapFrom(CONTENT_ROOT_FOLDER);
//	}
//}

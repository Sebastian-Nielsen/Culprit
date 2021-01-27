package unitTests;

import common.compilerFacade.CompilerDataContainer;
import common.DataExtractor;
import framework.ContentFileHierarchy;
import framework.DeployFileHierarchy;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import stubs.NavigationHtmlStub;

import java.io.File;
import java.util.Map;

import static common.fileOption.FileOption.KEY.ID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static testHelper.TestHelper.getResourceFile;

public class DataExtractorTest {

	private final String EXPECTED_ROOT_PATH = "DataExtractorTest_testFiles/expected";;
	private final String    INPUT_ROOT_PATH = "DataExtractorTest_testFiles/input";

	private final String A = "/A";
	private final String B = "/B";
	private final String C = "/nested/C";
	private final String D = "/nested/x2nested/D";
	private final String E = "/nested_v2/E";

	private final File INPUT_FILE_A = getResourceFile(INPUT_ROOT_PATH + A + ".md");
	private final File INPUT_FILE_B = getResourceFile(INPUT_ROOT_PATH + B + ".md");
	private final File INPUT_FILE_C = getResourceFile(INPUT_ROOT_PATH + C + ".md");
	private final File INPUT_FILE_D = getResourceFile(INPUT_ROOT_PATH + D + ".md");
	private final File INPUT_FILE_E = getResourceFile(INPUT_ROOT_PATH + E + ".md");

	private final File EXPECTED_FILE_A = getResourceFile(EXPECTED_ROOT_PATH + A + ".extIsIgnored");
	private final File EXPECTED_FILE_B = getResourceFile(EXPECTED_ROOT_PATH + B + ".extIsIgnored");
	private final File EXPECTED_FILE_C = getResourceFile(EXPECTED_ROOT_PATH + C + ".extIsIgnored");
	private final File EXPECTED_FILE_D = getResourceFile(EXPECTED_ROOT_PATH + D + ".extIsIgnored");
	private final File EXPECTED_FILE_E = getResourceFile(EXPECTED_ROOT_PATH + E + ".extIsIgnored");

	@Test
	public void shouldExtractIdToFileMap() throws Exception {
		DataExtractor dataExtractor = newDataExtractor(INPUT_ROOT_PATH, EXPECTED_ROOT_PATH);

		// Exercise
		Map<String, File> idToRelativeDeployPath = dataExtractor.extractIdToContentFile();

		// Verify
		assertThat(idToRelativeDeployPath, hasEntry("11111111-1111-1111-1111-111111111111", INPUT_FILE_A));
		assertThat(idToRelativeDeployPath, hasEntry("22222222-2222-2222-2222-222222222222", INPUT_FILE_B));
		assertThat(idToRelativeDeployPath, hasEntry("33333333-3333-3333-3333-333333333333", INPUT_FILE_C));
		assertThat(idToRelativeDeployPath, hasEntry("44444444-4444-4444-4444-444444444444", INPUT_FILE_D));
		assertThat(idToRelativeDeployPath, hasEntry("55555555-5555-5555-5555-555555555555", INPUT_FILE_E));

		// DEBUG
//		int i = 1;
//		for (Map.Entry<String, String> e : idToRelativeDeployPath.entrySet()) {
//			System.out.println(i + ") " + e.getKey() + "  :  " + e.getValue());
//			System.out.println();
//			i++;
//		}
	}

	/**
	 * This method will fail if {@link #shouldExtractIdToFileMap} fails
	 */
	@Test
	public void shouldBuildCompilerDataContainer() throws Exception {
		DataExtractor dataExtractor = newDataExtractor(INPUT_ROOT_PATH, EXPECTED_ROOT_PATH);

		// Exercise
		CompilerDataContainer dataContainer = dataExtractor.buildDataContainerForCompiler(new NavigationHtmlStub());

		// Verify
		assertThat(dataContainer.getContentFileOfId("11111111-1111-1111-1111-111111111111"), is(INPUT_FILE_A));
		assertThat(dataContainer.getContentFileOfId("22222222-2222-2222-2222-222222222222"), is(INPUT_FILE_B));
		assertThat(dataContainer.getContentFileOfId("33333333-3333-3333-3333-333333333333"), is(INPUT_FILE_C));
		assertThat(dataContainer.getContentFileOfId("44444444-4444-4444-4444-444444444444"), is(INPUT_FILE_D));
		assertThat(dataContainer.getContentFileOfId("55555555-5555-5555-5555-555555555555"), is(INPUT_FILE_E));

		assertThat(dataContainer.getFOContainerOf(INPUT_FILE_A).get(ID), is("11111111-1111-1111-1111-111111111111"));
		assertThat(dataContainer.getFOContainerOf(INPUT_FILE_B).get(ID), is("22222222-2222-2222-2222-222222222222"));
		assertThat(dataContainer.getFOContainerOf(INPUT_FILE_C).get(ID), is("33333333-3333-3333-3333-333333333333"));
		assertThat(dataContainer.getFOContainerOf(INPUT_FILE_D).get(ID), is("44444444-4444-4444-4444-444444444444"));
		assertThat(dataContainer.getFOContainerOf(INPUT_FILE_E).get(ID), is("55555555-5555-5555-5555-555555555555"));
	}


	/* === PRIVATE METHODS */

	@NotNull
	private DataExtractor newDataExtractor(String INPUT_ROOT_PATH, String EXPECTED_ROOT_PATH) {
		ContentFileHierarchy contentHiearchy = new ContentFileHierarchy(getResourceFile(INPUT_ROOT_PATH)   );
		DeployFileHierarchy  deployHiearchy  = new DeployFileHierarchy( getResourceFile(EXPECTED_ROOT_PATH));
		
		return new DataExtractor(contentHiearchy, deployHiearchy);
	}

}

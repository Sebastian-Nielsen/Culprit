package unitTests.FileUtilsSuiteClasses;

import org.junit.jupiter.api.Test;

import java.io.File;

import static framework.utils.FileUtils.Filename.relativeFilePathBetween;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static testHelper.TestHelper.getResourceFile;

public class FileUtils_relativeFilePathBetween {

	public static final String DIR_PATH = "FileUtils_testFiles/relativeFilePathBetween";
	public static final File   DIR      = getResourceFile(DIR_PATH);

	public static final String A = "/A.md";
	public static final String B = "/x1nested/B.md";
	public static final String C = "/x1nested/x2nested/C.md";
	public static final String D = "/x1nested/x2nested/D.md";

	public static final File FILE_A = getResourceFile(DIR_PATH + A);
	public static final File FILE_B = getResourceFile(DIR_PATH + B);
	public static final File FILE_C = getResourceFile(DIR_PATH + C);
	public static final File FILE_D = getResourceFile(DIR_PATH + D);

	public static final File x1nested = getResourceFile(DIR_PATH + "/x1nested");
	public static final File x2nested = getResourceFile(DIR_PATH + "/x1nested/x2nested");

	@Test
	public void shouldGetRelativeFilePathBetween() {
		// Going one dir up
		assertThat( relativeFilePathBetween(FILE_A, FILE_B), is("./x1nested/B.md") );
		assertThat( relativeFilePathBetween(FILE_B, FILE_C), is("./x2nested/C.md") );
		// Going one dir down
		assertThat( relativeFilePathBetween(FILE_B, FILE_A), is("./../A.md") );
		assertThat( relativeFilePathBetween(FILE_C, FILE_B), is("./../B.md") );
		// The dir of file
		assertThat( relativeFilePathBetween(FILE_A, DIR     ), is(".") );
		assertThat( relativeFilePathBetween(FILE_B, x1nested), is(".") );
		assertThat( relativeFilePathBetween(FILE_C, x2nested), is(".") );
		assertThat( relativeFilePathBetween(FILE_D, x2nested), is(".") );
	}
}

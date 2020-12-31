package unitTests.FileUtilsSuiteClasses;

import org.junit.jupiter.api.Test;

import static framework.utils.FileUtils.Filename.changeFileExt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class FileUtils_miscellaneous {
	@Test
	public void shouldChangeFileExt() {
		// Fixture
		final String newExt = "html";
		// Exercise
		String actualFileName = changeFileExt("a/bb/test.md", newExt);
		// Verify
		assertThat(actualFileName, is("a/bb/test." + newExt));
	}
}

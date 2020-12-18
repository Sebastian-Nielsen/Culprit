package unitTests.FileUtilsSuiteClasses;

import framework.utils.FileUtils;
import org.junit.jupiter.api.Test;

import java.io.File;

import static framework.utils.FileUtils.changeFileExt;
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

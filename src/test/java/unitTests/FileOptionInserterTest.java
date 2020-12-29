package unitTests;

import common.fileOption.FileOptionInserter;
import framework.UUIDGenerator;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import stubs.UUIDGeneratorStub;

import java.io.File;
import java.io.IOException;

import static common.fileOption.FileOption.KEY.ID;
import static framework.utils.FileUtils.contentOf;
import static framework.utils.FileUtils.writeStringTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class FileOptionInserterTest {

	@Test
	public void shouldAddIdFileOptionToMarkdownFile(@TempDir File tempDir) throws IOException {
		// Fixture
		final File file = new File(tempDir + "/test.md");
		final String originalFileContent = "this is \n an arbitrary text";
		writeStringTo(
				file,
				originalFileContent
		);

		FileOptionInserter foInserter = newFileOptionInserter();

		// Exercise
		foInserter.addIdTo(file);

		// Verify
		String expectedContent = generateExpectedFileOption() + '\n' + originalFileContent;
		String   actualContent = contentOf(file);

		assertThat(actualContent, is(expectedContent));
	}

	private String generateExpectedFileOption() {
		String uuid = new UUIDGeneratorStub().generate();
		return "[" + ID + "]:<> (" + uuid + ")";
	}


	/* === PRIVATE METHODS */

	@NotNull
	private FileOptionInserter newFileOptionInserter() {
		return new FileOptionInserter(new UUIDGeneratorStub());
	}

}

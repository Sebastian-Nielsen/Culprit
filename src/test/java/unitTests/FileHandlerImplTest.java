package unitTests;

import framework.FileHandler;
import common.FileHandlerImpl;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static testHelper.TestHelper.getResourceFile;

public class FileHandlerImplTest {

	private @NotNull FileHandler fileHandler;

	@BeforeEach
	public void setup() throws IOException {
		final String fileName = "FileHandlerTest.md";
		fileHandler = newFileHandlerImpl(fileName);
	}


	@Test
	public void shouldReadline() throws IOException {
		String firstLine  = fileHandler.readLine();
		String secondLine = fileHandler.readLine();
		String thirdLine  = fileHandler.readLine();

		assertThat(firstLine,  is("The first line"));
		assertThat(secondLine, is("The second line"));
		assertNull(thirdLine);
	}

	@Test
	public void shouldHasNextTwice() throws IOException {
		assertTrue(fileHandler.hasNext()); // has first line

		fileHandler.readLine();
		assertTrue(fileHandler.hasNext()); // has second line

		fileHandler.readLine();
		assertFalse(fileHandler.hasNext());// has third line
	}


	/* === PRIVATE METHODS */

	@NotNull
	private FileHandlerImpl newFileHandlerImpl(String name) throws IOException {
		return new FileHandlerImpl(getResourceFile(name));
	}
}

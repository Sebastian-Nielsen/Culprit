package unitTests.FileUtilsSuiteClasses;

import framework.utils.FileUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileUtils_areDistinctFilePaths {

	@Test
	public void shouldBeDistinctFilePaths() {
		assertTrue(FileUtils.Filename.areDistinctFilePaths("content", "deployment"));
		assertTrue(FileUtils.Filename.areDistinctFilePaths("a/content", "deployment"));
		assertTrue(FileUtils.Filename.areDistinctFilePaths("content", "a/deployment"));
		assertTrue(FileUtils.Filename.areDistinctFilePaths("a/content", "a/deployment"));
		assertTrue(FileUtils.Filename.areDistinctFilePaths("content/a", "deployment/a"));
	}

	@Test
	public void shouldNotBeDinstinctFilePaths() {
		assertFalse(FileUtils.Filename.areDistinctFilePaths("a", "a"));
		assertFalse(FileUtils.Filename.areDistinctFilePaths("a/b", "a"));
		assertFalse(FileUtils.Filename.areDistinctFilePaths("a", "a/b"));
		assertFalse(FileUtils.Filename.areDistinctFilePaths("a/b/c/d", "a/b"));
		assertFalse(FileUtils.Filename.areDistinctFilePaths("a/b", "a/b/c"));
	}

}

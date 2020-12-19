package framework.utils;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

public class FileUtils {

	/**
	 * Get {@link File}s recursively in {@code folder} in a depth-first manner.
	 * @return A list of all files in {@code folder}
	 */
	public static File[] listAllFilesOf(File folder) throws IOException {
		return Files.walk(folder.toPath())
				.skip(1)  // Don't include the "root" aka. {@code folder}
				.map(Path::toFile)
				.toArray(File[]::new);
	}

	/**
	 * Get {@link File}s recursively in {@code folder} in a depth-first manner.
	 * @return A list of all files in {@code folder}
	 */
	public static String[] getRelativePathsFrom(File folder) throws IOException {
		String basePath = "" + folder;
		URI uri = new File(basePath).toURI();

//		System.out.println("base: " + base);
//		System.out.println(uri + " sadflkj ");

		return Arrays.stream(listAllFilesOf(folder))
				.map(file -> getRelativePath(file, uri))
//				.peek(file -> System.out.println("> actual > " +file))
				.toArray(String[]::new);
	}

	/**
	 * Get {@link File}s recursively in {@code folder} that is of type Folder in a depth-first manner.
	 * @return a list of all folders in {@code folder}
	 */
	public static File[] listAllFoldersOf(File folder) throws IOException {
		File[] files = listAllFilesOf(folder);
		return Arrays.stream(files)
				.filter(File::isDirectory)
				.toArray(File[]::new);
	}

	/**
	 * Change The file extension of a filename to something new.
	 * e.g. "test.md" -> "test.html"
	 * @return filename
	 */
	public static String changeFileExt(String filename, String newExt) {
		return filename.substring(0, filename.lastIndexOf(".")) + "." + newExt;
	}

	/**
	 * Get the path of each file in the inputFolders.content dir RELATIVE to the base inputFolders.content dir
	 * E.g. instead of 'C:/.../culprit/inputFolders.content/aa/test.md' then 'aa/test.md'
	 */
	public static String getRelativePath(File file, URI basePath) {
		return basePath.relativize(file.toURI()).getPath();
	}

}

package framework.utils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static framework.utils.FileUtils.Filename.relativePath;
import static framework.utils.FileUtils.Lister.RECURSION.RECURSIVELY;
import static framework.utils.FileUtils.Retriever.contentOf;
import static org.apache.commons.io.FileUtils.readFileToString;

public class FileUtils {
	/**
	 * FileUtil methods related to listing files
	 */
	public static class
	Lister {

		public enum RECURSION {
			RECURSIVELY(true), NONRECURSIVELY(false);

			private final boolean isRecursive;

			RECURSION(boolean isRecursive) {
				this.isRecursive = isRecursive;
			}

			public boolean getBool() {
				return isRecursive;
			}

		}


		/* ===================================================== */

		/**
		 * @param isRecursive whether files and dirs of each folder should be listed too.
		 */
		public static Stream<File> streamFilesAndDirsFrom(File folder, RECURSION isRecursive) throws IOException {
			if (isRecursive.getBool())
				return streamFilesAndDirsRecursivelyFrom(folder);
			else
				return streamFilesAndDirsNonRecursivelyFrom(folder);
		}

		private static Stream<File> streamFilesAndDirsRecursivelyFrom(File folder) throws IOException {
			return Files.walk(folder.toPath())
					.skip(1)  // Don't include the "root" aka. {@code folder}
					.map(Path::toFile);
		}

		private static Stream<File> streamFilesAndDirsNonRecursivelyFrom(File folder) {
			return Arrays.stream(folder.listFiles());
		}


		/* ===================================================== */

		public static Stream<File> streamNonDirsFrom(File folder, RECURSION isRecursive) throws IOException {
			if (isRecursive.getBool())
				return streamNonDirsRecursivelyFrom(folder);
			else
				return streamNonDirsNonRecursivelyFrom(folder);
		}

		/**
		 * Get all non-dirs recursively in {@code folder} in a depth-first manner.
		 * @return A list of all files in {@code folder}
		 */
		private static Stream<File> streamNonDirsRecursivelyFrom(File folder) throws IOException {
			return Files.walk(folder.toPath())
					.skip(1)  // Don't include the "root" aka. {@code folder}
					.map(Path::toFile)
					.filter(File::isFile);
		}

		private static Stream<File> streamNonDirsNonRecursivelyFrom(File folder) {
			return Arrays.stream(folder.listFiles())
					.filter(File::isFile);
		}



		/* ===================================================== */


		/**
		 * @param isRecursive whether files and dirs of each folder should be listed too.
		 */
		public static File[] listFilesAndDirsFrom(File folder, RECURSION isRecursive) throws IOException {
			if (isRecursive.getBool())
				return listFilesAndDirsRecursivelyFrom(folder);
			else
				return listFilesAndDirsNonRecursivelyFrom(folder);
		}

		private static File[] listFilesAndDirsRecursivelyFrom(File folder) throws IOException {
			return Files.walk(folder.toPath())
					.skip(1)  // Don't include the "root" aka. {@code folder}
					.map(Path::toFile)
					.toArray(File[]::new);
		}

		private static File[] listFilesAndDirsNonRecursivelyFrom(File folder) {
			return folder.listFiles();
		}



		/* ===================================================== */

		/**
		 * @param extFilter extension that all returned files should.
		 *          The extension String should be written without the leading dot. E.g. "html"
		 */
		public static File[] listNonDirsFrom(File folder, RECURSION isRecursive, String extFilter) throws IOException {
			File[] files;
			if (isRecursive.getBool())
				files = listNonDirsRecursivelyFrom(folder);
			else
				files = listNonDirsNonRecursivelyFrom(folder);

			return Arrays.stream(files)
					.filter(f -> f.toString().endsWith(extFilter))
					.toArray(File[]::new);
		}

		public static File[] listNonDirsFrom(File folder, RECURSION isRecursive) throws IOException {
			if (isRecursive.getBool())
				return listNonDirsRecursivelyFrom(folder);
			else
				return listNonDirsNonRecursivelyFrom(folder);
		}

		/**
		 * Get all non-dirs recursively in {@code folder} in a depth-first manner.
		 * @return A list of all files in {@code folder}
		 */
		public static File[] listNonDirsRecursivelyFrom(File folder) throws IOException {
			return Files.walk(folder.toPath())
					.skip(1)  // Don't include the "root" aka. {@code folder}
					.map(Path::toFile)
					.filter(File::isFile)
					.toArray(File[]::new);
		}

		private static File[] listNonDirsNonRecursivelyFrom(File folder) {
			File[] files = folder.listFiles();
			if (files == null)
				return new File[]{};
			return Arrays.stream(files)
					.filter(File::isFile)
					.toArray(File[]::new);
		}


		/* ===================================================== */



		public static Stream<File> streamDirsFrom(File folder, RECURSION isRecursive) throws IOException {
			if (isRecursive.getBool())
				return streamDirsRecursivelyFrom(folder);
			else
				return streamDirsNonRecursivelyFrom(folder);
		}

		private static Stream<File> streamDirsRecursivelyFrom(File folder) throws IOException {
			return Files.walk(folder.toPath())  // lists recursively in a depth-first manner
					.skip(1)  // Don't include the "root" aka. {@code folder}
					.map(Path::toFile)
					.filter(File::isDirectory);
		}

		private static Stream<File> streamDirsNonRecursivelyFrom(File folder) {
			return Arrays.stream(folder.listFiles())
					.filter(File::isDirectory);
		}


		/* ===================================================== */


		public static String[] listContentOfFilesFrom(String folderPath) throws IOException {
			return listContentOfFilesFrom(new File(folderPath));
		}

		public static String[] listContentOfFilesFrom(File folder) throws IOException {
			return Arrays.stream(listNonDirsRecursivelyFrom(folder))
					.map(FileUtils.Retriever::contentOf)
					.toArray(String[]::new);
		}


		/* ===================================================== */


		/**
		 * Get all not nested non-folders in {@code folder} that are
		 * within a folder in {@code folder}.
		 */
//		public static Stream<File> streamNonDirsFrom(File folder, RECURSION isRecursive) throws ExecutionControl.NotImplementedException, IOException {
//			if (isRecursive.getBool())
//				throw new ExecutionControl.NotImplementedException("not implemented yet");
//			else
//				return streamNonDirsNonRecursivelyFrom(folder);
//		}

//		private static Stream<File> streamNonDirsNonRecursivelyFrom(File folder) throws IOException {
//			File[] files = listNonDirsFrom(folder, NONRECURSIVELY);
//
//			if (files == null)
//				return Stream.empty();
//			else
//				return Arrays.stream(files)
//						.filter(File::isFile);
//		}
//

		/* ===================================================== */


		/**
		 * Map each file in {@code folder} to their content.
		 * @return a map of each {@code File} in the given folder to their
		 * respective content.
		 */
		public static Map<File, String> filesToTheirContent(File folder) throws IOException {
			Map<File, String> fileToContent = new HashMap<>();
			for (File file : listNonDirsRecursivelyFrom(folder))
				fileToContent.put(
						file,
						String.join("/n", Files.readAllLines(file.toPath()))
				);
			return fileToContent;
		}


		/* ===================================================== */


		/**
		 * Get {@link File}s recursively in {@code folder} in a depth-first manner.
		 * @return A list of all files in {@code folder}
		 */
		public static String[] getRelativePathsFrom(File folder) throws IOException {
			return Arrays.stream(listFilesAndDirsFrom(folder, RECURSIVELY))
					.map(file -> relativePath(folder, file))
					.toArray(String[]::new);
		}

	}


	/**
	 * FileUtil methods related to modifying files
	 */
	public static class Modifier {



		public static void writeStringTo(File file, String content) throws IOException {
			Charset charset = Charset.availableCharsets().get("utf-8");
			org.apache.commons.io.FileUtils.writeStringToFile(file, content, charset);
		}
		public static void insertLineAtTopOf(File file, String lineToPrepend) throws IOException {
			String newContent = lineToPrepend + '\n' + contentOf(file);

			file.delete();

			FileOutputStream fos = new FileOutputStream(file);
			fos.write(newContent.getBytes());
			fos.flush();
			fos.close();
		}

	}

	/**
	 * FileUtil methods related to retrieving content from files
	 */
	public static class Retriever {


		/**
		 * Return the content of the given {@code File}
		 */
		public static String contentOf(File file) {
			String content;
			try {
				content = readFileToString(file, "utf-8");

//				if (file.getName().endsWith("C.md")) {
//					System.out.println("(((((((((((((((((((((");
//					System.out.println(content);
//					System.out.println("(((((((((((((((((((((");
//					System.exit(0);
//				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			}

			// For some reason ´readFileToString` adds '\r', which makes our tests fail because our
			// expected doesn't have any "\r", so remove all '\r'.
			return content.replaceAll("\r","");
		}

	}

	/**
	 * Anything related to modifying paths or path-calculation-oriented methods
	 */
	public static class Filename {

		/**
		 * Replace all "\\\\" occurences in a path with "/".
		 */
		public static String normalize(String path) {

			return path.replaceAll("\\\\", "/");
		}

		/**
		 * Change The file extension of a filename to something new.
		 * e.g. "test.md" -> "test.html"
		 * @return filename
		 */
		public static String changeFileExt(String filename, String newExt) {
			int indexOfLastDot = filename.lastIndexOf(".");
			if (indexOfLastDot == -1)
				return filename;
			else
				return filename.substring(0, indexOfLastDot) + "." + newExt;
		}
		public static String changeFileExt(File file, String newExt) {
			return changeFileExt(file.toString(), newExt);
		}

		public static File[] changeFileExtOfFilesIn(File[] files, String toExt) {
			return Arrays.stream(files)
					.map(file -> new File(changeFileExt(file.toString(), toExt)))
					.toArray(File[]::new);
		}

		/**
		 * Get the path of each file in the inputFolders.content dir RELATIVE to the base 'C:/.../culprit/content' dir
		 * E.g. instead of 'C:/.../culprit/content/aa/test.md' then 'aa/test.md'
		 */
		public static String relativePath(File basePath, File file) {
			return basePath.toURI().relativize(file.toURI()).getPath();
		}

		/**
		 * Get the path of each file in the inputFolders.content dir RELATIVE to the base 'C:/.../culprit/content' dir
		 * E.g. instead of 'C:/.../culprit/content/aa/test.md' then 'aa/test.md'
		 */
		public static String relativePath(String basePath, String filename) {
			return relativePath(new File(basePath), new File(filename));
		}

		public static String fileExtOf(File file) {
			String filename = file.toString();
			return filename.substring(filename.lastIndexOf(".") + 1);
		}

		/**
		 * Checks whether the paths aren't a subset of one another (i.e. is distinct).
		 * <pre>
		 * +-------------------------------------------------------------------------+
		 * |CWD: "C:/Users/sebas/IdeaProjects/culprit_2"                             |
		 * |  path1: "/someDir/content/"          (relative path in relation to CWD) |
		 * |  path2: "/someDir/deploy/"           (relative path in relation to CWD) |                                   |
		 * |    ->                                                                   |
		 * | @return false       - since they share the common folder 'someDir' ´    |
		 * +-------------------------------------------------------------------------+
		 * |CWD: "C:/Users/sebas/IdeaProjects/culprit_2"                             |
		 * |  path1: "/content/"                                                     |
		 * |  path2: "/deploy/"                                                      |
		 * |    ->                                                                   |
		 * | @return true                                                            |
		 * +-------------------------------------------------------------------------+
		 * </pre>
		 * @return true if they don't share a common dir (i.e. are distinct), false otherwise
		 */
		public static boolean areDistinctFilePaths(@NotNull String path1, 
		                                           @NotNull String path2) {
			Path absPath1 = getAbsPathOf(path1);
			Path absPath2 = getAbsPathOf(path2);

			Path parent, possibleChild;

			parent        = absPath1;
			possibleChild = absPath2;

			boolean path2IsChildToPath1 = possibleChild.toAbsolutePath()
											.startsWith(parent);

			parent        = absPath2;
			possibleChild = absPath1;

			boolean path1IsChildToPath2 = possibleChild.toAbsolutePath().startsWith(parent);

			return !path1IsChildToPath2 && !path2IsChildToPath1;
		}

		public static Path getAbsPathOf(String path) {
			return Paths.get(path).toAbsolutePath();
		}



		/**
		 * Calculates the relative file path; Examples:
		 * <pre>
		 * +-------------------------------------------------------------------------+
		 * |baseFile: "resources/compilerTest_testFiles/D_LINKS/expected/nested/C.md"|
		 * |  toFile: "resources/compilerTest_testFiles/D_LINKS/expected/A.md"       |
		 * |                ->                                                       |
		 * | @return    "./../A.md"                                                  |
		 * +-------------------------------------------------------------------------+
		 * |baseFile: "resources/compilerTest_testFiles/D_LINKS/expected/nested"     |
		 * |  toFile: "resources/compilerTest_testFiles/D_LINKS/input"               |
		 * |                ->                                                       |
		 * | @return    "../../input"                                                |
		 * +-------------------------------------------------------------------------+
		 * </pre>
		 *
		 * @param fromFile file from which to start  the relative path from
		 * @param toFile   file to   which to end up
		 * @return relative filePath, e.g.  "./../B.md"  or  "../../input"
		 */
		public static String relativizePathBetween(File fromFile, File toFile) {
			Path fromFilePath = getNormalizedPath(fromFile);
			Path   toFilePath = getNormalizedPath(  toFile);

			String relativePath = normalize( "" + fromFilePath.relativize(toFilePath) );

			if (fromFile.isFile())
				return removeLeadingDot(relativePath);
			else
				return relativePath;
		}

		@NotNull
		private static Path getNormalizedPath(File fromFile) {
			return Paths.get( normalize( fromFile.toString() ) );
		}

		public static String removeLeadingDot(String relativePath) {
			if (relativePath.startsWith("."))
				return relativePath.substring(1);
			return relativePath;
		}

	}
}



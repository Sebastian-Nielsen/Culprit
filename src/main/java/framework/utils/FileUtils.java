package framework.utils;

import jdk.jshell.spi.ExecutionControl;

import java.io.*;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static framework.utils.FileUtils.Filename.getRelativePath;
import static framework.utils.FileUtils.Lister.RECURSION.NONRECURSIVE;
import static framework.utils.FileUtils.Lister.RECURSION.RECURSIVE;
import static framework.utils.FileUtils.Retriever.contentOf;
import static org.apache.commons.io.FileUtils.readFileToString;

public class FileUtils {
	/**
	 * FileUtil methods related to listing files
	 */
	public static class Lister {

		public enum RECURSION {
			RECURSIVE(true), NONRECURSIVE(false);

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


		public static File[] listDirsFrom(File folder, RECURSION isRecursive) throws IOException {
			if (isRecursive.getBool())
				return listDirsRecursivelyFrom(folder);
			else
				return listDirsNonRecursivelyFrom(folder);
		}

		private static File[] listDirsRecursivelyFrom(File folder) throws IOException {
			return Files.walk(folder.toPath())  // lists recursively in a depth-first manner
					.skip(1)  // Don't include the "root" aka. {@code folder}
					.map(Path::toFile)
					.filter(File::isDirectory)
					.toArray(File[]::new);
		}

		private static File[] listDirsNonRecursivelyFrom(File folder) throws IOException {
			File[] files = folder.listFiles();
			if (files == null)
				return new File[]{};
			return Arrays.stream(files)
					.filter(File::isDirectory)
					.toArray(File[]::new);
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
		public static Stream<File> streamNonDirsFrom(File folder, RECURSION isRecursive) throws ExecutionControl.NotImplementedException, IOException {
			if (isRecursive.getBool())
				throw new ExecutionControl.NotImplementedException("not implemented yet");
			else
				return streamNonDirsNonRecursivelyFrom(folder);
		}

		private static Stream<File> streamNonDirsNonRecursivelyFrom(File folder) throws IOException {
			File[] files = listNonDirsFrom(folder, NONRECURSIVE);

			if (files == null)
				return Stream.empty();
			else
				return Arrays.stream(files)
						.filter(File::isFile);
		}


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
						String.join("\n", Files.readAllLines(file.toPath()))
				);
			return fileToContent;
		}


		/* ===================================================== */


		/**
		 * Get {@link File}s recursively in {@code folder} in a depth-first manner.
		 * @return A list of all files in {@code folder}
		 */
		public static String[] getRelativePathsFrom(File folder) throws IOException {
			String basePath = "" + folder;
			URI uri = new File(basePath).toURI();

			return Arrays.stream(listFilesAndDirsFrom(folder, RECURSIVE))
					.map(file -> getRelativePath(file, uri))
					.toArray(String[]::new);
		}

	}


	/**
	 * FileUtil methods related to modifying files
	 */
	public static class Modifier {



		public static void writeStringTo(File file, String content) throws IOException {
			Charset charset = Charset.availableCharsets().get("windows-1252");
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
				content = readFileToString(file, "iso-8859-1");

//				if (file.getName().endsWith("twix.md")) {
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
		 * Replace all "\\" occurences in a path with "/".
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
			return filename.substring(0, filename.lastIndexOf(".")) + "." + newExt;
		}

		public static File[] changeFileExtOfFilesIn(Set<File> files, String toExt) {
			return files.stream()
					.map(file -> new File(changeFileExt(file.toString(), toExt)))
					.toArray(File[]::new);
		}

		/**
		 * Get the path of each file in the inputFolders.content dir RELATIVE to the base inputFolders.content dir
		 * E.g. instead of 'C:/.../culprit/inputFolders.content/aa/test.md' then 'aa/test.md'
		 */
		public static String getRelativePath(File file, URI basePath) {
			return basePath.relativize(file.toURI()).getPath();
		}

	}
}



package common;

import framework.Compiler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import framework.FileOption;

import static framework.FileOption.KEY.*;
import static framework.FileOption.KEY;
import static framework.utils.FileUtils.*;
import static org.apache.commons.io.FileUtils.readFileToString;

public class CompilerImpl implements Compiler {
	private final File contentRootFolder;
	private final Map<String, File> IDValToFile = new HashMap<>();

	public CompilerImpl(File contentRootFolder) {
		this.contentRootFolder = contentRootFolder;
	}

	/**
	 * Go through all files while:
	 * (1) record (value of {@code ID}, {@code File}) pairs
	 * (2) Asserting that each {@code File} has all _required_ {@code FileOption}s
	 */
	public void preprocess(Map<File, Map<KEY, String>> fileToKeyToVal) {

		for (File file : fileToKeyToVal.keySet()) {

			assertHasAllRequiredFileOptions(file, fileToKeyToVal);

			recordIDValAndFile(file, fileToKeyToVal);
		}

	}

	private void recordIDValAndFile(File file, Map<File, Map<KEY, String>> fileToKeyToVal) {
			IDValToFile.put(
					fileToKeyToVal.get(file).get(ID),
					file
			);
	}

	private void assertHasAllRequiredFileOptions(File file, Map<File, Map<KEY, String>> fileToKeyToVal) {

		Set<KEY> keysOfFile = fileToKeyToVal.get(file).keySet();

		for (KEY key : FileOption.REQUIRED_KEYS) {

			boolean fileHasRequiredFileOption = keysOfFile.contains(key);
			if (!fileHasRequiredFileOption)
				throw new RuntimeException(
						"For File: " + file + "\n" +
						"For KEY: "  + key  + "\n" +
						"no value has been given and there's no default value.\n\n" +
						"Please add FileOption: `["+key+"]:<> (<value>)´\n" +
						"to File: " + file + "\n\n");
		}

	}


	public Map<File, String> compile(Map<File, Map<KEY, String>> fileToKeyToVal) throws IOException {
		Map<File, String> fileToCompiledContent = new HashMap<>();

		for (File file : listAllNonDirFilesFrom(contentRootFolder))
			fileToCompiledContent.put(
					file,
					compile(file, fileToKeyToVal.get(file))
			);

		return fileToCompiledContent;
	}


	private String compile(File fileToCompile, Map<KEY, String> keyToVal) throws IOException {
		String content = contentOf(fileToCompile);

//		String ID_val     = keyToVal.getOrDefault(KEY.ID,      KEY.ID.getDefaultVal(fileToCompile));
		String DLINKS_val = keyToVal.getOrDefault(KEY.D_LINKS, "false");

		System.out.println("(((((((((((((((((((((88");
		System.out.println(content);
		content = handleDLINKS(DLINKS_val, content, fileToCompile);

		System.out.println(fileToCompile);
		System.out.println("+" + content + "+");

		return content;
	}

	/**
	 * For each dynamic-link in {@code fileToCompileContent}, replace
	 * the ID with the relative path of the file associated with the ID,
	 * relativized in relation to {@code fileToCompile}.
	 *  E.g.    `[link text](2d424a8f-6fe8-455d-81de-6be20691cf32)`
	 *              ->
	 *          `[link text](../folder/Z.md)`
	 * @param value the value of the D_LINKS-key.
	 */
	private String handleDLINKS(String value, String contentOfFileToCompile, File fileToCompile) {
		if (value.equals("false"))
			return contentOfFileToCompile;

		String markdownLinkRegex = "\\[(.*?)]\\((.*?)\\)";
		Matcher matcher = Pattern.compile(markdownLinkRegex).matcher(contentOfFileToCompile);

		if (!matcher.find())
			return contentOfFileToCompile;

		return replaceAllIDsWithFilePaths(matcher, fileToCompile);
	}

	private String replaceAllIDsWithFilePaths(Matcher matcher, File file) {
		StringBuffer buffer = new StringBuffer();
		do {
			String linkText   = matcher.group(1);
			String id         = matcher.group(2);
			File fileOfID     = IDValToFile.get(id);
			String pathToFile = relativeFilePathBetween(file, fileOfID);
			String replacement = "[" + linkText + "](" + pathToFile + ")";

			matcher.appendReplacement(buffer, replacement);
		} while (matcher.find());

		return buffer.toString();
	}

	/**
	 * Calculates the relative file path; E.g.
	 * +-------------------------------------------------------------------------+
	 * |baseFile: "resources/compilerTest_testFiles/D_LINKS/expected/nested/C.md"|
	 * |  toFile: "resources/compilerTest_testFiles/D_LINKS/expected/A.md"       |
	 * |                ->                                                       |                    |
	 * | @return    "./../A.md"                                                  |
	 * +-------------------------------------------------------------------------+
	 * @param baseFile file from which to start  the relative path from
	 * @param toFile   file to   which to end up the relative path to
	 * @return relative filePath, e.g. "./../B.md"
	 */
	private String relativeFilePathBetween(File baseFile, File toFile) {
		Path baseFilePath = Paths.get("" + baseFile);
		Path   toFilePath = Paths.get(normalize("" + toFile));

		String relativePath = baseFilePath.relativize(toFilePath).toString();

		relativePath = relativePath.replaceAll("\\\\", "/");
		relativePath = removeLeadingDot(relativePath);

		return relativePath;
	}

	private String removeLeadingDot(String relativePath) {
		if (relativePath.startsWith("."))
			return relativePath.substring(1);
		return relativePath;
	}


}

package common;

import framework.Compiler;
import framework.FileOption;
import framework.FileOptionContainer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static framework.Constants.Constants.CWD;
import static framework.FileOption.KEY;
import static framework.FileOption.KEY.ID;
import static framework.ValidatorImpl.REGEXES;
import static framework.utils.FileUtils.*;

public class CompilerImpl implements Compiler {
	private final File contentRootFolder;
	private final Map<String, File> IDValToFile = new HashMap<>();

	public CompilerImpl(File contentRootFolder) {
		this.contentRootFolder = contentRootFolder;
	}

	public CompilerImpl() {
		this.contentRootFolder = new File(CWD + "content");
	}

	/**
	 * Go through all files while:
	 * (1) record (value of {@code ID}, {@code File}) pairs
	 * (2) Asserting that each {@code File} has all _required_ {@code FileOption}s
	 */
	public void preprocess(Map<File, FileOptionContainer> fileToFOContainer) {

		for (File file : fileToFOContainer.keySet()) {

			assertHasAllRequiredFileOptions(file, fileToFOContainer);

			recordIDValAndFile(file, fileToFOContainer);
		}

	}

	private void recordIDValAndFile(File file, Map<File, FileOptionContainer> fileToFOContainer) {
			IDValToFile.put(
					fileToFOContainer.get(file).get(ID),
					file
			);
	}

	private void assertHasAllRequiredFileOptions(File file, Map<File, FileOptionContainer> fileToFOContainer) {

		Set<KEY> keysOfFile = fileToFOContainer.get(file).keySet();

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


	public Map<File, String> compile(Map<File, FileOptionContainer> fileToFOContainer) throws IOException {
		Map<File, String> fileToCompiledContent = new HashMap<>();

		for (File file : listAllNonDirFilesFrom(contentRootFolder))

			fileToCompiledContent.put(
					file,
					compile(file, fileToFOContainer.get(file))
			);

		return fileToCompiledContent;
	}


	private String compile(File fileToCompile, FileOptionContainer keyToVal) throws IOException {
//		String ID_val     = keyToVal.getOrDefault(KEY.ID,      KEY.ID.getDefaultVal(fileToCompile));
		String DLINKS_val = keyToVal.getOrDefault(KEY.D_LINKS, "false");

		String content = contentOf(fileToCompile);
		content = handleDLINKS(DLINKS_val, content, fileToCompile);

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

		String markdownLinkRegex = "\\[(.*?)]\\((%s)\\)".formatted(REGEXES.UUID);
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
			File fileOfID        = IDValToFile.get(id);
			String pathToFile = relativeFilePathBetween(file, fileOfID);
			String replacement = "[" + linkText + "](" + pathToFile + ")";

			matcher.appendReplacement(buffer, replacement);
		} while (matcher.find());

		matcher.appendTail(buffer);

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
